package com.example.valeriarocac196;

import android.content.Intent;
import android.os.Bundle;

import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.UI.TermAdapter;
import com.example.valeriarocac196.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

public class TermActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermActivity.this, AddTermActivity.class);
                intent.putExtra("termId",mTermViewModel.lastID()+1);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //term list recycler view
        RecyclerView rv = findViewById(R.id.recyclerview);
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

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}