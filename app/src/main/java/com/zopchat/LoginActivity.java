package com.zopchat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.google.firebase.firestore.FieldValue;
import java.util.*;

public class LoginActivity extends AppCompatActivity {
    EditText mobileEt, passwordEt; FirebaseAuth auth; FirebaseFirestore db;
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b); setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance(); db = FirebaseFirestore.getInstance();
        mobileEt = findViewById(R.id.mobileEt); passwordEt = findViewById(R.id.passwordEt);
        findViewById(R.id.loginBtn).setOnClickListener(v -> login());
        findViewById(R.id.createBtn).setOnClickListener(v -> createAccount());
        if (auth.getCurrentUser()!=null) routeNext();
    }
    void login() {
        String mobile = Utils.normalizeMobile(mobileEt.getText().toString()); String pass = passwordEt.getText().toString();
        if (!Utils.isValidIndianMobile(mobile)) { toast("Valid mobile number likho"); return; }
        if (pass.length()<6) { toast("Password minimum 6 character hona chahiye"); return; }
        auth.signInWithEmailAndPassword(Utils.mobileAuthEmail(mobile), pass)
                .addOnSuccessListener(r -> routeNext())
                .addOnFailureListener(e -> toast(e.getMessage()));
    }
    void createAccount() {
        String mobile = Utils.normalizeMobile(mobileEt.getText().toString()); String pass = passwordEt.getText().toString();
        if (!Utils.isValidIndianMobile(mobile)) { toast("Valid mobile number likho"); return; }
        if (pass.length()<6) { toast("Password minimum 6 character hona chahiye"); return; }
        db.collection("mobileNumbers").document(mobile).get().addOnSuccessListener(snap -> {
            if (snap.exists()) { toast("Ye mobile already Zop Chat par hai"); return; }
            auth.createUserWithEmailAndPassword(Utils.mobileAuthEmail(mobile), pass)
                .addOnSuccessListener(r -> {
                    String uid = auth.getUid();
                    Map<String,Object> user = new HashMap<>();
                    user.put("uid", uid); user.put("email", Utils.mobileAuthEmail(mobile)); user.put("mobile", mobile);
                    user.put("name", ""); user.put("about", "Hey there! I am using Zop Chat.");
                    user.put("photoURL", ""); user.put("isProfileComplete", false); user.put("online", true);
                    user.put("createdAt", FieldValue.serverTimestamp()); user.put("updatedAt", FieldValue.serverTimestamp()); user.put("lastSeen", FieldValue.serverTimestamp());
                    db.collection("users").document(uid).set(user, SetOptions.merge());
                    Map<String,Object> mob = new HashMap<>(); mob.put("uid", uid); mob.put("email", Utils.mobileAuthEmail(mobile)); mob.put("createdAt", FieldValue.serverTimestamp());
                    db.collection("mobileNumbers").document(mobile).set(mob, SetOptions.merge());
                    db.collection("mobileLogin").document(mobile).set(mob, SetOptions.merge());
                    startActivity(new Intent(this, ProfileActivity.class)); finish();
                }).addOnFailureListener(e -> toast(e.getMessage()));
        });
    }
    void routeNext() {
        String uid = auth.getUid(); if (uid==null) return;
        db.collection("users").document(uid).get().addOnSuccessListener(snap -> {
            Boolean done = snap.getBoolean("isProfileComplete");
            PresenceManager.setOnline(true);
            startActivity(new Intent(this, done != null && done ? MainActivity.class : ProfileActivity.class)); finish();
        });
    }
    void toast(String s){ Toast.makeText(this, s, Toast.LENGTH_LONG).show(); }
}
