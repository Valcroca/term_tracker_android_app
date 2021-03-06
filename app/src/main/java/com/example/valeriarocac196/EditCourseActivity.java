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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.UI.AssessmentAdapter;
import com.example.valeriarocac196.ViewModel.AssessmentViewModel;
import com.example.valeriarocac196.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
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
    List<AssessmentEntity> filteredAssessments = new ArrayList<>();
    long date;
    long date2;
    long millsStart;
    long millsEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

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

        Date courseStart;
        Date courseEnd;
        Date courseAlertDate;
        final int[] courseId = new int[1];
        // status spinner code
        Spinner spin = findViewById(R.id.editCourseStatus);
        ArrayAdapter<String> courseStatusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        courseStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(courseStatusAdapter);
        spin.setOnItemSelectedListener(this);

        if (getIntent().getStringExtra("courseName") != null) {
            //setting course data, passed from adapter, on edit fields
            courseId[0] = getIntent().getExtras().getInt("courseId");
            mEditCourseName.setText(getIntent().getStringExtra("courseName"));
            String startString = getIntent().getStringExtra("courseStart");
            mEditCourseStart.setText(startString);
            String startAlertString = getIntent().getStringExtra("courseStartAlert");
            mEditCourseStartAlert.setText(startAlertString);
            String endString = getIntent().getStringExtra("courseEnd");
            mEditCourseEnd.setText(endString);
            String endAlertString = getIntent().getStringExtra("courseEndAlert");
            mEditCourseEndAlert.setText(endAlertString);
            int spinnerPosition = courseStatusAdapter.getPosition(getIntent().getStringExtra("courseStatus"));
            mEditCourseStatus.setSelection(spinnerPosition);
            mEditCourseMentorName.setText(getIntent().getStringExtra("courseMentorName"));
            mEditCourseMentorPhone.setText(getIntent().getStringExtra("courseMentorPhone"));
            mEditCourseMentorEmail.setText(getIntent().getStringExtra("courseMentorEmail"));
            mEditCourseNotes.setText(getIntent().getStringExtra("courseNotes"));

        }
        // Course DatePickerDialog start date listener and functionality
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditCourseStart, calendar);
        };
        mEditCourseStart = findViewById(R.id.editCourseStart);
        mEditCourseStart.setOnClickListener(v -> new DatePickerDialog(EditCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            DateConverter.updateDateText(mEditCourseStart, calendar);
        };
        mEditCourseEnd = findViewById(R.id.editCourseEnd);
        mEditCourseEnd.setOnClickListener(v -> new DatePickerDialog(EditCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        mEditCourseStartAlert.setOnClickListener(v -> new DatePickerDialog(EditCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditCourseStartAlert, calendar);
                millsStart = calendar.getTimeInMillis();
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
        mEditCourseEndAlert.setOnClickListener(v -> new DatePickerDialog(EditCourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditCourseEndAlert, calendar);
                millsEnd = calendar.getTimeInMillis();
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        courseEndAlertDateListener = (view, year, month, dayOfMonth) -> {
            String aDate = month + "/" + dayOfMonth + "/" + year;
            mEditCourseEndAlert.setText(aDate);
        };

        //add assessment button
        FloatingActionButton fab = findViewById(R.id.add_assessment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //limit 5 assessments per course
                if (filteredAssessments.size() <= 4) {
                    Intent intent = new Intent(EditCourseActivity.this, AddAssessmentActivity.class);
                    intent.putExtra("assessmentCourseId", courseId[0]);
                    startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "This course already has reached the 5 assessments limit! ", Toast.LENGTH_LONG).show();
                }
            }
        });

        //course's assessments recycler view
        RecyclerView assessmentsRecyclerView = findViewById(R.id.associated_assessments);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentsRecyclerView.setAdapter(assessmentAdapter);
        assessmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                filteredAssessments.clear();
                for (AssessmentEntity a : assessments) {
                    if (a.getAssessmentCourseId() == getIntent().getIntExtra("courseId", 0)) {
                        filteredAssessments.add(a);
                    }
                }
                assessmentAdapter.setAssessments(filteredAssessments);
            }
        });

        //save edited course button
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passesValidations()) {
                    Intent replyIntent = new Intent();
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

                    replyIntent.putExtra("courseName", name);
                    replyIntent.putExtra("courseStart", start);
                    replyIntent.putExtra("courseEnd", end);
                    replyIntent.putExtra("status", status);
                    replyIntent.putExtra("mentorName", mentorName);
                    replyIntent.putExtra("mentorPhone", mentorPhone);
                    replyIntent.putExtra("mentorEmail", mentorEmail);
                    replyIntent.putExtra("notes", notes);
                    replyIntent.putExtra("courseStartAlert", alertStart);
                    replyIntent.putExtra("courseEndAlert", alertEnd);

                    int id = getIntent().getIntExtra("courseId", 0);
                    int termId = getIntent().getIntExtra("courseTermId", 0);
                    CourseEntity updatedCourse = new CourseEntity(id, termId, name, DateConverter.toDate(start), DateConverter.toDate(alertStart), DateConverter.toDate(end), DateConverter.toDate(alertEnd), status, mentorName, mentorPhone, mentorEmail, notes);
                    mCourseViewModel.updateCourse(updatedCourse);
                    //alerts
                    if (!alertEnd.isEmpty()) {
                        Intent intent2=new Intent(EditCourseActivity.this, MyReceiverEnd.class);
                        intent2.putExtra("key","Alert: Course "+name+" ENDS on "+end+"!");
                        PendingIntent sender2= PendingIntent.getBroadcast(EditCourseActivity.this,0,intent2,0);
                        AlarmManager alarmManager2=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        date2=millsEnd;
                        alarmManager2.set(AlarmManager.RTC_WAKEUP, date2, sender2);
                    }
                    if (!alertStart.isEmpty()) {
                        Intent intent=new Intent(EditCourseActivity.this, MyReceiverStart.class);
                        intent.putExtra("key","Alert: Course "+name+" STARTS on "+start+"!");
                        PendingIntent sender= PendingIntent.getBroadcast(EditCourseActivity.this,0,intent,0);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        date=millsStart;
                        alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
                    }

                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        //delete course button
        final Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                courseId[0] = getIntent().getExtras().getInt("courseId");
                int courseTermId = getIntent().getExtras().getInt("courseTermId");
                String name = mEditCourseName.getText().toString();
                String start = mEditCourseStart.getText().toString();
                String end = mEditCourseEnd.getText().toString();
                String status = mEditCourseStatus.getSelectedItem().toString();
                String mentorName = mEditCourseMentorName.getText().toString();
                String mentorPhone = mEditCourseMentorPhone.getText().toString();
                String mentorEmail = mEditCourseMentorEmail.getText().toString();
                String notes = mEditCourseNotes.getText().toString();
                String alertStart = mEditCourseStartAlert.getText().toString();
                String alertEnd = mEditCourseEndAlert.getText().toString();
                if (filteredAssessments.isEmpty()) {
                    CourseEntity deletingCourse = new CourseEntity(courseId[0], courseTermId, name, DateConverter.toDate(start), DateConverter.toDate(alertStart), DateConverter.toDate(end), DateConverter.toDate(alertEnd), status, mentorName, mentorPhone, mentorEmail, notes);
                    mCourseViewModel.deleteCourse(deletingCourse);

                    Toast.makeText(getApplicationContext(), name + " Was Deleted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), name + " Has Assessments! It Cannot Be Deleted.", Toast.LENGTH_LONG).show();
                }
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
        //Share button
        final Button shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mEditCourseNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
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
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
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
        if(resultCode==RESULT_OK) {

            AssessmentEntity assessment = new AssessmentEntity(mAssessmentViewModel.lastID()+1, getIntent().getIntExtra("courseId",0), data.getStringExtra("assessmentName"), data.getStringExtra("assessmentStatus"), data.getStringExtra("assessmentType"), DateConverter.toDate(data.getStringExtra("startDate")),  DateConverter.toDate(data.getStringExtra("alertStartDate")), DateConverter.toDate(data.getStringExtra("dueDate")),  DateConverter.toDate(data.getStringExtra("alertDueDate")));
            mAssessmentViewModel.insertAssessment(assessment);
        }
    }

}
