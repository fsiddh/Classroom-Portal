package com.example.laaliproject.Model;

import java.util.HashMap;

public class Mentor {
    String id;
    String name;
    String email;
    HashMap<String,Student> Students;


    public Mentor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Student> getStudents() {
        return Students;
    }

    public void setStudents(HashMap<String, Student> students) {
        Students = students;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
