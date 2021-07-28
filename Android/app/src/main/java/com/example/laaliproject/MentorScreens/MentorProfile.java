package com.example.laaliproject.MentorScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laaliproject.Login;
import com.example.laaliproject.R;
import com.example.laaliproject.StudentScreens.StudentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MentorProfile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    Button logout;
    TextView txt;
    FirebaseDatabase database;
    DatabaseReference dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_profile);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView2);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        logout=findViewById(R.id.button300);
        txt=findViewById(R.id.textView100);
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        uid=user.getUid();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_students:
                        startActivity(new Intent(getApplicationContext(), MentorPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_sessions:
                        Intent intent=new Intent(getApplicationContext(), MentorSessions.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_assignments:
                        startActivity(new Intent(getApplicationContext(), MentorAssignments.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        return true;
                }
                return false;
            }
        });
        dat.child("Mentor").child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txt.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(MentorProfile.this, Login.class));
            }
        });
    }
}