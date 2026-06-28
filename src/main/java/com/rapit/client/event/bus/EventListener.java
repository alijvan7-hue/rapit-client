package com.rapit.client.event.bus;

import java.lang.annotation.*;

/**
 * Marks a method as an event subscriber.
 * The method must accept exactly one parameter that extends {@link com.rapit.client.event.events.RapitEvent}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventListener {
    /** Higher value = called first. Default 0. */
    int priority() default 0;
}
