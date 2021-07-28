package com.example.laaliproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laaliproject.MPScreens.MPSessions;
import com.example.laaliproject.MentorScreens.MentorPostAssignment;
import com.example.laaliproject.MentorScreens.MentorSessions;
import com.example.laaliproject.Model.Sessions;
import com.example.laaliproject.Model.Student;
import com.example.laaliproject.Model.StudentAssignment;
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

public class PostSession extends AppCompatActivity {
    EditText topic,time;
    Button post;
    String topics,times;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    FirebaseDatabase mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_session);
        topic=findViewById(R.id.sesstopic);
        time=findViewById(R.id.sessdate);
        post=findViewById(R.id.postsess);

        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        uid=user.getUid();
        mdatabase= FirebaseDatabase.getInstance();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topics=topic.getText().toString().trim();
                times=time.getText().toString().trim();

                if(topics.isEmpty()){
                    topic.setError("Topic is required");
                    topic.requestFocus();
                    return;
                }
                if(times.isEmpty()){
                    time.setError("Timings is required");
                    time.requestFocus();
                    return;
                }
                Sessions s = new Sessions(topics,times);
                if(uid.equals("UX7lYk4fVVWC7algP6h3A43jles2")) {
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get Post object and use the values to update the UI
                            Log.d("post", dataSnapshot.getValue().toString());
                            HashMap<String, Student> hm = (HashMap<String, Student>) dataSnapshot.getValue();
                            Log.d("post", hm.toString());
                            for (Map.Entry<String, Student> entry : hm.entrySet()) {
                                mdatabase.getReference().child("ProgramManager").child("Mentor").child("Students").child(entry.getKey()).child("Session").setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(PostSession.this, "Session Posted", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(PostSession.this, MentorSessions.class));
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
                    mdatabase.getReference().child("ProgramManager").child("Mentor").child("Session").setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(PostSession.this, "Mentor Session Posted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PostSession.this, MPSessions.class));
                            return;
                        }
                    });
                }
            }
        });
    }

}