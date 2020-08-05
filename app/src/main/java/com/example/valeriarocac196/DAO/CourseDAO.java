package com.example.valeriarocac196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;

import java.util.List;
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Update
    void updateCourse(CourseEntity course);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("SELECT * FROM course_table WHERE courseId = :id")
    LiveData<List<CourseEntity>> getCourseById(int id);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseId ASC")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query ("SELECT * FROM course_table WHERE courseTermId= :termId ORDER BY courseID ASC")
    LiveData<List<CourseEntity>> getAllAssociatedCourses(int termId);
}
