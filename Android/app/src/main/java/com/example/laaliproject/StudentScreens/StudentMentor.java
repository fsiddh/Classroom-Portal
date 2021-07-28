package com.example.laaliproject.StudentScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.laaliproject.Model.Mentor;
import com.example.laaliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class StudentMentor extends AppCompatActivity {
    TextView mentor,emailt;
    FirebaseDatabase database;
    DatabaseReference dat;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mentor);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView1);
        bottomNav.setSelectedItemId(R.id.nav_mentor);
        mentor=findViewById(R.id.mentor);
        emailt=findViewById(R.id.email);
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_sessions:
                        startActivity(new Intent(getApplicationContext(), StudentPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_assignments:
                        Intent intent=new Intent(getApplicationContext(), StudentAssignments.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_mentor:
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), StudentProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("smlog",dataSnapshot.getValue().toString());
                String name=dataSnapshot.child("name").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                mentor.setText(name);
                emailt.setText(email);
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