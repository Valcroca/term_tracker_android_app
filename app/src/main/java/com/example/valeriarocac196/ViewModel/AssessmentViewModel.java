package com.example.valeriarocac196.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.valeriarocac196.Database.TrackerRepository;
import com.example.valeriarocac196.Entities.AssessmentEntity;


import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    int courseId;
    private TrackerRepository mRepository;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<AssessmentEntity>> mAssociatedAssessments;

    public AssessmentViewModel(Application application, int courseId){
        super(application);
        mRepository=new TrackerRepository(application);
        mAllAssessments=mRepository.getAllAssessments();
        mAssociatedAssessments=mRepository.getAllAssociatedAssessments(courseId);
    }
    public AssessmentViewModel(Application application){
        super(application);
        mRepository=new TrackerRepository(application);
        mAllAssessments=mRepository.getAllAssessments();
        mAssociatedAssessments=mRepository.getAllAssociatedAssessments(courseId);
    }
    public LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int courseId){
        return mRepository.getAllAssociatedAssessments(courseId);
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments(){
        return mAllAssessments;
    }

    public void updateAssessment(AssessmentEntity assessmentEntity) { mRepository.updateAssessment(assessmentEntity);}

    public void insertAssessment(AssessmentEntity assessmentEntity){ mRepository.insertAssessment(assessmentEntity); }

    public void deleteAssessment(AssessmentEntity assessmentEntity){ mRepository.deleteAssessment(assessmentEntity); }

    public int lastID(){
        return mAllAssessments.getValue().size();
    }
}
