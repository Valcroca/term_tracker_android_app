package com.example.valeriarocac196.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.valeriarocac196.Database.TrackerRepository;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termId;
    private TrackerRepository mRepository;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<CourseEntity>> mAllCourses;

    public CourseViewModel(Application application){
        super(application);
        mRepository=new TrackerRepository(application);
        mAllCourses=mRepository.getAllCourses();
        mAssociatedCourses=mRepository.getAllAssociatedCourses(termId);
    }
    public LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int courseId){
        return mRepository.getAllAssociatedAssessments(courseId);
    }
    public LiveData<List<CourseEntity>> getAllCourses(){
        return mAllCourses;
    }

    public void insertCourse(CourseEntity courseEntity){
        mRepository.insertCourse(courseEntity);
    }

    public void updateCourse(CourseEntity courseEntity){ mRepository.updateCourse(courseEntity); }

    public void deleteCourse(CourseEntity courseEntity){ mRepository.deleteCourse(courseEntity); }

    public int lastID(){
        return mAllCourses.getValue().size();
    }
}
