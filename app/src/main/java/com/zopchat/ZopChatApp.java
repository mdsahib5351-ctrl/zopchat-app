package com.zopchat;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class ZopChatApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        try { FirebaseDatabase.getInstance().setPersistenceEnabled(true); } catch (Exception ignored) {}
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);
    }
}
