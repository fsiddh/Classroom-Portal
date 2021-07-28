package com.example.laaliproject.StudentScreens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laaliproject.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Objects;

public class SubmitAssignment extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    String url;
    FirebaseDatabase mdatabase;
    DatabaseReference det;
    TextView task;
    ImageView upload;
    Uri imageuri = null;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);
        submit=findViewById(R.id.submitass);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        mdatabase = FirebaseDatabase.getInstance();
        det = mdatabase.getReference();

        task = findViewById(R.id.taskques);
        upload=findViewById(R.id.imageView30);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                det.child("ProgramManager").child("Mentor").child("Students").child(uid).child("StudentAssignment").child("link").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        det.child("ProgramManager").child("Mentor").child("Students").child(uid).child("StudentAssignment").child("status").setValue("Submitted").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SubmitAssignment.this,"Assignment submitted",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SubmitAssignment.this,StudentAssignments.class));

                            }
                        });
                    }
                });
            }
        });
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                HashMap<String,String> hm= (HashMap<String, String>) dataSnapshot.getValue();
                Log.d("asslog",hm.get("question"));
                task.setText(hm.get("question"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("asslog", "loadPost:onCancelled", databaseError.toException());
            }
        };
        det.child("ProgramManager").child("Mentor").child("Students").child(uid).child("StudentAssignment").addValueEventListener(postListener);

    }
    ProgressDialog dialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading to Firebase");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = uid;
            Toast.makeText(SubmitAssignment.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child("students/" + messagePushID+ "/" + "assignment.pdf");
            Toast.makeText(SubmitAssignment.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        url=myurl;
                        Toast.makeText(SubmitAssignment.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        Toast.makeText(SubmitAssignment.this, myurl, Toast.LENGTH_SHORT).show();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(SubmitAssignment.this, "Uploaded Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
