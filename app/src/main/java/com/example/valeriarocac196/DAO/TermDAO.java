package com.example.valeriarocac196.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.valeriarocac196.Entities.TermEntity;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity term);

    @Update
    void updateTerm (TermEntity termEntity);

    @Delete
    void deleteTerm(TermEntity term);

    @Query("SELECT * FROM term_table WHERE termId = :id")
    TermEntity getTermById(int id);

    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termId ASC")
    LiveData<List<TermEntity>> getAllTerms();
}
