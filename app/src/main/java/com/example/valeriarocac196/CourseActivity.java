package com.example.valeriarocac196;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.UI.CourseAdapter;
import com.example.valeriarocac196.ViewModel.CourseViewModel;
import com.example.valeriarocac196.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private TermViewModel mTermViewModel;
    private CourseViewModel mCourseViewModel;
    private EditText mEditTitle;
    private EditText mEditStart;
    private EditText mEditEnd;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditTitle= findViewById(R.id.editTitle);
        mEditStart= findViewById(R.id.editStart);
        mEditEnd= findViewById(R.id.editEnd);
        if(getIntent().getStringExtra("termTitle")!=null) {
            mEditTitle.setText(getIntent().getStringExtra("termTitle"));
            mEditStart.setText(getIntent().getStringExtra("termStart"));
            mEditEnd.setText(getIntent().getStringExtra("termEnd"));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseActivity.this, CourseDetail.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.associated_courses);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courses) {
                List<CourseEntity> filteredCourses=new ArrayList<>();
                for(CourseEntity c:courses)if(c.getCourseId()==getIntent().getIntExtra("courseId",0))courses.add(c);
                adapter.setCourses(filteredCourses);
            }
        });

        final Button button=findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                String title = mEditTitle.getText().toString();
                String start = mEditStart.getText().toString();
                String end = mEditEnd.getText().toString();

                replyIntent.putExtra("termTitle", title);
                replyIntent.putExtra("termStart", start);
                replyIntent.putExtra("termEnd", end);
                if(getIntent().getStringExtra("termTitle")!=null) {
                    int id=getIntent().getIntExtra("termId",0);
                    TermEntity term = new TermEntity(id, title, start, end, false);
                    mTermViewModel.insert(term);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {

            CourseEntity course = new CourseEntity(mCourseViewModel.lastID()+1, getIntent().getIntExtra("termId",0), data.getStringExtra("courseName"), data.getStringExtra("courseStart"),  data.getStringExtra("courseEnd"));
            mCourseViewModel.insert(course);
        }
    }
}
