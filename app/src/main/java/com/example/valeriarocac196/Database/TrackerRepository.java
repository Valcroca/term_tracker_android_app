package com.example.valeriarocac196.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.valeriarocac196.DAO.AssessmentDAO;
import com.example.valeriarocac196.DAO.CourseDAO;
import com.example.valeriarocac196.DAO.TermDAO;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;

import java.util.List;

public class TrackerRepository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private LiveData<List<TermEntity>> mAllTerms;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<CourseEntity>> mAllAssociatedCourses;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<AssessmentEntity>> mAllAssociatedAssessments;
    private int termId;
    private int courseId;

    public TrackerRepository(Application application) {
        TrackerManagementDatabase db = TrackerManagementDatabase.getDatabase(application);
        mTermDAO = db.termDAO();
        mAllTerms = mTermDAO.getAllTerms();
        mCourseDAO = db.courseDAO();
        mAllCourses = mCourseDAO.getAllCourses();
        mAllAssociatedCourses = mCourseDAO.getAllAssociatedCourses(termId);
        mAssessmentDAO = db.assessmentDAO();
        mAllAssessments = mAssessmentDAO.getAllAssessments();
        mAllAssociatedAssessments = mAssessmentDAO.getAllAssociatedAssessments(courseId);
    }

    public LiveData<List<TermEntity>> getAllTerms() { return mAllTerms; };
    public LiveData<List<CourseEntity>> getAllCourses() { return mAllCourses; };
    public LiveData<List<CourseEntity>> getAllAssociatedCourses(int termId) { return mAllAssociatedCourses; };
    public LiveData<List<AssessmentEntity>> getAllAssessments() { return mAllAssessments; };
    public LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int courseId) { return mAllAssociatedAssessments; }

    public void insert (TermEntity termEntity) {
        new insertAsyncTask(mTermDAO).execute(termEntity);
    }

    private static class insertAsyncTask extends AsyncTask<TermEntity, Void, Void> {

        private TermDAO mAsyncTaskDao;

        insertAsyncTask(TermDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TermEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert (CourseEntity courseEntity) {
        new insertAsyncTask1(mCourseDAO).execute(courseEntity);
    }

    private static class insertAsyncTask1 extends AsyncTask<CourseEntity, Void, Void> {

        private CourseDAO mAsyncTaskDao;

        insertAsyncTask1(CourseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CourseEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insert(AssessmentEntity assessmentEntity) {
        new insertAsyncTask2(mAssessmentDAO).execute(assessmentEntity);
    }

    private static class insertAsyncTask2 extends AsyncTask<AssessmentEntity, Void, Void> {

        private AssessmentDAO mAsyncTaskDao;

        insertAsyncTask2(AssessmentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AssessmentEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
