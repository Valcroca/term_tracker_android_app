package com.example.valeriarocac196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.valeriarocac196.Entities.CourseEntity;

import java.util.List;
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query ("SELECT * FROM course_table WHERE courseTermId= :termId ORDER BY courseID ASC")
    LiveData<List<CourseEntity>> getAllAssociatedCourses(int termId);
}