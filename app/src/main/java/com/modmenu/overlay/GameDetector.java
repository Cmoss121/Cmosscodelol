package com.modmenu.overlay;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Utility class to detect running games and create appropriate ModManager instances
 */
public class GameDetector {
    private static final String TAG = "GameDetector";
    
    // Game package names and their corresponding display names
    private static final Map<String, String> SUPPORTED_GAMES = new HashMap<String, String>() {{
        put("com.kiloo.subwaysurf", "Subway Surfers");
        // Add more supported games here
    }};
    
    private Context context;
    
    /**
     * Constructor
     * @param context Application context
     */
    public GameDetector(Context context) {
        this.context = context;
    }
    
    /**
     * Detects currently running game from supported list
     * @return Game name or null if no supported game is running
     */
    public String detectRunningGame() {
        String foregroundApp = getForegroundAppPackage();
        if (foregroundApp != null && SUPPORTED_GAMES.containsKey(foregroundApp)) {
            Log.d(TAG, "Detected game: " + SUPPORTED_GAMES.get(foregroundApp));
            return SUPPORTED_GAMES.get(foregroundApp);
        }
        return null;
    }
    
    /**
     * Checks if Subway Surfers is currently running
     * @return true if Subway Surfers is running, false otherwise
     */
    public boolean isSubwaySurfersRunning() {
        String foregroundApp = getForegroundAppPackage();
        return "com.kiloo.subwaysurf".equals(foregroundApp);
    }
    
    /**
     * Creates appropriate ModManager for detected game
     * @return ModManager instance for the detected game, or generic ModManager if no specific
     * game is detected or supported
     */
    public ModManager createModManagerForDetectedGame() {
        String foregroundApp = getForegroundAppPackage();
        
        if (foregroundApp != null) {
            if ("com.kiloo.subwaysurf".equals(foregroundApp)) {
                Log.d(TAG, "Creating Subway Surfers mod manager");
                return new SubwaySurfersModManager(context);
            }
            
            // Add more game-specific mod managers here
        }
        
        // Return generic mod manager if no game detected or no specific manager available
        Log.d(TAG, "Creating generic mod manager");
        return new ModManager(context);
    }
    
    /**
     * Gets the package name of the foreground app
     * @return Package name or null if it couldn't be determined
     */
    public String getForegroundAppPackage() {
        // For API 21+, we need to use UsageStatsManager which requires permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getForegroundAppPackageUsageStats();
        } else {
            // For older versions, use ActivityManager (less reliable, only works on some devices)
            return getForegroundAppPackageActivityManager();
        }
    }
    
    /**
     * Gets foreground app using UsageStatsManager (API 21+)
     * Requires PACKAGE_USAGE_STATS permission
     * @return Package name or null if it couldn't be determined
     */
    private String getForegroundAppPackageUsageStats() {
        try {
            UsageStatsManager usageStatsManager = (UsageStatsManager) 
                    context.getSystemService(Context.USAGE_STATS_SERVICE);
            
            long currentTime = System.currentTimeMillis();
            // Get usage stats for the last 10 seconds
            List<UsageStats> stats = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY, currentTime - 10000, currentTime);
            
            if (stats != null && !stats.isEmpty()) {
                SortedMap<Long, UsageStats> sortedMap = new TreeMap<>();
                for (UsageStats usageStats : stats) {
                    sortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                
                if (!sortedMap.isEmpty()) {
                    // Get the most recently used app
                    String packageName = sortedMap.get(sortedMap.lastKey()).getPackageName();
                    
                    // Don't return our own package
                    if (packageName.equals(context.getPackageName())) {
                        return null;
                    }
                    
                    return packageName;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting foreground app with UsageStats", e);
        }
        
        return null;
    }
    
    /**
     * Gets foreground app using ActivityManager (pre API 21)
     * Less reliable, only works on some devices
     * @return Package name or null if it couldn't be determined
     */
    private String getForegroundAppPackageActivityManager() {
        try {
            ActivityManager activityManager = (ActivityManager) 
                    context.getSystemService(Context.ACTIVITY_SERVICE);
            
            List<ActivityManager.RunningAppProcessInfo> appProcesses = 
                    activityManager.getRunningAppProcesses();
            
            if (appProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        // Don't return our own package
                        if (appProcess.processName.equals(context.getPackageName())) {
                            continue;
                        }
                        
                        return appProcess.processName;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting foreground app with ActivityManager", e);
        }
        
        return null;
    }
    
    /**
     * Gets the display name of an app from its package name
     * @param packageName Package name to look up
     * @return Display name or the package name if it couldn't be determined
     */
    public String getAppNameFromPackage(String packageName) {
        if (packageName == null) {
            return null;
        }
        
        // First check our internal map of known games
        if (SUPPORTED_GAMES.containsKey(packageName)) {
            return SUPPORTED_GAMES.get(packageName);
        }
        
        // Otherwise try to get the app name from the package manager
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            return packageManager.getApplicationLabel(appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return packageName; // Fallback to package name
        }
    }
}