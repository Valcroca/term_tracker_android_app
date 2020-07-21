package com.example.valeriarocac196.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.valeriarocac196.Database.TrackerRepository;
import com.example.valeriarocac196.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termId;
    private TrackerRepository mRepository;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<CourseEntity>> mAllCourses;
    public CourseViewModel(Application application, int termId){
        super(application);
        mRepository=new TrackerRepository(application);
        mAssociatedCourses=mRepository.getAllAssociatedCourses(termId);
    }
    public CourseViewModel(Application application){
        super(application);
        mRepository=new TrackerRepository(application);
        mAllCourses=mRepository.getAllCourses();
        mAssociatedCourses=mRepository.getAllAssociatedCourses(termId);
    }
    public LiveData<List<CourseEntity>> getAllAssociatedCourses(int termId){
        return mRepository.getAllAssociatedCourses(termId);
    }
    public LiveData<List<CourseEntity>> getAllCourses(){
        return mAllCourses;
    }
    public void insert(CourseEntity courseEntity){
        mRepository.insert(courseEntity);
    }
    public int lastID(){
        return mAllCourses.getValue().size();
    }
}
