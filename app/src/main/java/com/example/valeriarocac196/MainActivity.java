package com.example.valeriarocac196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button allTermsBtn = findViewById(R.id.allTermsButton);
        allTermsBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, TermActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
        Button trackerBtn = findViewById(R.id.progressTrackerButton);
        trackerBtn.setOnClickListener((view) -> {
            Intent intent2 = new Intent(MainActivity.this, TrackerActivity.class);
            startActivityForResult(intent2, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
 }

    @Override
    protected void onResume() {
        super.onResume();
       }
}
