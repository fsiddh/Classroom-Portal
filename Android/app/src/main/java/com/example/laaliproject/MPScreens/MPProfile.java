package com.example.laaliproject.MPScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laaliproject.Login;
import com.example.laaliproject.MentorScreens.MentorProfile;
import com.example.laaliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MPProfile extends AppCompatActivity {
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
        setContentView(R.layout.activity_m_p_profile);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView3);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        logout=findViewById(R.id.button301);
        txt=findViewById(R.id.textView101);
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        uid=user.getUid();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_mentors:
                        startActivity(new Intent(getApplicationContext(), PMpage.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                }
                return false;
            }
        });
        dat.child("name").addValueEventListener(new ValueEventListener() {
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
                startActivity(new Intent(MPProfile.this, Login.class));
            }
        });
    }
}