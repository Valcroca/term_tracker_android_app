package com.example.valeriarocac196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity course);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentId ASC")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query ("SELECT * FROM assessment_table WHERE assessmentCourseId= :courseId ORDER BY assessmentId ASC")
    LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int courseId);
}
