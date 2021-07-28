package com.example.laaliproject.StudentScreens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.laaliproject.MentorScreens.MentorAssignments;
import com.example.laaliproject.MentorScreens.MentorProfile;
import com.example.laaliproject.MentorScreens.MentorSessions;
import com.example.laaliproject.Model.StudentAssignment;
import com.example.laaliproject.R;
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

public class StudentPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dat;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    TextView desc,due;
    CardView cardView;
    Button attend,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentpage);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView1);
        bottomNav.setSelectedItemId(R.id.nav_sessions);
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        desc=findViewById(R.id.name5);
        due=findViewById(R.id.due5);
        cardView=findViewById(R.id.cv5);

        attend=findViewById(R.id.attend);
        feedback=findViewById(R.id.feedback);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_sessions:
                        return true;
                    case R.id.nav_assignments:
                        Intent intent=new Intent(getApplicationContext(), StudentAssignments.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_mentor:
                        startActivity(new Intent(getApplicationContext(), StudentMentor.class));
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

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dat.child("Mentor").child("Students").child(uid).child("Session").child("attendance").setValue("Attended").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(StudentPage.this,"Attendance marked",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentPage.this);
                builder.setTitle("Enter feedback number (1-5)");

                final EditText input = new EditText(StudentPage.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        dat.child("Mentor").child("Students").child(uid).child("Session").child("feedback").setValue(m_Text).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(StudentPage.this,"Feedback submitted",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
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
        dat.child("Mentor").child("Students").child(uid).child("Session").addValueEventListener(postListener);

    }
}