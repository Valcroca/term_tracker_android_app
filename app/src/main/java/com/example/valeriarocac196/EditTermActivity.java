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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.UI.CourseAdapter;
import com.example.valeriarocac196.ViewModel.CourseViewModel;
import com.example.valeriarocac196.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTermActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private CourseViewModel mCourseViewModel;

    //term data display/input elements
    private TermViewModel mTermViewModel;

    Calendar calendar = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public DatePickerDialog.OnDateSetListener termStartListener;
    public DatePickerDialog.OnDateSetListener termEndListener;

    private EditText mEditTermTitle;
    private EditText mEditTermStart;
    private EditText mEditTermEnd;
    private int position;
    List<CourseEntity> filteredCourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditTermTitle = findViewById(R.id.editTermTitle);
        mEditTermStart = findViewById(R.id.editTermStart);
        mEditTermEnd = findViewById(R.id.editTermEnd);

        Date termStart;
        Date termEnd;
        final int[] termId = new int[1];

        if (getIntent().getStringExtra("termTitle") != null) {
            //setting term data, passed from adapter, on edit fields
            termId[0] = getIntent().getExtras().getInt("termId");
            mEditTermTitle.setText(getIntent().getStringExtra("termTitle"));
            String startString = getIntent().getStringExtra("termStart");
            mEditTermStart.setText(startString);
            String endString = getIntent().getStringExtra("termEnd");
            mEditTermEnd.setText(endString);
        }
        // Term DatePickerDialog start date listener and functionality
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditTermStart, calendar);
        };
        mEditTermStart = findViewById(R.id.editTermStart);
        mEditTermStart.setOnClickListener(v -> new DatePickerDialog(EditTermActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditTermStart, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        termStartListener = (view, year, month, dayOfMonth) -> {
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditTermStart.setText(sDate);
        };
        // Term DatePickerDialog end date listener and functionality
        DatePickerDialog.OnDateSetListener endDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditTermStart, calendar);
        };
        mEditTermEnd = findViewById(R.id.editTermEnd);
        mEditTermEnd.setOnClickListener(v -> new DatePickerDialog(EditTermActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateConverter.updateDateText(mEditTermEnd, calendar);
            }
        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH)).show());
        termEndListener = (view, year, month, dayOfMonth) -> {
            String sDate = month + "/" + dayOfMonth + "/" + year;
            mEditTermStart.setText(sDate);
        };

        //add course button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTermActivity.this, AddCourseActivity.class);
                intent.putExtra("courseTermId", termId[0]);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //term's courses recycler view
        RecyclerView coursesRecyclerView = findViewById(R.id.associated_courses);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        coursesRecyclerView.setAdapter(courseAdapter);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courses) {
                filteredCourses.clear();
                for (CourseEntity c : courses) {
                    if (c.getCourseTermId() == getIntent().getIntExtra("termId", 0)) {
                        filteredCourses.add(c);
                    }
                courseAdapter.setCourses(filteredCourses);
                }

            }
        });

        //save edited term button
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passesValidations()) {
                    Intent replyIntent = new Intent();
                    String title = mEditTermTitle.getText().toString();
                    String start = mEditTermStart.getText().toString();
                    String end = mEditTermEnd.getText().toString();

                    replyIntent.putExtra("termTitle", title);
                    replyIntent.putExtra("termStart", start);
                    replyIntent.putExtra("termEnd", end);

                    int id = getIntent().getIntExtra("termId", 0);
                    TermEntity updatedTerm = new TermEntity(id, title, DateConverter.toDate(start), DateConverter.toDate(end), false);
                    mTermViewModel.updateTerm(updatedTerm);

                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });

        //delete term button
        final Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                termId[0] = getIntent().getExtras().getInt("termId");
                String title = mEditTermTitle.getText().toString();
                String start = mEditTermStart.getText().toString();
                String end = mEditTermEnd.getText().toString();
                if (filteredCourses.isEmpty()) {
                    TermEntity deletingTerm = new TermEntity(termId[0], title, DateConverter.toDate(start), DateConverter.toDate(end), false );
                    mTermViewModel.deleteTerm(deletingTerm);

                    Toast.makeText(getApplicationContext(), title + " Was Deleted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), title + " Has Courses! It Cannot Be Deleted.", Toast.LENGTH_LONG).show();
                }
                setResult(RESULT_OK, replyIntent);
                finish();
            }

        });
    }

    private boolean passesValidations() {
        boolean passes = true;
        String errorMessage = "";
        String title = mEditTermTitle.getText().toString();
        Date start = DateConverter.toDate(mEditTermStart.getText().toString());
        Date end = DateConverter.toDate(mEditTermEnd.getText().toString());

        if (title == null || title.isEmpty()) {
            errorMessage += "Title cannot be empty.\n";
        }
        if (start == null) {
            errorMessage += "Start date cannot be empty.\n";
        }
        if (end == null) {
            errorMessage += "End date cannot be empty.\n";
        }

        if (errorMessage.isEmpty())
            passes = true;
        else {
            passes = false;
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
        return passes;
    }

    @Override
    public boolean onSupportNavigateUp(){
         onBackPressed();
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {

            CourseEntity course = new CourseEntity(mCourseViewModel.lastID()+1, getIntent().getIntExtra("termId",0), data.getStringExtra("courseName"), DateConverter.toDate(data.getStringExtra("courseStart")),  DateConverter.toDate(data.getStringExtra("courseStartAlert")),  DateConverter.toDate(data.getStringExtra("courseEnd")), DateConverter.toDate(data.getStringExtra("courseEndAlert")),  getIntent().getStringExtra("status"), getIntent().getStringExtra("mentorName"), getIntent().getStringExtra("mentorPhone"), getIntent().getStringExtra("mentorEmail"), getIntent().getStringExtra("notes"));
            mCourseViewModel.insertCourse(course);
        }
    }
}
