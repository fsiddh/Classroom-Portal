package com.example.laaliproject.StudentScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laaliproject.Assignments_ViewHolder;
import com.example.laaliproject.ItemClickListener;
import com.example.laaliproject.MentorScreens.MentorPostAssignment;
import com.example.laaliproject.Model.Student;
import com.example.laaliproject.Model.StudentAssignment;
import com.example.laaliproject.R;
import com.example.laaliproject.Student_ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class StudentAssignments extends AppCompatActivity {
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
        setContentView(R.layout.activity_student_assignments);

        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        desc=findViewById(R.id.name);
        due=findViewById(R.id.due);
        cardView=findViewById(R.id.cv);

        //recyclerView = (RecyclerView) findViewById(R.id.rv2);
        //recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView1);
        bottomNav.setSelectedItemId(R.id.nav_assignments);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_sessions:
                        startActivity(new Intent(getApplicationContext(), StudentPage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_assignments:
                        return true;
                    case R.id.nav_mentor:
                        Intent intent=new Intent(getApplicationContext(), StudentMentor.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), StudentProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        Log.d("asslog","load users");
        loadAssignments();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentAssignments.this,SubmitAssignment.class);
                intent.putExtra("SID",uid);
                startActivity(intent);
            }
        });
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
        dat.child("Mentor").child("Students").child(uid).child("StudentAssignment").addValueEventListener(postListener);
    }

}
