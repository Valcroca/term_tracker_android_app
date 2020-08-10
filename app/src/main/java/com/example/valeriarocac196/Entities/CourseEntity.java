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
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int courseTermId;
    private String name, status, mentorName, mentorPhone, mentorEmail, notes;
    private Date startDate, startDateAlert, endDate, endDateAlert;

    public CourseEntity(int courseId, int courseTermId, String name, Date startDate, Date startDateAlert, Date endDate, Date endDateAlert, String status, String mentorName, String mentorPhone, String mentorEmail, String notes) {
        this.courseId = courseId;
        this.courseTermId = courseTermId;
        this.name = name;
        this.startDate = startDate;
        this.startDateAlert = startDateAlert;
        this.endDate = endDate;
        this.endDateAlert = endDateAlert;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.notes = notes;

    }

    public Date getStartDateAlert() {
        return startDateAlert;
    }

    public void setStartDateAlert(Date startDateAlert) {
        this.startDateAlert = startDateAlert;
    }

    public Date getEndDateAlert() {
        return endDateAlert;
    }

    public void setEndDateAlert(Date endDateAlert) {
        this.endDateAlert = endDateAlert;
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

}
