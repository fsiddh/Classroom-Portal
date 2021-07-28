package com.example.laaliproject.MPScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.laaliproject.ItemClickListener;
import com.example.laaliproject.Model.Student;
import com.example.laaliproject.R;
import com.example.laaliproject.Student_ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MPMentorStudents extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dat;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Student, Student_ViewHolder> userAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_mentor_students);
        database = FirebaseDatabase.getInstance();
        dat = database.getReference("ProgramManager");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.rv4);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadUsers();
    }

    private void loadUsers() {
        Log.d("mentorlog","load users");
        userAdapter = new FirebaseRecyclerAdapter<Student, Student_ViewHolder>(Student.class,
                R.layout.studentcard,
                Student_ViewHolder.class,
                dat.child("Mentor").child("Students").orderByKey()
        ) {
            @Override
            protected void populateViewHolder(Student_ViewHolder viewholder, Student user, int i) {
                Log.d("mentorlog","populate viewholder");
                Log.d("mentorlog",user.getName());
                int a=user.getAge();
                String age1= String.valueOf(a);
                Log.d("mentorlog",age1);
                viewholder.uname.setText(user.getName());
                viewholder.age.setText(age1);

                viewholder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //on click on student
                    }
                });
            }
        };
        Log.d("mentorlog","set adapter");
        recyclerView.setAdapter(userAdapter);
        Log.d("mentorlog","set adapter done");

    }
}