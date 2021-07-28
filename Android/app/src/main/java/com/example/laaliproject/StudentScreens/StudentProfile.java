package com.example.laaliproject.StudentScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laaliproject.Login;
import com.example.laaliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {
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
        setContentView(R.layout.activity_student_profile);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView1);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        logout=findViewById(R.id.button3);
        txt=findViewById(R.id.textView6);
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        uid=user.getUid();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_sessions:
                        startActivity(new Intent(getApplicationContext(), StudentPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_assignments:
                        startActivity(new Intent(getApplicationContext(), StudentAssignments.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_mentor:
                        Intent intent=new Intent(getApplicationContext(), StudentMentor.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        return true;
                }
                return false;
            }
        });

        dat.child("Mentor").child("Students").child(uid).child("name").addValueEventListener(new ValueEventListener() {
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
                startActivity(new Intent(StudentProfile.this, Login.class));
            }
        });
    }
}