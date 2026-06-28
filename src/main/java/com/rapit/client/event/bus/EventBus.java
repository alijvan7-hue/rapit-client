package com.rapit.client.event.bus;

import com.rapit.client.event.events.RapitEvent;
import com.rapit.client.util.Logger;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread-safe, annotation-driven event bus.
 *
 * <p>Usage:
 * <pre>
 *   // Subscribe
 *   eventBus.register(this);
 *
 *   // Handle
 *   {@literal @}EventListener
 *   public void onUpdate(PlayerUpdateEvent e) { ... }
 *
 *   // Post
 *   eventBus.post(new PlayerUpdateEvent());
 * </pre>
 */
public class EventBus {

    /** Map from event class → list of subscriber entries */
    private final Map<Class<? extends RapitEvent>, List<SubscriberEntry>> subscribers =
            new ConcurrentHashMap<>();

    // ── Registration ──────────────────────────────────────────────────────────

    /**
     * Scans {@code listener} for methods annotated with {@link EventListener}
     * and registers them.
     */
    public void register(Object listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventListener.class)) continue;
            if (method.getParameterCount() != 1) continue;

            Class<?> paramType = method.getParameterTypes()[0];
            if (!RapitEvent.class.isAssignableFrom(paramType)) continue;

            @SuppressWarnings("unchecked")
            Class<? extends RapitEvent> eventClass = (Class<? extends RapitEvent>) paramType;

            method.setAccessible(true);
            subscribers
                    .computeIfAbsent(eventClass, k -> new CopyOnWriteArrayList<>())
                    .add(new SubscriberEntry(listener, method,
                            method.getAnnotation(EventListener.class).priority()));

            // Keep sorted by priority (higher first)
            subscribers.get(eventClass).sort(
                    Comparator.comparingInt(SubscriberEntry::getPriority).reversed());
        }
    }

    /**
     * Removes all subscriptions belonging to {@code listener}.
     */
    public void unregister(Object listener) {
        subscribers.values().forEach(list ->
                list.removeIf(entry -> entry.getListener() == listener));
    }

    // ── Posting ───────────────────────────────────────────────────────────────

    /**
     * Posts an event to all registered subscribers.
     *
     * @param event the event to dispatch
     * @return the same event (potentially cancelled)
     */
    public <T extends RapitEvent> T post(T event) {
        List<SubscriberEntry> entries = subscribers.get(event.getClass());
        if (entries == null || entries.isEmpty()) return event;

        for (SubscriberEntry entry : entries) {
            if (event.isCancelled()) break;
            try {
                entry.invoke(event);
            } catch (Exception ex) {
                Logger.error("EventBus error in " + entry.getListener().getClass().getSimpleName()
                        + "#" + entry.getMethod().getName() + ": " + ex.getMessage());
            }
        }
        return event;
    }

    // ── Inner ─────────────────────────────────────────────────────────────────

    private static class SubscriberEntry {
        private final Object listener;
        private final Method method;
        private final int    priority;

        SubscriberEntry(Object listener, Method method, int priority) {
            this.listener = listener;
            this.method   = method;
            this.priority = priority;
        }

        void invoke(RapitEvent event) throws Exception {
            method.invoke(listener, event);
        }

        Object getListener() { return listener; }
        Method getMethod()   { return method; }
        int    getPriority() { return priority; }
    }
}
