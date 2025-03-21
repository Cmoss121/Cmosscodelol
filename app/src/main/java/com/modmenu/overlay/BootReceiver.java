package com.modmenu.overlay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * Broadcast receiver for device boot to start the floating mod service if enabled in preferences
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "Boot completed received");
            
            ModPreferences preferences = new ModPreferences(context);
            
            // Check if start on boot is enabled
            if (preferences.getStartOnBoot()) {
                Log.d(TAG, "Start on boot is enabled");
                
                // Check if we have overlay permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                    Log.e(TAG, "Overlay permission not granted, cannot start service");
                    return;
                }
                
                // Start the floating mod service
                Intent serviceIntent = new Intent(context, FloatingModService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent);
                } else {
                    context.startService(serviceIntent);
                }
                
                Log.d(TAG, "Service started on boot");
            }
        }
    }
}