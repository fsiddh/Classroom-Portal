package com.example.laaliproject.MPScreens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.laaliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PMpage extends AppCompatActivity {
    TextView mname,memail;
    CardView cardView;
    FirebaseDatabase database;
    DatabaseReference dat;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmpage);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView3);
        bottomNav.setSelectedItemId(R.id.nav_mentors);

        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        mname=findViewById(R.id.name6);
        memail=findViewById(R.id.due6);
        cardView=findViewById(R.id.cv6);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_mentors:
                        return true;
                    case R.id.nav_sessions:
                        Intent intent=new Intent(getApplicationContext(),MPSessions.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_assignments:
                        startActivity(new Intent(getApplicationContext(), MPAssignments.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), MPProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PMpage.this,MPMentorStudents.class));
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("asslog",dataSnapshot.getValue().toString());
                HashMap<String,String> hm= (HashMap<String, String>) dataSnapshot.getValue();
                mname.setText(hm.get("name"));
                memail.setText(hm.get("email"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("asslog", "loadPost:onCancelled", databaseError.toException());
            }
        };
        dat.child("Mentor").addValueEventListener(postListener);
    }
}