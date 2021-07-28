package com.example.laaliproject.Model;

public class StudentAssignment {
    String question;
    String deadline;
    String grade;
    String link;
    String status;

    public StudentAssignment() {
    }

    public StudentAssignment(String question, String deadline) {
        this.question = question;
        this.deadline = deadline;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
