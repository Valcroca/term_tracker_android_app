package com.example.valeriarocac196;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
    private Spinner mEditCourseStatus;
    private EditText mEditCourseMentorName;
    private EditText mEditCourseMentorPhone;
    private EditText mEditCourseMentorEmail;
    private EditText mEditCourseNotes;

    private int position;
    TrackerManagementDatabase db;
    long date;
    long mills;
    long date2;
    long mills2;

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
            mills = calendar.getTimeInMillis();
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
            mills2 = calendar.getTimeInMillis();
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
                if (passesValidations()) {
                    String name = mEditCourseName.getText().toString();
                    String start = mEditCourseStart.getText().toString();
                    String end = mEditCourseEnd.getText().toString();
                    String status = mEditCourseStatus.getSelectedItem().toString();
                    String mentorName = mEditCourseMentorName.getText().toString();
                    String mentorPhone = mEditCourseMentorPhone.getText().toString();
                    String mentorEmail = mEditCourseMentorEmail.getText().toString();
                    String notes = "";
                    if (mEditCourseNotes.getText() != null) {
                        notes = mEditCourseNotes.getText().toString();
                    }
                    String alertStart = mEditCourseStartAlert.getText().toString();
                    String alertEnd = mEditCourseEndAlert.getText().toString();
                    Intent intent = getIntent();
                    Bundle extras = intent.getExtras();
                    Integer courseTermId = extras.getInt("courseTermId");

                    CourseEntity tempCourse = new CourseEntity(mCourseViewModel.lastID() + 1, courseTermId, name, DateConverter.toDate(start), DateConverter.toDate(alertStart), DateConverter.toDate(end), DateConverter.toDate(alertEnd), status, mentorName, mentorPhone, mentorEmail, notes);
                    mCourseViewModel.insertCourse(tempCourse);
                    mCourseViewModel.getAllCourses();
                    //alerts
                    if (!alertEnd.isEmpty()) {
                        Intent intentCourseEnd=new Intent(AddCourseActivity.this, MyReceiverEndAdd.class);
                        intentCourseEnd.putExtra("key","Alert: Course "+name+" ENDS on "+end+"!");
                        PendingIntent sender= PendingIntent.getBroadcast(AddCourseActivity.this,0,intentCourseEnd,0);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        date2=mills2;
                        alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                    }
                    if (!alertStart.isEmpty()) {
                        Intent intentCourseStart=new Intent(AddCourseActivity.this, MyReceiverStartAdd.class);
                        intentCourseStart.putExtra("key","Alert: Course "+name+" STARTS on "+start+"!");
                        PendingIntent sender= PendingIntent.getBroadcast(AddCourseActivity.this,0,intentCourseStart,0);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        date=mills;
                        alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                    }

                    Toast.makeText(getApplicationContext(), "Course " + name + " Added!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        // status spinner code
        Spinner spin = findViewById(R.id.editCourseStatus);
        ArrayAdapter<String> courseStatusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        courseStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(courseStatusAdapter);
        spin.setOnItemSelectedListener(this);
    }

    private boolean passesValidations() {
        boolean passes = true;
        String errorMessage = "";
        String name = mEditCourseName.getText().toString();
        String start = mEditCourseStart.getText().toString();
        String end = mEditCourseEnd.getText().toString();
        String status = mEditCourseStatus.getSelectedItem().toString();
        String mentorName = mEditCourseMentorName.getText().toString();
        String mentorPhone = mEditCourseMentorPhone.getText().toString();
        String mentorEmail = mEditCourseMentorEmail.getText().toString();
        String alertStart = mEditCourseStartAlert.getText().toString();
        String alertEnd = mEditCourseEndAlert.getText().toString();

        if (name == null || name.isEmpty()) {
            errorMessage += "Name cannot be empty.\n";
        }
        if (start == null || start.isEmpty()) {
            errorMessage += "Start date cannot be empty.\n";
        }
        if (alertStart == null || alertStart.isEmpty()) {
            errorMessage += "Alert for Start cannot be empty.\n";
        }
        if (end == null || end.isEmpty()) {
            errorMessage += "End date cannot be empty.\n";
        }
        if (alertEnd == null || alertEnd.isEmpty()) {
            errorMessage += "Alert for End cannot be empty.\n";
        }
        if (status == null || status.isEmpty()) {
            errorMessage += "Status cannot be empty.\n";
        }
        if (mentorName == null || mentorName.isEmpty()) {
            errorMessage += "Mentor Name cannot be empty.\n";
        }
        if (mentorPhone == null || mentorPhone.isEmpty()) {
            errorMessage += "Mentor Phone cannot be empty.\n";
        }
        if (mentorEmail == null || mentorEmail.isEmpty()) {
            errorMessage += "Mentor Email cannot be empty.\n";
        }

        if (errorMessage.isEmpty())
            passes = true;
        else {
            passes = false;
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
        return passes;
    }

    //status spinner code
    String[] statuses = { "plan to take", "dropped", "in-progress", "completed" };

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
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
