package com.example.valeriarocac196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class EditAssessmentActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private AssessmentViewModel mAssessmentViewModel;

    Calendar calendar = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public DatePickerDialog.OnDateSetListener assessmentDueDateListener;
    public DatePickerDialog.OnDateSetListener alarmDateListener;

    private EditText mEditAssessmentName;
    private EditText mEditAssessmentDueDate;
    private EditText mEditAssessmentAlertDate;
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
        mEditAssessmentDueDate = findViewById(R.id.editAssessmentDueDate);
        mEditAssessmentAlertDate = findViewById(R.id.editAssessmentAlertDate);

        final int[] assessmentId = new int[1];

        if (getIntent().getStringExtra("assessmentName") != null) {
            //setting assessment data, passed from adapter, on edit fields
            assessmentId[0] = getIntent().getExtras().getInt("assessmentId");
            mEditAssessmentName.setText(getIntent().getStringExtra("assessmentName"));
            String dueDateString = (String) getIntent().getExtras().get("assessmentDueDate");
            mEditAssessmentDueDate.setText(dueDateString);
            String alertDateString = (String) getIntent().getExtras().get("assessmentAlertDate");
            mEditAssessmentAlertDate.setText(alertDateString);
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
        // assessment DatePickerDialog alert date listener and functionality
        DatePickerDialog.OnDateSetListener alertDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditAssessmentDueDate, calendar);
        };
        mEditAssessmentAlertDate = findViewById(R.id.editAssessmentAlertDate);
        mEditAssessmentAlertDate.setOnClickListener(v -> new DatePickerDialog(EditAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditAssessmentAlertDate, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        alarmDateListener = (view, year, month, dayOfMonth) -> {
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentAlertDate.setText(sDate);
        };
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
        //save edited course button
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                String name = mEditAssessmentName.getText().toString();
                String due = mEditAssessmentDueDate.getText().toString();
                String alert = mEditAssessmentAlertDate.getText().toString();

                replyIntent.putExtra("assessmentName", name);
                replyIntent.putExtra("assessmentDueDate", due);
                replyIntent.putExtra("assessmentAlertDate", alert);

                if (getIntent().getStringExtra("assessmentName") != null) {
                    int id = getIntent().getIntExtra("assessmentId", 0);
                    int courseId = getIntent().getIntExtra("assessmentCourseId", 0);
                    AssessmentEntity updatedAssessment = new AssessmentEntity(id, courseId, name, DateConverter.toDate(due), DateConverter.toDate(alert));
                    mAssessmentViewModel.updateAssessment(updatedAssessment);
                }
                setResult(RESULT_OK, replyIntent);
                finish();
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
                String due = mEditAssessmentDueDate.getText().toString();
                String alert = mEditAssessmentAlertDate.getText().toString();

                if (filteredAssessments.size() > 1) {
                    AssessmentEntity deletingAssessment = new AssessmentEntity(assessmentId[0], assessmentCourseId, name, DateConverter.toDate(due), DateConverter.toDate(alert));
                    mAssessmentViewModel.deleteAssessment(deletingAssessment);

                    Toast.makeText(getApplicationContext(), name + " Was Deleted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Could not delete the assessment. You must have at least one assessment per course.", Toast.LENGTH_LONG).show();
                }
                setResult(RESULT_OK, replyIntent);
                finish();
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
