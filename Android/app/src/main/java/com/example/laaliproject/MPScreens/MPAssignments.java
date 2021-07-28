package com.example.laaliproject.MPScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laaliproject.MentorScreens.MentorAssignments;
import com.example.laaliproject.MentorScreens.MentorPostAssignment;
import com.example.laaliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MPAssignments extends AppCompatActivity {
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_assignments);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView3);
        bottomNav.setSelectedItemId(R.id.nav_assignments);
        button=findViewById(R.id.floatingActionButton2);
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
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), MPProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MPAssignments.this, MentorPostAssignment.class));
            }
        });
    }
}