package com.zopchat;

import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth; import com.google.firebase.firestore.*; import java.util.*;

public class ProfileActivity extends AppCompatActivity {
    EditText nameEt, mobileEt, aboutEt; FirebaseFirestore db; String uid;
    @Override protected void onCreate(Bundle b){ super.onCreate(b); setContentView(R.layout.activity_profile);
        db=FirebaseFirestore.getInstance(); uid=FirebaseAuth.getInstance().getUid();
        nameEt=findViewById(R.id.nameEt); mobileEt=findViewById(R.id.mobileEt); aboutEt=findViewById(R.id.aboutEt);
        db.collection("users").document(uid).get().addOnSuccessListener(s->{
            nameEt.setText(s.getString("name")); mobileEt.setText(s.getString("mobile"));
            String about=s.getString("about"); aboutEt.setText(about==null?"Hey there! I am using Zop Chat.":about);
        });
        findViewById(R.id.saveBtn).setOnClickListener(v->save());
    }
    void save(){
        String name=nameEt.getText().toString().trim(), mobile=Utils.normalizeMobile(mobileEt.getText().toString()), about=aboutEt.getText().toString().trim();
        if(name.isEmpty()){toast("Name likho");return;} if(!Utils.isValidIndianMobile(mobile)){toast("Valid mobile likho");return;}
        Map<String,Object> data=new HashMap<>(); data.put("name",name); data.put("mobile",mobile); data.put("about",about); data.put("isProfileComplete",true); data.put("online",true); data.put("lastSeen",FieldValue.serverTimestamp()); data.put("updatedAt",FieldValue.serverTimestamp());
        db.collection("users").document(uid).set(data, SetOptions.merge()).addOnSuccessListener(x->{
            Map<String,Object> mob=new HashMap<>(); mob.put("uid",uid); mob.put("email",FirebaseAuth.getInstance().getCurrentUser().getEmail()); mob.put("updatedAt",FieldValue.serverTimestamp());
            db.collection("mobileNumbers").document(mobile).set(mob, SetOptions.merge());
            PresenceManager.setOnline(true); startActivity(new Intent(this, MainActivity.class)); finish();
        }).addOnFailureListener(e->toast(e.getMessage()));
    }
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
}
