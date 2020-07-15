package com.example.valeriarocac196;

public class Course {
    private long courseId, courseTermId;
    private String name, startDate, endDate, status, mentorName, mentorPhone, mentorEmail;
    private int courseAlerts;

    public Course() {
        this.name = "";
        this.startDate = "";
        this.endDate = "";
        this.status = "";
        this.mentorName = "";
        this.mentorPhone = "";
        this.mentorEmail = "";
        this.courseAlerts = 0;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(long courseTermId) {
        this.courseTermId = courseTermId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public int getCourseAlerts() {
        return courseAlerts;
    }

    public void setCourseAlerts(int courseAlerts) {
        this.courseAlerts = courseAlerts;
    }
}
