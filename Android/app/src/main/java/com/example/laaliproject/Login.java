package com.example.laaliproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laaliproject.MPScreens.PMpage;
import com.example.laaliproject.MentorScreens.MentorPage;
import com.example.laaliproject.StudentScreens.StudentPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText entpass;
    EditText entemail;
    FirebaseAuth firebaseAuth;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        entpass=findViewById(R.id.passlogin);
        entemail=findViewById(R.id.emaillogin);
        firebaseAuth=FirebaseAuth.getInstance();

        login=findViewById(R.id.loginuser);

        //Login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=entemail.getText().toString().trim();
                final String pass=entpass.getText().toString().trim();

                //Checking for constraints and removing blank spaces
                if(email.isEmpty()){
                    entemail.setError("Email is required");
                    entemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    entemail.setError("Enter valid email");
                    entemail.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    entpass.setError("Email is required");
                    entpass.requestFocus();
                    return;
                }
                if(pass.length()<6){
                    entpass.setError("Password length should be a minimum of 6");
                    entpass.requestFocus();
                    return;
                }

                //authentication with Firebase
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this,"Sign in successful",Toast.LENGTH_SHORT).show();
                            //On success open user home page
                            if(email.equals("program.manager@laali.com")){
                                Intent intent=new Intent(Login.this, PMpage.class);
                                startActivity(intent);
                            }
                            else if(email.equals("mentor@laali.com")){
                                Intent intent=new Intent(Login.this, MentorPage.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent=new Intent(Login.this, StudentPage.class);
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(Login.this,"Failed to log in. Please check your credentials.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}