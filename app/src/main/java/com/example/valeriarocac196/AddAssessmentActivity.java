package com.example.valeriarocac196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddAssessmentActivity extends AppCompatActivity {
    private AssessmentViewModel mAssessmentViewModel;

    Calendar calendar = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public DatePickerDialog.OnDateSetListener assessmentDueDateListener;
    public DatePickerDialog.OnDateSetListener alarmDateListener;

    private EditText mEditAssessmentName;
    private EditText mEditAssessmentDueDate;
    private EditText mEditAssessmentAlertDate;
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
        mEditAssessmentDueDate = findViewById(R.id.editAssessmentDueDate);
        mEditAssessmentAlertDate = findViewById(R.id.editAssessmentAlertDate);

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
        mEditAssessmentAlertDate.setOnClickListener(v -> new DatePickerDialog(AddAssessmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            String aDate = month + "/" + dayOfMonth + "/" + year;
            mEditAssessmentAlertDate.setText(aDate);
        };
        //save new course button
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

                String name = mEditAssessmentName.getText().toString();
                String due = mEditAssessmentDueDate.getText().toString();
                String alert = mEditAssessmentAlertDate.getText().toString();
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                Integer assessmentCourseId = extras.getInt("assessmentCourseId");

                AssessmentEntity tempAssessment = new AssessmentEntity(mAssessmentViewModel.lastID()+1, assessmentCourseId, name, DateConverter.toDate(due), DateConverter.toDate(alert));
                mAssessmentViewModel.insertAssessment(tempAssessment);
                mAssessmentViewModel.getAllAssessments();


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
            AssessmentEntity assessment = new AssessmentEntity(mAssessmentViewModel.lastID() + 1, getIntent().getIntExtra("assessmentCourseId", 0), data.getStringExtra("assessmentName"), DateConverter.toDate(data.getStringExtra("assessmentDueDate")), DateConverter.toDate(data.getStringExtra("assessmentAlertDate")));
            mAssessmentViewModel.insertAssessment(assessment);
        }
    }
}
