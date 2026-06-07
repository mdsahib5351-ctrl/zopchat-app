package com.zopchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        new Handler(Looper.getMainLooper()).postDelayed(this::routeNext, 900);
    }

    void routeNext() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        String uid = auth.getUid();
        db.collection("users").document(uid).get().addOnSuccessListener(snap -> {
            Boolean done = snap.getBoolean("isProfileComplete");
            startActivity(new Intent(this, done != null && done ? MainActivity.class : ProfileActivity.class));
            finish();
        }).addOnFailureListener(e -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
