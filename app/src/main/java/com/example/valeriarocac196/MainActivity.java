package com.example.valeriarocac196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    DBHelper myHelper;
    private static final String TAG = "MainActivity";
    static ArrayList<Term> allTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new DBHelper(MainActivity.this);
        myHelper.getWritableDatabase();
        //display DB name
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName(), Toast.LENGTH_SHORT).show();
        //create Term table
        myHelper.createTable("term_table");

        Button allTermsBtn = findViewById(R.id.allTermsButton);
        allTermsBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, TermListActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        myHelper.close();
        Toast.makeText(MainActivity.this, myHelper.getDatabaseName() + " closed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //read
        try {
            allTerms = myHelper.readRecords("SELECT * FROM term_table");
            for(Term term : allTerms) {
                Log.d(TAG, term.getTermId() + ", " + term.getTitle() + ", " + term.getStartDate() + ", " + term.getEndDate() + ", " + term.getCurrent() );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
        //create
//        myHelper.addRecord("title", "1st Term", "start", "2020-01-01", "end", "2020-06-30", "current", false);
//        myHelper.addRecord("title", "2nd Term", "start", "2020-07-01", "end", "2020-12-31", "current", false);

        //delete
//        myHelper.deleteRecord("DELETE FROM term_table WHERE id = 1");
//
        //update
//            String[] whereArgs = {"5"};
//            int rows = myHelper.changesRecord("term_table", true, "id = ?", whereArgs);
//            Toast.makeText(MainActivity.this, "Rows updated: " + rows, Toast.LENGTH_SHORT).show();
    }
}
