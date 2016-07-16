package com.nashipaechepkurui.mavuno.app;

import com.firebase.client.Firebase;

/**
 * Created by carina on 7/16/16.
 */
public class MyApplication extends android.app.Application {
    public static final String FIREBASE_URL = "https://mavuno.firebaseio.com";
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(getBaseContext());
    }
}
