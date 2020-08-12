package com.example.valeriarocac196;

import android.app.DatePickerDialog;
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

import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.Database.TrackerManagementDatabase;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.UI.AssessmentAdapter;
import com.example.valeriarocac196.ViewModel.AssessmentViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddAssessmentActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private AssessmentViewModel mAssessmentViewModel;

    Calendar calendar = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public DatePickerDialog.OnDateSetListener assessmentDueDateListener;
    public DatePickerDialog.OnDateSetListener alarmDueDateListener;
    public DatePickerDialog.OnDateSetListener assessmentStartDateListener;
    public DatePickerDialog.OnDateSetListener alarmStartDateListener;

    private EditText mEditAssessmentName;
    private Spinner mEditAssessmentStatus;
    private EditText mEditAssessmentDueDate;
    private EditText mEditAssessmentAlertDueDate;
    private EditText mEditAssessmentStartDate;
    private EditText mEditAssessmentAlertStartDate;
    private int position;
    TrackerManagementDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        db = TrackerManagementDatabase.getDatabase(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditAssessmentName = findViewById(R.id.editAssessmentName);
        mEditAssessmentStatus = findViewById(R.id.editAssessmentStatus);
        mEditAssessmentDueDate = findViewById(R.id.editAssessmentDueDate);
        mEditAssessmentAlertDueDate = findViewById(R.id.editAssessmentAlertDueDate);
        mEditAssessmentStartDate = findViewById(R.id.editAssessmentStartDate);
        mEditAssessmentAlertStartDate = findViewById(R.id.editAssessmentAlertStartDate);

        // assessment DatePickerDialog due date listener and functionality
        DatePickerDialog.OnDateSetListener dueDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentDueDate, calendar);
        };
        mEditAssessmentDueDate = findViewById(R.id.editAssessmentDueDate);
        mEditAssessmentDueDate.setOnClickListener(v -> new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditAssessmentDueDate, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        assessmentDueDateListener = (view, year, month, dayOfMonth) -> {
            String dDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentDueDate.setText(dDate);
        };
        // assessment DatePickerDialog alert due date listener and functionality
        DatePickerDialog.OnDateSetListener alertDueDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentDueDate, calendar);
        };
        mEditAssessmentAlertDueDate = findViewById(R.id.editAssessmentAlertDueDate);
        mEditAssessmentAlertDueDate.setOnClickListener(v -> new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditAssessmentAlertDueDate, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        alarmDueDateListener = (view, year, month, dayOfMonth) -> {
            String aDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentAlertDueDate.setText(aDate);
        };
        // assessment DatePickerDialog start date listener and functionality
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentStartDate, calendar);
        };
        mEditAssessmentStartDate = findViewById(R.id.editAssessmentStartDate);
        mEditAssessmentStartDate.setOnClickListener(v -> new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditAssessmentStartDate, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        assessmentStartDateListener = (view, year, month, dayOfMonth) -> {
            String dDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentStartDate.setText(dDate);
        };
        // assessment DatePickerDialog alert due date listener and functionality
        DatePickerDialog.OnDateSetListener alertStartDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentStartDate, calendar);
        };
        mEditAssessmentAlertStartDate = findViewById(R.id.editAssessmentAlertStartDate);
        mEditAssessmentAlertStartDate.setOnClickListener(v -> new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditAssessmentAlertStartDate, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        alarmStartDateListener = (view, year, month, dayOfMonth) -> {
            String aDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentAlertStartDate.setText(aDate);
        };
        //save new assessment button
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessments) {
                adapter.setAssessments(assessments);
            }
        });
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (passesValidations()) {
                    String name = mEditAssessmentName.getText().toString();
                    String status = mEditAssessmentStatus.getSelectedItem().toString();
                    String start = mEditAssessmentStartDate.getText().toString();
                    String alertStart = mEditAssessmentAlertStartDate.getText().toString();
                    String due = mEditAssessmentDueDate.getText().toString();
                    String alertDue = mEditAssessmentAlertDueDate.getText().toString();
                    Intent intent = getIntent();
                    Bundle extras = intent.getExtras();
                    Integer assessmentCourseId = extras.getInt("assessmentCourseId");

                    AssessmentEntity tempAssessment = new AssessmentEntity(mAssessmentViewModel.lastID() + 1, assessmentCourseId, name, status, DateConverter.toDate(start), DateConverter.toDate(alertStart), DateConverter.toDate(due), DateConverter.toDate(alertDue));
                    mAssessmentViewModel.insertAssessment(tempAssessment);
                    mAssessmentViewModel.getAllAssessments();
                    finish();
                }
            }
        });
        // status spinner code
        Spinner spin = findViewById(R.id.editAssessmentStatus);
        ArrayAdapter<String> assessmentStatusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        assessmentStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(assessmentStatusAdapter);
        spin.setOnItemSelectedListener(this);
    }
    private boolean passesValidations() {
        boolean passes = true;
        String errorMessage = "";
        String name = mEditAssessmentName.getText().toString();
        String status = mEditAssessmentStatus.getSelectedItem().toString();
        String start = mEditAssessmentStartDate.getText().toString();
        String alertStart = mEditAssessmentAlertStartDate.getText().toString();
        String end = mEditAssessmentDueDate.getText().toString();
        String alertEnd = mEditAssessmentAlertDueDate.getText().toString();

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
            errorMessage += "Due date cannot be empty.\n";
        }
        if (alertEnd == null || alertEnd.isEmpty()) {
            errorMessage += "Alert for Due date cannot be empty.\n";
        }
        if (status == null || status.isEmpty()) {
            errorMessage += "Status cannot be empty.\n";
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
    String[] statuses = { "plan to take", "passed", "failed" };
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
        if (resultCode == RESULT_OK) {
            AssessmentEntity assessment = new AssessmentEntity(mAssessmentViewModel.lastID() + 1, getIntent().getIntExtra("assessmentCourseId", 0), data.getStringExtra("assessmentName"), data.getStringExtra("assessmentStatus"), DateConverter.toDate(data.getStringExtra("assessmentStartDate")), DateConverter.toDate(data.getStringExtra("assessmentAlertStartDate")), DateConverter.toDate(data.getStringExtra("assessmentDueDate")), DateConverter.toDate(data.getStringExtra("assessmentAlertDueDate")));
            mAssessmentViewModel.insertAssessment(assessment);
        }
    }
}
