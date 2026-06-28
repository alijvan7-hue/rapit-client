package com.rapit.client.profile;

import com.rapit.client.RapitClient;
import com.rapit.client.util.Logger;

import java.util.List;

/**
 * High-level profile manager wrapping ConfigManager.
 * Provides profile creation, switching, deletion and listing.
 */
public class ProfileManager {

    public ProfileManager() {
        Logger.info("ProfileManager initialized.");
    }

    /** Returns all available profile names. */
    public List<String> getProfiles() {
        return RapitClient.getInstance().getConfigManager().listProfiles();
    }

    /** Saves the current state to a new or existing profile. */
    public void saveProfile(String name) {
        RapitClient.getInstance().getConfigManager().save(name);
        Logger.info("Profile saved: " + name);
    }

    /** Loads a profile by name. */
    public void loadProfile(String name) {
        RapitClient.getInstance().getConfigManager().load(name);
        Logger.info("Profile loaded: " + name);
    }

    /** Deletes a profile by name. */
    public void deleteProfile(String name) {
        RapitClient.getInstance().getConfigManager().deleteProfile(name);
        Logger.info("Profile deleted: " + name);
    }

    /** Returns the currently active profile name. */
    public String getCurrentProfile() {
        return RapitClient.getInstance().getConfigManager().getCurrentProfile();
    }
}
