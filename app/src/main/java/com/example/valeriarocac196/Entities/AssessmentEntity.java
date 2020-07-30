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
    private String name, info, alertName, status;
    private Date dueDate, alertDate;

    public AssessmentEntity(int assessmentId, int assessmentCourseId, String name, String info, String alertName, String status, Date dueDate, Date alertDate) {
        this.assessmentId = assessmentId;
        this.assessmentCourseId = assessmentCourseId;
        this.name = name;
        this.info = info;
        this.alertName = alertName;
        this.status = status;
        this.dueDate = dueDate;
        this.alertDate = alertDate;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(Date alertDate) {
        this.alertDate = alertDate;
    }
}
