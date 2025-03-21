package com.modmenu.overlay;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FloatingModService extends Service {

    private static final String TAG = "FloatingModService";
    public static final String ACTION_UPDATE_OPACITY = "com.modmenu.overlay.UPDATE_OPACITY";
    private static boolean isServiceRunning = false;
    
    private WindowManager windowManager;
    private View floatingView;
    private CardView menuCard;
    private LinearLayout menuContent;
    private ImageView btnClose, btnMinimize;
    private ModManager modManager;
    private ModPreferences preferences;
    private GameDetector gameDetector;
    private TextView gameTitleText;
    private ScheduledExecutorService gameDetectionScheduler;
    
    // Initial position values
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;
    private WindowManager.LayoutParams params;

    public static boolean isRunning() {
        return isServiceRunning;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isServiceRunning = true;
        preferences = new ModPreferences(this);
        gameDetector = new GameDetector(this);
        
        // Set up modManager based on detected game
        setupModManager();
        
        // Initialize window manager and layout parameters
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        
        // Create the floating view
        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_mod_menu, null);

        // Initialize layout parameters
        int layoutFlag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
        }
        
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        
        // Initial position of the window
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;
        
        // Set opacity from preferences
        params.alpha = preferences.getOpacity();
        
        // Get layout elements
        menuCard = floatingView.findViewById(R.id.cardViewMenu);
        menuContent = floatingView.findViewById(R.id.menuContent);
        btnClose = floatingView.findViewById(R.id.btnClose);
        btnMinimize = floatingView.findViewById(R.id.btnMinimize);
        
        // Set up close button
        btnClose.setOnClickListener(v -> stopSelf());
        
        // Set up minimize/maximize button
        btnMinimize.setOnClickListener(v -> {
            if (menuContent.getVisibility() == View.VISIBLE) {
                menuContent.setVisibility(View.GONE);
                btnMinimize.setRotation(180); // Rotate to show it's minimized
            } else {
                menuContent.setVisibility(View.VISIBLE);
                btnMinimize.setRotation(0); // Rotate back
            }
            // Update the layout
            windowManager.updateViewLayout(floatingView, params);
        });
        
        // Set touch listener for dragging the floating view
        menuCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                        
                    case MotionEvent.ACTION_MOVE:
                        // Calculate movement
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        
                        // Update the layout
                        windowManager.updateViewLayout(floatingView, params);
                        return true;
                }
                return false;
            }
        });
        
        // Initialize mod switches
        initializeModSwitches();
        
        // Add the view to the window
        windowManager.addView(floatingView, params);
    }

    private void initializeModSwitches() {
        // Speed Hack
        Switch switchSpeedHack = floatingView.findViewById(R.id.switchSpeedHack);
        switchSpeedHack.setChecked(preferences.isSpeedHackEnabled());
        switchSpeedHack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.setSpeedHackEnabled(isChecked);
            if (isChecked) {
                modManager.enableSpeedHack();
                Toast.makeText(this, "Speed hack enabled", Toast.LENGTH_SHORT).show();
            } else {
                modManager.disableSpeedHack();
                Toast.makeText(this, "Speed hack disabled", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Unlimited Resources
        Switch switchUnlimitedResources = floatingView.findViewById(R.id.switchUnlimitedResources);
        switchUnlimitedResources.setChecked(preferences.isUnlimitedResourcesEnabled());
        switchUnlimitedResources.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.setUnlimitedResourcesEnabled(isChecked);
            if (isChecked) {
                modManager.enableUnlimitedResources();
                Toast.makeText(this, "Unlimited resources enabled", Toast.LENGTH_SHORT).show();
            } else {
                modManager.disableUnlimitedResources();
                Toast.makeText(this, "Unlimited resources disabled", Toast.LENGTH_SHORT).show();
            }
        });
        
        // God Mode
        Switch switchGodMode = floatingView.findViewById(R.id.switchGodMode);
        switchGodMode.setChecked(preferences.isGodModeEnabled());
        switchGodMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.setGodModeEnabled(isChecked);
            if (isChecked) {
                modManager.enableGodMode();
                Toast.makeText(this, "God mode enabled", Toast.LENGTH_SHORT).show();
            } else {
                modManager.disableGodMode();
                Toast.makeText(this, "God mode disabled", Toast.LENGTH_SHORT).show();
            }
        });
        
        // No Cooldown
        Switch switchNoCooldown = floatingView.findViewById(R.id.switchNoCooldown);
        switchNoCooldown.setChecked(preferences.isNoCooldownEnabled());
        switchNoCooldown.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.setNoCooldownEnabled(isChecked);
            if (isChecked) {
                modManager.enableNoCooldown();
                Toast.makeText(this, "No cooldown enabled", Toast.LENGTH_SHORT).show();
            } else {
                modManager.disableNoCooldown();
                Toast.makeText(this, "No cooldown disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_UPDATE_OPACITY.equals(intent.getAction())) {
            float opacity = intent.getFloatExtra("opacity", 1.0f);
            params.alpha = opacity;
            windowManager.updateViewLayout(floatingView, params);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
        }
        
        // Clean up resources
        if (modManager != null) {
            modManager.onDeactivate();
        }
        
        // Stop game detection scheduler
        if (gameDetectionScheduler != null && !gameDetectionScheduler.isShutdown()) {
            gameDetectionScheduler.shutdown();
        }
        
        isServiceRunning = false;
    }
    
    /**
     * Sets up the appropriate mod manager based on detected games
     * and starts periodic game detection
     */
    private void setupModManager() {
        // Initialize with appropriate mod manager based on current foreground app
        updateModManager();
        
        // Start periodic game detection to update mod manager when games change
        gameDetectionScheduler = Executors.newSingleThreadScheduledExecutor();
        gameDetectionScheduler.scheduleAtFixedRate(() -> {
            updateModManager();
        }, 5, 5, TimeUnit.SECONDS); // Check every 5 seconds
    }
    
    /**
     * Updates the mod manager based on currently detected game
     */
    private void updateModManager() {
        boolean isSubwaySurfersRunning = gameDetector.isSubwaySurfersRunning();
        
        // Create an appropriate mod manager for the detected game
        if (isSubwaySurfersRunning) {
            if (!(modManager instanceof SubwaySurfersModManager)) {
                Log.d(TAG, "Switching to Subway Surfers mod manager");
                
                // Clean up old mod manager if it exists
                if (modManager != null) {
                    modManager.onDeactivate();
                }
                
                // Create new Subway Surfers-specific manager
                modManager = new SubwaySurfersModManager(this);
                
                // Update UI to show game-specific mods if UI is initialized
                updateUIForDetectedGame("Subway Surfers");
                
                // Apply any enabled mods
                applyEnabledMods();
            }
        } else {
            // If no supported game is detected and we're using a game-specific manager,
            // switch back to the default manager
            if (!(modManager instanceof ModManager) || modManager instanceof SubwaySurfersModManager) {
                Log.d(TAG, "Switching to default mod manager");
                
                // Clean up old mod manager if it exists
                if (modManager != null) {
                    modManager.onDeactivate();
                }
                
                // Create new default manager
                modManager = new ModManager(this);
                
                // Update UI
                updateUIForDetectedGame("No Game Detected");
                
                // Apply any enabled mods
                applyEnabledMods();
            }
        }
    }
    
    /**
     * Updates the UI to show which game is currently detected
     * @param gameName Name of the detected game
     */
    private void updateUIForDetectedGame(final String gameName) {
        // Run on UI thread
        Handler mainHandler = new Handler(getMainLooper());
        mainHandler.post(() -> {
            // If we have a game title text view, update it
            if (floatingView != null) {
                gameTitleText = floatingView.findViewById(R.id.gameTitleText);
                if (gameTitleText != null) {
                    gameTitleText.setText(gameName);
                }
                
                // Update game-specific controls visibility
                LinearLayout subwaySurfersControls = floatingView.findViewById(R.id.subwaySurfersControls);
                if (subwaySurfersControls != null) {
                    if ("Subway Surfers".equals(gameName)) {
                        subwaySurfersControls.setVisibility(View.VISIBLE);
                        setupSubwaySurfersControls();
                    } else {
                        subwaySurfersControls.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    
    /**
     * Setup Subway Surfers specific controls
     */
    private void setupSubwaySurfersControls() {
        if (floatingView == null || !(modManager instanceof SubwaySurfersModManager)) {
            return;
        }
        
        final SubwaySurfersModManager ssModManager = (SubwaySurfersModManager) modManager;
        
        // Infinite Hoverboard
        Switch switchInfiniteHoverboard = floatingView.findViewById(R.id.switchInfiniteHoverboard);
        switchInfiniteHoverboard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ssModManager.enableInfiniteHoverboard();
                Toast.makeText(this, "Infinite hoverboard enabled", Toast.LENGTH_SHORT).show();
            } else {
                ssModManager.disableInfiniteHoverboard();
                Toast.makeText(this, "Infinite hoverboard disabled", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Max Score Multiplier
        Switch switchMaxMultiplier = floatingView.findViewById(R.id.switchMaxMultiplier);
        switchMaxMultiplier.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ssModManager.enableMaxScoreMultiplier();
                Toast.makeText(this, "Max score multiplier enabled", Toast.LENGTH_SHORT).show();
            } else {
                ssModManager.disableMaxScoreMultiplier();
                Toast.makeText(this, "Max score multiplier disabled", Toast.LENGTH_SHORT).show();
            }
        });
        
        // No Obstacles
        Switch switchNoObstacles = floatingView.findViewById(R.id.switchNoObstacles);
        switchNoObstacles.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ssModManager.enableNoObstacles();
                Toast.makeText(this, "No obstacles enabled", Toast.LENGTH_SHORT).show();
            } else {
                ssModManager.disableNoObstacles();
                Toast.makeText(this, "No obstacles disabled", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Auto Jump/Slide
        Switch switchAutoJump = floatingView.findViewById(R.id.switchAutoJump);
        switchAutoJump.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ssModManager.enableAutomaticJump();
                Toast.makeText(this, "Auto jump/slide enabled", Toast.LENGTH_SHORT).show();
            } else {
                ssModManager.disableAutomaticJump();
                Toast.makeText(this, "Auto jump/slide disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /**
     * Applies any enabled mods from preferences
     */
    private void applyEnabledMods() {
        // Apply any enabled mods based on preferences
        if (preferences.isSpeedHackEnabled()) {
            modManager.enableSpeedHack();
        }
        
        if (preferences.isUnlimitedResourcesEnabled()) {
            modManager.enableUnlimitedResources();
        }
        
        if (preferences.isGodModeEnabled()) {
            modManager.enableGodMode();
        }
        
        if (preferences.isNoCooldownEnabled()) {
            modManager.enableNoCooldown();
        }
    }
}
