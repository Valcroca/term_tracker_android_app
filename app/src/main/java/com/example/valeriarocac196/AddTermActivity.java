package com.example.valeriarocac196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.UI.TermAdapter;
import com.example.valeriarocac196.ViewModel.TermViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTermActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private TermViewModel mTermViewModel;

    Calendar calendar = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public DatePickerDialog.OnDateSetListener termStartListener;
    public DatePickerDialog.OnDateSetListener termEndListener;

    private EditText mEditTermTitle;
    private EditText mEditTermStart;
    private EditText mEditTermEnd;
    private int position;
    TrackerManagementDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        db = TrackerManagementDatabase.getDatabase(getApplicationContext());

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
        int termId;

        // Term DatePickerDialog start date listener and functionality
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            DateConverter.updateDateText(mEditTermStart, calendar);
        };
        mEditTermStart = findViewById(R.id.editTermStart);
        mEditTermStart.setOnClickListener(v -> new DatePickerDialog(AddTermActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        mEditTermEnd.setOnClickListener(v -> new DatePickerDialog(AddTermActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        final TermAdapter adapter = new TermAdapter(this);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                // Update the cached copy of the words in the adapter.
                adapter.setTerms(terms);
            }
        });
        //save new term button
        final Button button=findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermEntity tempTerm1 = new TermEntity( mTermViewModel.lastID()+1, mEditTermTitle.getText().toString(), DateConverter.toDate(mEditTermStart.getText().toString()),  DateConverter.toDate(mEditTermEnd.getText().toString()), false);
                mTermViewModel.insertTerm(tempTerm1);
                mTermViewModel.getAllTerms();
                //go back to term list
                Intent intent = new Intent(AddTermActivity.this, TermActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
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
            TermEntity term = new TermEntity( data.getStringExtra("termTitle"), DateConverter.toDate(data.getStringExtra("termStart")),  DateConverter.toDate(data.getStringExtra("termEnd")), false);
            mTermViewModel.insertTerm(term);
        }
    }
}
