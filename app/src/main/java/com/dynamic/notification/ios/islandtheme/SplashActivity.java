package com.dynamic.notification.ios.islandtheme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.lock.MyOneApplication;
import com.lock.SplashLaunchActivity;

public class SplashActivity extends AppCompatActivity {

    SplashScreen splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        MyOneApplication global=(MyOneApplication)this.getApplication();
        global.config.getRemoteConfig(this);

        global.config.OnSplashListener(() -> {
                    Intent intent = new Intent(SplashActivity.this, SplashLaunchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(intent);
                    finish();
                }
        );

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, SplashLaunchActivity.class));
//                finish();
//            }
//        }, 2000);

    }


}