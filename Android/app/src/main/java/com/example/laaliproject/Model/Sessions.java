package com.example.laaliproject.Model;

public class Sessions {
    String topic;
    String datetime;
    String link;
    int feedback;
    String attendance;

    public Sessions() {
    }

    public Sessions(String topic, String datetime) {
        this.topic = topic;
        this.datetime = datetime;
    }

    public Sessions(String topic, String datetime, String link) {
        this.topic = topic;
        this.datetime = datetime;
        this.link = link;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
