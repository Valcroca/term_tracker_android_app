package com.example.valeriarocac196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.Database.TrackerManagementDatabase;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.UI.AssessmentAdapter;
import com.example.valeriarocac196.UI.CourseAdapter;
import com.example.valeriarocac196.ViewModel.AssessmentViewModel;
import com.example.valeriarocac196.ViewModel.CourseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {
    private CourseViewModel mCourseViewModel;
    private AssessmentViewModel mAssessmentViewModel;

    Calendar calendar = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public DatePickerDialog.OnDateSetListener courseStartListener;
    public DatePickerDialog.OnDateSetListener courseEndListener;
    public DatePickerDialog.OnDateSetListener courseStartAlertDateListener;
    public DatePickerDialog.OnDateSetListener courseEndAlertDateListener;

    private EditText mEditCourseName;
    private EditText mEditCourseStart;
    private EditText mEditCourseStartAlert;
    private EditText mEditCourseEnd;
    private EditText mEditCourseEndAlert;
    private EditText mEditCourseStatus;
    private EditText mEditCourseMentorName;
    private EditText mEditCourseMentorPhone;
    private EditText mEditCourseMentorEmail;
    private EditText mEditCourseNotes;

    private int position;
    TrackerManagementDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        db = TrackerManagementDatabase.getDatabase(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditCourseName = findViewById(R.id.editCourseName);
        mEditCourseStart = findViewById(R.id.editCourseStart);
        mEditCourseStartAlert = findViewById(R.id.editStartAlert);
        mEditCourseEnd = findViewById(R.id.editCourseEnd);
        mEditCourseEndAlert = findViewById(R.id.editEndAlert);
        mEditCourseStatus = findViewById(R.id.editCourseStatus);
        mEditCourseMentorName = findViewById(R.id.editCourseMentorName);
        mEditCourseMentorPhone = findViewById(R.id.editCourseMentorPhone);
        mEditCourseMentorEmail = findViewById(R.id.editCourseMentorEmail);
        mEditCourseNotes = findViewById(R.id.editCourseNotes);


        // Course DatePickerDialog start date listener and functionality
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditCourseStart, calendar);
        };
        mEditCourseStart = findViewById(R.id.editCourseStart);
        mEditCourseStart.setOnClickListener(v -> new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditCourseStart, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        courseStartListener = (view, year, month, dayOfMonth) -> {
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditCourseStart.setText(sDate);
        };
        // Course DatePickerDialog end date listener and functionality
        DatePickerDialog.OnDateSetListener endDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditCourseEnd, calendar);
        };
        mEditCourseEnd = findViewById(R.id.editCourseEnd);
        mEditCourseEnd.setOnClickListener(v -> new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditCourseEnd, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        courseEndListener = (view, year, month, dayOfMonth) -> {
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditCourseStart.setText(sDate);
        };
        // Course DatePickerDialog start alert date listener and functionality
        DatePickerDialog.OnDateSetListener startAlert = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditCourseStartAlert, calendar);
        };
        mEditCourseStartAlert = findViewById(R.id.editStartAlert);
        mEditCourseStartAlert.setOnClickListener(v -> new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditCourseStartAlert, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        courseStartAlertDateListener = (view, year, month, dayOfMonth) -> {
            String aDate = month + "/" + dayOfMonth + "/" + year;
            mEditCourseStartAlert.setText(aDate);
        };
        // Course DatePickerDialog end alert date listener and functionality
        DatePickerDialog.OnDateSetListener endAlert = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditCourseEndAlert, calendar);
        };
        mEditCourseEndAlert = findViewById(R.id.editEndAlert);
        mEditCourseEndAlert.setOnClickListener(v -> new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditCourseEndAlert, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        courseEndAlertDateListener = (view, year, month, dayOfMonth) -> {
            String aDate = month + "/" + dayOfMonth + "/" + year;
            mEditCourseEndAlert.setText(aDate);
        };
        //save new course button
        final CourseAdapter adapter = new CourseAdapter(this);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courses) {
                adapter.setCourses(courses);
            }
        });
        final AssessmentAdapter adapter2 = new AssessmentAdapter(this);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessments) {
                adapter2.setAssessments(assessments);
            }
        });
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mEditCourseName.getText().toString();
                String start = mEditCourseStart.getText().toString();
                String end = mEditCourseEnd.getText().toString();
                String status = mEditCourseStatus.getText().toString();
                String mentorName = mEditCourseMentorName.getText().toString();
                String mentorPhone = mEditCourseMentorPhone.getText().toString();
                String mentorEmail = mEditCourseMentorEmail.getText().toString();
                String notes = mEditCourseNotes.getText().toString();
                String alertStart = mEditCourseStartAlert.getText().toString();
                String alertEnd = mEditCourseEndAlert.getText().toString();
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                Integer courseTermId = extras.getInt("courseTermId");

                CourseEntity tempCourse = new CourseEntity(mCourseViewModel.lastID()+1, courseTermId, name, DateConverter.toDate(start), DateConverter.toDate(alertStart), DateConverter.toDate(end), DateConverter.toDate(alertEnd), status, mentorName, mentorPhone, mentorEmail, notes);
                mCourseViewModel.insertCourse(tempCourse);
                mCourseViewModel.getAllCourses();

                Toast.makeText(getApplicationContext(), "Course " + name + " Added!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            CourseEntity course = new CourseEntity(mCourseViewModel.lastID() + 1, getIntent().getIntExtra("courseTermId", 0), data.getStringExtra("courseName"), DateConverter.toDate(data.getStringExtra("courseStart")), DateConverter.toDate(data.getStringExtra("courseStartAlert")), DateConverter.toDate(data.getStringExtra("courseEnd")), DateConverter.toDate(data.getStringExtra("courseEndAlert")), data.getStringExtra("courseStatus"), data.getStringExtra("courseMentorName"), data.getStringExtra("courseMentorPhone"), data.getStringExtra("courseMentorEmail"), data.getStringExtra("courseNotes"));
            mCourseViewModel.insertCourse(course);
        }
    }
}
