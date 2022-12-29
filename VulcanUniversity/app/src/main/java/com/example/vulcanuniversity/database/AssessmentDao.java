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
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);
    @Delete
    void deleteAssessment(AssessmentEntity assessment);

    @Query("SELECT * FROM Assessments ORDER BY assessmentId ASC")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("DELETE FROM Assessments")
    int deleteAllAssessments();

    @Update
    void update(AssessmentEntity assessment);


    @Query ("SELECT * FROM Assessments WHERE courseId= :id ORDER BY assessmentId ASC")
    LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int id);
}
