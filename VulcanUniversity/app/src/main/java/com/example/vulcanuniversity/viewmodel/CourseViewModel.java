package com.example.vulcanuniversity.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<CourseEntity>> mAssociatedCourses;

    public CourseViewModel(Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllCourses = mRepository.getAllCourses();
    }

    public CourseViewModel(Application application, int termId) {
        super(application);
        mRepository = new AppRepository(application);
        mAllCourses = mRepository.getAllCourses();
        mAssociatedCourses = mRepository.getAllAssociatedTerms(termId);
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }
    public LiveData<List<CourseEntity>> getAllAssociatedTerms(int termId){
        return mAssociatedCourses;
    }

    public void insert(CourseEntity Course) { mRepository.insert(Course); }

    public void delete(CourseEntity Course) { mRepository.delete(Course); }

    public void deleteAllCourses() {
        mRepository.deleteAllCourses();
    }

    public void update(CourseEntity Course) {
        mRepository.update(Course);
    }

}

