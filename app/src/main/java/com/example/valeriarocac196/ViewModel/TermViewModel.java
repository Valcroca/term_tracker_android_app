package com.example.valeriarocac196.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.valeriarocac196.Database.TrackerRepository;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
//    int termId;
    private TrackerRepository mRepository;
//    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<TermEntity>> mAllTerms;
//    public TermViewModel(Application application, int termId){
//        super(application);
//        mRepository=new TrackerRepository(application);
//        mAssociatedCourses=mRepository.getAllAssociatedCourses(termId);
//    }
    public TermViewModel(Application application){
        super(application);
        mRepository=new TrackerRepository(application);
        mAllTerms=mRepository.getAllTerms();
//        mAssociatedCourses=mRepository.getAllAssociatedCourses(termId);
    }
    public LiveData<List<CourseEntity>> getAllAssociatedCourses(int termId){
        return mRepository.getAllAssociatedCourses(termId);
    }
    public LiveData<List<TermEntity>> getAllTerms(){
        return mAllTerms;
    }
    public void insert(TermEntity termEntity){
        mRepository.insert(termEntity);
    }
    public int lastID(){
        return mAllTerms.getValue().size();
    }
}
