package com.lqos.coin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.lqos.coin.service.CoinsService;

public class MainActivity extends Activity {
    private boolean isToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, CoinsService.class));
        if (!enabled(this, "com.lqos.coin/com.lqos.coin.service.AccessibilityServiceIml")) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            isToStart = true;
        } else {
            isToStart = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isToStart) {
            if (enabled(this, "com.lqos.coin/com.lqos.coin.service.AccessibilityServiceIml")) {
                Toast.makeText(getApplicationContext(), "开启成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "开启失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), CoinsService.class);
                intent.putExtra("key", 0);
                startService(intent);
            }
        });
    }

    private boolean enabled(Context context, String name) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(name.toLowerCase());
            }
        }
        return false;
    }

    public void onclikc(View view) {
        Toast.makeText(getApplicationContext(), "有人土地", Toast.LENGTH_LONG).show();
    }
}
