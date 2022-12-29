package com.example.vulcanuniversity.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    private LiveData<List<AssessmentEntity>> mAllAssessments,mAssociatedCourses;
    protected int courseId;

    public AssessmentViewModel(Application application, int courseId) {
        super(application);
        mRepository = new AppRepository(application);
        mAssociatedCourses = mRepository.getAllAssociatedAssessments(courseId);
    }
    public AssessmentViewModel(Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllAssessments = mRepository.getAllAssessments();
        mAssociatedCourses = mRepository.getAllAssociatedAssessments(courseId);
    }
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int courseId){
        return mRepository.getAllAssociatedAssessments(courseId);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }

    public void insert(AssessmentEntity a) { mRepository.insert(a); }

    public void delete(AssessmentEntity a) { mRepository.delete(a); }

    public void deleteAllAssessments() {
        mRepository.deleteAllAssessments();
    }

    public void update(AssessmentEntity a) {
        mRepository.update(a);
    }

    public int lastId(){
        return mAllAssessments.getValue().size();
    }
}
