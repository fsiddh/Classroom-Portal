package com.example.laaliproject.MentorScreens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.laaliproject.MPScreens.MPSessions;
import com.example.laaliproject.PostSession;
import com.example.laaliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MentorSessions extends AppCompatActivity {
    FloatingActionButton button;
    FirebaseDatabase database;
    DatabaseReference dat;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    TextView desc,due;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_sessions);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView2);
        bottomNav.setSelectedItemId(R.id.nav_sessions);
        button=findViewById(R.id.floatingActionButton4);
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        desc=findViewById(R.id.name3);
        due=findViewById(R.id.due3);
        cardView=findViewById(R.id.cv4);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_students:
                        startActivity(new Intent(getApplicationContext(), MentorPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_sessions:
                        return true;
                    case R.id.nav_assignments:
                        startActivity(new Intent(getApplicationContext(), MentorAssignments.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), MentorProfile.class));
                        return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MentorSessions.this, PostSession.class));
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("asslog",dataSnapshot.getValue().toString());
                HashMap<String,String> hm= (HashMap<String, String>) dataSnapshot.getValue();
                desc.setText(hm.get("topic"));
                due.setText(hm.get("datetime"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("asslog", "loadPost:onCancelled", databaseError.toException());
            }
        };
        dat.child("Mentor").child("Session").addValueEventListener(postListener);
    }
}