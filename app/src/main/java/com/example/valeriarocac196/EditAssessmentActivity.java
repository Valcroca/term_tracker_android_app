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
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.ViewModel.AssessmentViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditAssessmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
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
    List<AssessmentEntity> filteredAssessments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

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

        final int[] assessmentId = new int[1];
        // status spinner code
        Spinner spin = findViewById(R.id.editAssessmentStatus);
        ArrayAdapter<String> assessmentStatusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        assessmentStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(assessmentStatusAdapter);
        spin.setOnItemSelectedListener(this);

        if (getIntent().getStringExtra("assessmentName") != null) {
            //setting assessment data, passed from adapter, on edit fields
            assessmentId[0] = getIntent().getExtras().getInt("assessmentId");
            mEditAssessmentName.setText(getIntent().getStringExtra("assessmentName"));
            int spinnerPosition = assessmentStatusAdapter.getPosition(getIntent().getStringExtra("courseStatus"));
            mEditAssessmentStatus.setSelection(spinnerPosition);
            String dueDateString = (String) getIntent().getExtras().get("assessmentDueDate");
            mEditAssessmentDueDate.setText(dueDateString);
            String alertDueDateString = (String) getIntent().getExtras().get("assessmentAlertDueDate");
            mEditAssessmentAlertDueDate.setText(alertDueDateString);
            String startDateString = (String) getIntent().getExtras().get("assessmentStartDate");
            mEditAssessmentStartDate.setText(startDateString);
            String alertStartDateString = (String) getIntent().getExtras().get("assessmentAlertStartDate");
            mEditAssessmentAlertStartDate.setText(alertStartDateString);
        }
        // assessment DatePickerDialog due date listener and functionality
        DatePickerDialog.OnDateSetListener dueDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentDueDate, calendar);
        };
        mEditAssessmentDueDate = findViewById(R.id.editAssessmentDueDate);
        mEditAssessmentDueDate.setOnClickListener(v -> new DatePickerDialog(EditAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentDueDate.setText(sDate);
        };
        // assessment DatePickerDialog alert due date listener and functionality
        DatePickerDialog.OnDateSetListener alertDueDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentDueDate, calendar);
        };
        mEditAssessmentAlertDueDate = findViewById(R.id.editAssessmentAlertDueDate);
        mEditAssessmentAlertDueDate.setOnClickListener(v -> new DatePickerDialog(EditAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentAlertDueDate.setText(sDate);
        };
        // assessment DatePickerDialog start date listener and functionality
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentStartDate, calendar);
        };
        mEditAssessmentStartDate = findViewById(R.id.editAssessmentStartDate);
        mEditAssessmentStartDate.setOnClickListener(v -> new DatePickerDialog(EditAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentStartDate.setText(sDate);
        };
        // assessment DatePickerDialog alert start date listener and functionality
        DatePickerDialog.OnDateSetListener alertStartDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentStartDate, calendar);
        };
        mEditAssessmentAlertStartDate = findViewById(R.id.editAssessmentAlertStartDate);
        mEditAssessmentAlertStartDate.setOnClickListener(v -> new DatePickerDialog(EditAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentAlertStartDate.setText(sDate);
        };
        //save edited course button
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                filteredAssessments.clear();
                for (AssessmentEntity a : assessments) {
                    if (a.getAssessmentCourseId() == getIntent().getIntExtra("assessmentCourseId", 0)) {
                        filteredAssessments.add(a);
                    }
                }
            }
        });
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passesValidations()) {
                    Intent replyIntent = new Intent();
                    String name = mEditAssessmentName.getText().toString();
                    String status = mEditAssessmentStatus.getSelectedItem().toString();
                    String start = mEditAssessmentStartDate.getText().toString();
                    String alertStart = mEditAssessmentAlertStartDate.getText().toString();
                    String due = mEditAssessmentDueDate.getText().toString();
                    String alertDue = mEditAssessmentAlertDueDate.getText().toString();

                    replyIntent.putExtra("assessmentName", name);
                    replyIntent.putExtra("assessmentDueDate", due);
                    replyIntent.putExtra("assessmentStartDate", start);

                    if (getIntent().getStringExtra("assessmentName") != null) {
                        int id = getIntent().getIntExtra("assessmentId", 0);
                        int courseId = getIntent().getIntExtra("assessmentCourseId", 0);
                        AssessmentEntity updatedAssessment = new AssessmentEntity(id, courseId, name, status, DateConverter.toDate(start), DateConverter.toDate(alertStart), DateConverter.toDate(due), DateConverter.toDate(alertDue));
                        mAssessmentViewModel.updateAssessment(updatedAssessment);
                    }
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
        //delete button
        final Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                assessmentId[0] = getIntent().getExtras().getInt("assessmentId");
                int assessmentCourseId = getIntent().getExtras().getInt("assessmentCourseId");
                String name = mEditAssessmentName.getText().toString();
                String status = mEditAssessmentStatus.getSelectedItem().toString();
                String start = mEditAssessmentStartDate.getText().toString();
                String alertStart = mEditAssessmentAlertStartDate.getText().toString();
                String due = mEditAssessmentDueDate.getText().toString();
                String alertDue = mEditAssessmentAlertDueDate.getText().toString();

                AssessmentEntity deletingAssessment = new AssessmentEntity(assessmentId[0], assessmentCourseId, name, status, DateConverter.toDate(due), DateConverter.toDate(start), DateConverter.toDate(alertStart), DateConverter.toDate(alertDue));
                mAssessmentViewModel.deleteAssessment(deletingAssessment);

                Toast.makeText(getApplicationContext(), name + " Was Deleted!", Toast.LENGTH_LONG).show();

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
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
}
