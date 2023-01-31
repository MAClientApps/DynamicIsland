package com.lock;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lock.background.LruBitmapCache;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.sdk.perelander.Mob;
import com.sdk.perelander.MobConfig;

public class MyOneApplication extends Application {

    private static AppOpenManager appOpenManager;

    public static SharedPreferences sharedPreferencesInApp;
    public static SharedPreferences.Editor editorInApp;

    public static final String TAG = "LauncherApp";
    private static MyOneApplication mInstance;
    LruBitmapCache mLruBitmapCache;
    private RequestQueue mRequestQueue;
    public MobConfig config;

    public static synchronized MyOneApplication getInstance() {
        MyOneApplication launcherApp;
        synchronized (MyOneApplication.class) {
            launcherApp = mInstance;
        }
        return launcherApp;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;

        config = new MobConfig(this, "68sbv51dsrgg");
        Mob.onCreate(config);
        registerActivityLifecycleCallbacks(new MobLifecycleCallbacks());

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {}
                });

        appOpenManager = new AppOpenManager(this);

        sharedPreferencesInApp = getSharedPreferences("my", MODE_PRIVATE);
        editorInApp = sharedPreferencesInApp.edit();

    }

    private static final class MobLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            Mob.onResume(activity);
        }
        @Override
        public void onActivityPaused(Activity activity) {
            Mob.onPause();
        }
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }
        @Override
        public void onActivityStarted(@NonNull Activity activity) {
        }
        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }
        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }
        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    }


    public static void setuser_balance(Integer user_balance) {
        editorInApp.putInt("user_balance", user_balance).commit();
    }
    public static Integer getuser_balance() {
        return sharedPreferencesInApp.getInt("user_balance", 0);
    }

    public static void setuser_onetime(Integer user_onetime) {
        editorInApp.putInt("user_onetime", user_onetime).commit();
    }
    public static Integer getuser_onetime() {
        return sharedPreferencesInApp.getInt("user_onetime", 0);
    }



    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (this.mLruBitmapCache == null) {
            this.mLruBitmapCache = new LruBitmapCache();
        }
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }
}
