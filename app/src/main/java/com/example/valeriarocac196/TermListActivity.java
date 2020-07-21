package com.example.valeriarocac196;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.UI.TermAdapter;
import com.example.valeriarocac196.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TermListActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.termsListRecyclerView);
        final TermAdapter adapter = new TermAdapter(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                // Update the cached copy of the words in the adapter.
                adapter.setTerms(terms);
            }
        });

        FloatingActionButton homeBtn = findViewById(R.id.homeButton);
        homeBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(TermListActivity.this, MainActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}