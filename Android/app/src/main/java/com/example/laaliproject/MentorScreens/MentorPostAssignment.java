package com.example.laaliproject.MentorScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laaliproject.MPScreens.MPAssignments;
import com.example.laaliproject.Model.Student;
import com.example.laaliproject.Model.StudentAssignment;
import com.example.laaliproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MentorPostAssignment extends AppCompatActivity {
    EditText task,due;
    Button post;
    String tasks,dues;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    FirebaseDatabase mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_post_assignment);
        task=findViewById(R.id.task);
        due=findViewById(R.id.duedate);
        post=findViewById(R.id.postassignment);

        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        uid=user.getUid();
        Log.d("postass",uid);

        mdatabase= FirebaseDatabase.getInstance();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks=task.getText().toString().trim();
                dues=due.getText().toString().trim();

                if(tasks.isEmpty()){
                    task.setError("Company name is required");
                    task.requestFocus();
                    return;
                }
                if(dues.isEmpty()){
                    due.setError("Position is required");
                    due.requestFocus();
                    return;
                }
                StudentAssignment sA = new StudentAssignment(tasks,dues);
                if(uid.equals("UX7lYk4fVVWC7algP6h3A43jles2")) {
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get Post object and use the values to update the UI
                            Log.d("post", dataSnapshot.getValue().toString());
                            HashMap<String, Student> hm = (HashMap<String, Student>) dataSnapshot.getValue();
                            Log.d("post", hm.toString());
                            for (Map.Entry<String, Student> entry : hm.entrySet()) {
                                mdatabase.getReference().child("ProgramManager").child("Mentor").child("Students").child(entry.getKey()).child("StudentAssignment").setValue(sA).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MentorPostAssignment.this, "Assignment Posted", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MentorPostAssignment.this,MentorAssignments.class));
                                        return;
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.w("post", "loadPost:onCancelled", databaseError.toException());
                        }
                    };
                    mdatabase.getReference().child("ProgramManager").child("Mentor").child("Students").addValueEventListener(postListener);
                }
                else if(uid.equals("0p0swzy96nXbOd8nejde71LPwF33")){
                    mdatabase.getReference().child("ProgramManager").child("Mentor").child("Assignment").setValue(sA).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MentorPostAssignment.this, "Mentor Assignment Posted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MentorPostAssignment.this, MPAssignments.class));
                            return;
                        }
                    });
                }
            }
        });

    }
}