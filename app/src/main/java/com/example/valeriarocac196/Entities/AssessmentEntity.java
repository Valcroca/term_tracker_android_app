package com.example.valeriarocac196.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "assessment_table",
        foreignKeys = @ForeignKey(
                entity = CourseEntity.class,
                parentColumns = "courseId",
                childColumns = "assessmentCourseId",
                onDelete = CASCADE
        )
)

public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private int assessmentCourseId;
    private String name, status;
    private Date startDate, alertStartDate, dueDate, alertDueDate;

    public AssessmentEntity(int assessmentId, int assessmentCourseId, String name, String status, Date startDate, Date alertStartDate, Date dueDate, Date alertDueDate) {
        this.assessmentId = assessmentId;
        this.assessmentCourseId = assessmentCourseId;
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
        this.alertDueDate = alertDueDate;
        this.startDate = startDate;
        this.alertStartDate = alertStartDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getAlertStartDate() {
        return alertStartDate;
    }

    public void setAlertStartDate(Date alertStartDate) {
        this.alertStartDate = alertStartDate;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getAssessmentCourseId() {
        return assessmentCourseId;
    }

    public void setAssessmentCourseId(int assessmentCourseId) {
        this.assessmentCourseId = assessmentCourseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getAlertDueDate() {
        return alertDueDate;
    }

    public void setAlertDueDate(Date alertDueDate) {
        this.alertDueDate = alertDueDate;
    }
}
