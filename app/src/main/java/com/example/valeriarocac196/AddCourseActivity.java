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
import com.example.valeriarocac196.UI.CourseAdapter;
import com.example.valeriarocac196.ViewModel.CourseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {
    private CourseViewModel mCourseViewModel;

    Calendar calendar = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public DatePickerDialog.OnDateSetListener courseStartListener;
    public DatePickerDialog.OnDateSetListener courseEndListener;
    public DatePickerDialog.OnDateSetListener courseAlertDateListener;

    private EditText mEditCourseName;
    private EditText mEditCourseStart;
    private EditText mEditCourseEnd;
    private EditText mEditCourseStatus;
    private EditText mEditCourseMentorName;
    private EditText mEditCourseMentorPhone;
    private EditText mEditCourseMentorEmail;
    private EditText mEditCourseNotes;
    private EditText mEditCourseAlertDate;
    private int position;
    TrackerManagementDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        db = TrackerManagementDatabase.getDatabase(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditCourseName = findViewById(R.id.editCourseName);
        mEditCourseStart = findViewById(R.id.editCourseStart);
        mEditCourseEnd = findViewById(R.id.editCourseEnd);
        mEditCourseStatus = findViewById(R.id.editCourseStatus);
        mEditCourseMentorName = findViewById(R.id.editCourseMentorName);
        mEditCourseMentorPhone = findViewById(R.id.editCourseMentorPhone);
        mEditCourseMentorEmail = findViewById(R.id.editCourseMentorEmail);
        mEditCourseNotes = findViewById(R.id.editCourseNotes);
        mEditCourseAlertDate = findViewById(R.id.editCourseAlertDate);

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
        // Course DatePickerDialog alert date listener and functionality
        DatePickerDialog.OnDateSetListener alertDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditCourseAlertDate, calendar);
        };
        mEditCourseAlertDate = findViewById(R.id.editCourseAlertDate);
        mEditCourseAlertDate.setOnClickListener(v -> new DatePickerDialog(AddCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditCourseAlertDate, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        courseAlertDateListener = (view, year, month, dayOfMonth) -> {
            String aDate = month + "/" + dayOfMonth + "/" + year;
            mEditCourseAlertDate.setText(aDate);
        };
        //save new course button
        final CourseAdapter adapter = new CourseAdapter(this);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courses) {
                adapter.setCourses(courses);
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
                String alert = mEditCourseAlertDate.getText().toString();
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                Integer courseTermId = extras.getInt("courseTermId");

                CourseEntity tempCourse = new CourseEntity(mCourseViewModel.lastID()+1, courseTermId, name, DateConverter.toDate(start), DateConverter.toDate(end), status, mentorName, mentorPhone, mentorEmail, notes, DateConverter.toDate(alert));
                mCourseViewModel.insertCourse(tempCourse);
                mCourseViewModel.getAllCourses();
                //TODO: create an associated default assessment for each new course
//                AssessmentEntity tempAssessment = new AssessmentEntity();

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
            CourseEntity course = new CourseEntity(mCourseViewModel.lastID() + 1, getIntent().getIntExtra("courseTermId", 0), data.getStringExtra("courseName"), DateConverter.toDate(data.getStringExtra("courseStart")), DateConverter.toDate(data.getStringExtra("courseEnd")), data.getStringExtra("courseStatus"), data.getStringExtra("courseMentorName"), data.getStringExtra("courseMentorPhone"), data.getStringExtra("courseMentorEmail"), data.getStringExtra("courseNotes"), DateConverter.toDate(data.getStringExtra("courseAlertDate")));
            mCourseViewModel.insertCourse(course);
        }
    }
}
