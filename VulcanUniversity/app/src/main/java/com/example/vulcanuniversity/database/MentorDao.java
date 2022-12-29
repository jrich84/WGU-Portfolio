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
public interface MentorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MentorEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MentorEntity> mentor);

    @Delete
    void deleteMentor(MentorEntity mentor);

    @Query("SELECT * FROM mentors WHERE mentorId = :mentorId")
    MentorEntity getMentorById(int mentorId);

    @Query("SELECT COUNT(*) FROM mentors WHERE mentorId =:mentorId")
    int termCount(int mentorId);

    @Query("SELECT * FROM mentors ORDER BY mentorId")
    LiveData<List<MentorEntity>> getAllMentors();

    @Query("DELETE FROM mentors")
    int deleteAllMentors();

    @Query("SELECT COUNT(*) FROM mentors")
    int getCount();

    @Update
    void update(MentorEntity mentors);

    @Query("SELECT * FROM mentors WHERE courseId=  :courseId ORDER BY courseId ASC")
    LiveData<List<MentorEntity>> getAllAssociatedMentors(int courseId);
}