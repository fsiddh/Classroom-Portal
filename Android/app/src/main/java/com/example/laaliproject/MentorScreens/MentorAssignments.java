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

public class MentorAssignments extends AppCompatActivity {
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
        setContentView(R.layout.activity_mentor_assignments);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView2);
        button=findViewById(R.id.floatingActionButton1);

        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        desc=findViewById(R.id.name2);
        due=findViewById(R.id.due2);
        cardView=findViewById(R.id.cv);

        bottomNav.setSelectedItemId(R.id.nav_assignments);
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
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), MentorProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MentorAssignments.this,MentorPostAssignment.class));
            }
        });

        loadAssignments();
    }
    private void loadAssignments() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("asslog",dataSnapshot.getValue().toString());
                HashMap<String,String> hm= (HashMap<String, String>) dataSnapshot.getValue();
                Log.d("asslog",hm.get("question"));
                desc.setText(hm.get("question"));
                due.setText(hm.get("deadline"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("asslog", "loadPost:onCancelled", databaseError.toException());
            }
        };
        dat.child("Mentor").child("Assignment").addValueEventListener(postListener);
    }
}