package com.modmenu.overlay;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 100;
    private Button btnStartService;
    private SeekBar seekBarOpacity;
    private Switch switchStartOnBoot;
    private ModPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = new ModPreferences(this);

        // Initialize views
        btnStartService = findViewById(R.id.btnStartService);
        seekBarOpacity = findViewById(R.id.seekBarOpacity);
        switchStartOnBoot = findViewById(R.id.switchStartOnBoot);

        // Set initial values from preferences
        seekBarOpacity.setProgress((int)(preferences.getOpacity() * 100));
        switchStartOnBoot.setChecked(preferences.getStartOnBoot());

        // Set up click listeners
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOverlayPermission()) {
                    startFloatingModService();
                }
            }
        });

        // Set up opacity change listener
        seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float opacity = progress / 100f;
                preferences.setOpacity(opacity);
                // If service is running, update the opacity
                if (FloatingModService.isRunning()) {
                    Intent intent = new Intent(MainActivity.this, FloatingModService.class);
                    intent.setAction(FloatingModService.ACTION_UPDATE_OPACITY);
                    intent.putExtra("opacity", opacity);
                    startService(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

        // Set up start on boot switch listener
        switchStartOnBoot.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.setStartOnBoot(isChecked);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update button text based on service status
        updateServiceButton();
    }

    private void updateServiceButton() {
        if (FloatingModService.isRunning()) {
            btnStartService.setText(R.string.stop_mod_menu);
        } else {
            btnStartService.setText(R.string.start_mod_menu);
        }
    }

    private boolean checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION);
            Toast.makeText(this, R.string.overlay_permission_needed, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startFloatingModService();
            } else {
                Toast.makeText(this, R.string.overlay_permission_denied, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startFloatingModService() {
        if (FloatingModService.isRunning()) {
            // Stop the service
            stopService(new Intent(MainActivity.this, FloatingModService.class));
            btnStartService.setText(R.string.start_mod_menu);
        } else {
            // Start the service
            Intent intent = new Intent(MainActivity.this, FloatingModService.class);
            startService(intent);
            btnStartService.setText(R.string.stop_mod_menu);
            
            // Minimize app (optional)
            moveTaskToBack(true);
        }
    }
}
