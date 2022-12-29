package com.example.vulcanuniversity.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity termEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);

    @Delete
    void deleteTerm(TermEntity termEntity);

    @Query("DELETE FROM terms WHERE id = :termId")
    void deleteTermById(int termId);

    @Query("SELECT * FROM terms WHERE id = :id")
    TermEntity getTermById(int id);

    @Query("SELECT * FROM terms ORDER BY id")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("DELETE FROM terms")
    int deleteAllTerms();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();

    @Update
    void update(TermEntity term);
}
