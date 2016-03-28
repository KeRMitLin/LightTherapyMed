package com.kermitlin.lighttherapymed;

import com.firebase.client.Firebase;

public class LightTherapyMedApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
