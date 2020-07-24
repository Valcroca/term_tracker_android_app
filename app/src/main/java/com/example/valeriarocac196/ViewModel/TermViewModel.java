package com.example.valeriarocac196.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.valeriarocac196.Database.TrackerRepository;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.TermEditActivity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private TrackerRepository mRepository;
    private LiveData<List<TermEntity>> mAllTerms;
    public LiveData<TermEntity> mTerm;

    public TermViewModel(Application application){
        super(application);
        mRepository=new TrackerRepository(application);
        mAllTerms=mRepository.getAllTerms();

    }

    public LiveData<List<CourseEntity>> getAllAssociatedCourses(int termId){
        return mRepository.getAllAssociatedCourses(termId);
    }

    public LiveData<List<TermEntity>> getAllTerms(){
        return mAllTerms;
    }

    public void insertTerm(TermEntity termEntity){
        mRepository.insert(termEntity);
    }

    public void updateTerm(TermEntity termEntity) {
        mRepository.updateTerm(termEntity);
    }

    public int lastID(){
        return mAllTerms.getValue().size();
    }
}
