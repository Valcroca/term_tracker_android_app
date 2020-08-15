package com.example.valeriarocac196;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;

import com.example.valeriarocac196.Database.TrackerManagementDatabase;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.UI.TermAdapter;
import com.example.valeriarocac196.UI.TrackerAssessmentAdapter;
import com.example.valeriarocac196.UI.TrackerCourseAdapter;
import com.example.valeriarocac196.UI.TrackerTermAdapter;
import com.example.valeriarocac196.ViewModel.AssessmentViewModel;
import com.example.valeriarocac196.ViewModel.CourseViewModel;
import com.example.valeriarocac196.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TrackerActivity extends AppCompatActivity {
    private CourseViewModel mCourseViewModel;
    private TermViewModel mTermViewModel;
    private AssessmentViewModel mAssessmentViewModel;

    TrackerManagementDatabase db;

    private TextView mTotalTerms;
    private TextView mTotalCourses;
    private TextView mCoursesPlanToTake;
    private TextView mCoursesDropped;
    private TextView mCoursesInProgress;
    private TextView mCoursesCompleted;
    private TextView mTotalAssessments;
    private TextView mAssessmentsPlanToTake;
    private TextView mAssessmentsPassed;
    private TextView mAssessmentsFailed;

    List<CourseEntity> courses = new ArrayList<>();
    List<AssessmentEntity> assessments = new ArrayList<>();
    List<TermEntity> terms = new ArrayList<>();

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        db = TrackerManagementDatabase.getDatabase(getApplicationContext());

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTotalTerms = findViewById(R.id.totalTerms);
        mTotalCourses = findViewById(R.id.totalCourses);
        mCoursesPlanToTake = findViewById(R.id.coursesPlanToTake);
        mCoursesDropped = findViewById(R.id.coursesDropped);
        mCoursesInProgress = findViewById(R.id.coursesInProgress);
        mCoursesCompleted = findViewById(R.id.coursesCompleted);
        mTotalAssessments = findViewById(R.id.totalAssessments);
        mAssessmentsPlanToTake = findViewById(R.id.assessmentsPlanToTake);
        mAssessmentsFailed = findViewById(R.id.assessmentsFailed);
        mAssessmentsPassed = findViewById(R.id.assessmentsPassed);

        //terms
        final TrackerTermAdapter adapterTerm = new TrackerTermAdapter(this);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                // Update the cached copy of the words in the adapter.
                adapterTerm.setTerms(terms);
                mTotalTerms.setText("Total Terms: "+ String.valueOf(adapterTerm.getItemCount()));
            }
        });

        //courses
        final TrackerCourseAdapter adapterCourse = new TrackerCourseAdapter(this);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courses) {
                // Update the cached copy of the words in the adapter.
                adapterCourse.setCourses(courses);
                mTotalCourses.setText("Total Courses: "+ String.valueOf(adapterCourse.getItemCount()));
                mCoursesPlanToTake.setText("Plan to Take: "+ String.valueOf(adapterCourse.getCoursesPlanToTake()));
                mCoursesDropped.setText("Dropped: "+ String.valueOf(adapterCourse.getCoursesDropped()));
                mCoursesInProgress.setText("In Progress: "+ String.valueOf(adapterCourse.getCoursesInProgress()));
                mCoursesCompleted.setText("Completed: "+ String.valueOf(adapterCourse.getCoursesCompleted()));
            }
        });

        //assessments
        final TrackerAssessmentAdapter adapterAssessment = new TrackerAssessmentAdapter(this);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                // Update the cached copy of the words in the adapter.
                adapterAssessment.setAssessments(assessments);
                mTotalAssessments.setText("Total Assessments: "+ String.valueOf(adapterAssessment.getItemCount()));
                mAssessmentsPlanToTake.setText("Plan to Take: "+ String.valueOf(adapterAssessment.getAssessmentsPlanToTake()));
                mAssessmentsFailed.setText("Failed: "+ String.valueOf(adapterAssessment.getAssessmentsFailed()));
                mAssessmentsPassed.setText("Passed: "+ String.valueOf(adapterAssessment.getAssessmentsPassed()));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}