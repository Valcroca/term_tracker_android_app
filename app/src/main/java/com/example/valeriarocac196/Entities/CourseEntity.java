package com.example.valeriarocac196.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;


@Entity(
        tableName = "course_table",
        foreignKeys = @ForeignKey(
                entity = TermEntity.class,
                parentColumns = "termId",
                childColumns = "courseTermId",
                onDelete = CASCADE
        )
)
public class CourseEntity {
    @PrimaryKey
    private int courseId;
    private int courseTermId;
    private String name, status, mentorName, mentorPhone, mentorEmail, notes;
    private Date startDate;
    private Date endDate;
    private Date courseAlertDate;

    public CourseEntity(int courseId, int courseTermId, String name, Date startDate, Date endDate, String status, String mentorName, String mentorPhone, String mentorEmail, String notes, Date courseAlertDate) {
        this.courseId = courseId;
        this.courseTermId = courseTermId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.notes = notes;
        this.courseAlertDate = courseAlertDate;
    }

    public CourseEntity(int i, int courseTermId, String courseName, Date courseStart, Date courseEnd) {
        this.courseId = courseId;
        this.courseTermId = courseTermId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCourseAlertDate() {
        return courseAlertDate;
    }

    public void setCourseAlertDate(Date courseAlertDate) {
        this.courseAlertDate = courseAlertDate;
    }
}
