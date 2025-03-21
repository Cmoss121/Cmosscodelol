package com.modmenu.overlay;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Handles saving and retrieving mod settings using SharedPreferences
 */
public class ModPreferences {

    private static final String PREFS_NAME = "ModMenuPreferences";
    private static final String KEY_OPACITY = "opacity";
    private static final String KEY_START_ON_BOOT = "start_on_boot";
    private static final String KEY_SPEED_HACK = "speed_hack";
    private static final String KEY_UNLIMITED_RESOURCES = "unlimited_resources";
    private static final String KEY_GOD_MODE = "god_mode";
    private static final String KEY_NO_COOLDOWN = "no_cooldown";

    private SharedPreferences prefs;

    public ModPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Get the opacity value for the floating menu
     * @return opacity value between 0.0f and 1.0f (default 0.8f)
     */
    public float getOpacity() {
        return prefs.getFloat(KEY_OPACITY, 0.8f);
    }

    /**
     * Set the opacity value for the floating menu
     * @param opacity value between 0.0f and 1.0f
     */
    public void setOpacity(float opacity) {
        prefs.edit().putFloat(KEY_OPACITY, opacity).apply();
    }

    /**
     * Check if the mod menu should start on boot
     * @return true if start on boot is enabled, false otherwise
     */
    public boolean getStartOnBoot() {
        return prefs.getBoolean(KEY_START_ON_BOOT, false);
    }

    /**
     * Set whether the mod menu should start on boot
     * @param startOnBoot true to enable start on boot, false otherwise
     */
    public void setStartOnBoot(boolean startOnBoot) {
        prefs.edit().putBoolean(KEY_START_ON_BOOT, startOnBoot).apply();
    }

    /**
     * Check if speed hack is enabled
     * @return true if speed hack is enabled, false otherwise
     */
    public boolean isSpeedHackEnabled() {
        return prefs.getBoolean(KEY_SPEED_HACK, false);
    }

    /**
     * Set whether speed hack is enabled
     * @param enabled true to enable, false to disable
     */
    public void setSpeedHackEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_SPEED_HACK, enabled).apply();
    }

    /**
     * Check if unlimited resources is enabled
     * @return true if unlimited resources is enabled, false otherwise
     */
    public boolean isUnlimitedResourcesEnabled() {
        return prefs.getBoolean(KEY_UNLIMITED_RESOURCES, false);
    }

    /**
     * Set whether unlimited resources is enabled
     * @param enabled true to enable, false to disable
     */
    public void setUnlimitedResourcesEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_UNLIMITED_RESOURCES, enabled).apply();
    }

    /**
     * Check if god mode is enabled
     * @return true if god mode is enabled, false otherwise
     */
    public boolean isGodModeEnabled() {
        return prefs.getBoolean(KEY_GOD_MODE, false);
    }

    /**
     * Set whether god mode is enabled
     * @param enabled true to enable, false to disable
     */
    public void setGodModeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_GOD_MODE, enabled).apply();
    }

    /**
     * Check if no cooldown is enabled
     * @return true if no cooldown is enabled, false otherwise
     */
    public boolean isNoCooldownEnabled() {
        return prefs.getBoolean(KEY_NO_COOLDOWN, false);
    }

    /**
     * Set whether no cooldown is enabled
     * @param enabled true to enable, false to disable
     */
    public void setNoCooldownEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_NO_COOLDOWN, enabled).apply();
    }
}
