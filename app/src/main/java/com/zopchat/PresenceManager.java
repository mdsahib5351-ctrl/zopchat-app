package com.zopchat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class PresenceManager {
    public static void setOnline(boolean online) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;
        Map<String, Object> data = new HashMap<>();
        data.put("online", online);
        data.put("lastSeen", FieldValue.serverTimestamp());
        data.put("updatedAt", FieldValue.serverTimestamp());
        FirebaseFirestore.getInstance().collection("users").document(uid).update(data);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("presence").child(uid);
        Map<String, Object> rtdb = new HashMap<>();
        rtdb.put("online", online);
        rtdb.put("lastSeen", System.currentTimeMillis());
        ref.setValue(rtdb);
        if (online) {
            Map<String, Object> offline = new HashMap<>();
            offline.put("online", false);
            offline.put("lastSeen", System.currentTimeMillis());
            ref.onDisconnect().setValue(offline);
        }
    }
}
