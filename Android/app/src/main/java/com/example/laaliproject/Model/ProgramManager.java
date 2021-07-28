package com.example.laaliproject.Model;

public class ProgramManager {
    String name;
    String id;
    Mentor Mentor;

    public ProgramManager() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public com.example.laaliproject.Model.Mentor getMentor() {
        return Mentor;
    }

    public void setMentor(com.example.laaliproject.Model.Mentor mentor) {
        Mentor = mentor;
    }
}
