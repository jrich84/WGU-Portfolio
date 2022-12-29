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
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> course);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("SELECT * FROM courses WHERE courseId = :courseId")
    CourseEntity getCourseById(int courseId);

    @Query("SELECT COUNT(*) FROM courses WHERE termId =:termId")
    int termCount(int termId);

    @Query("SELECT * FROM courses ORDER BY courseId")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("DELETE FROM courses")
    int deleteAllCourses();

    @Query("SELECT COUNT(*) FROM courses")
    int getCount();

    @Update
    void update(CourseEntity course);

    @Query ("SELECT * FROM courses WHERE termId=  :courseId ORDER BY termId ASC")
    LiveData<List<CourseEntity>> getAllAssociatedTerms(int courseId);
}
