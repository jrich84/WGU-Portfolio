package com.example.vulcanuniversity.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.MentorEntity;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    private LiveData<List<MentorEntity>> mAllMentors;
    private LiveData<List<MentorEntity>> mAssociatedMentors;
    protected int courseId;

    public MentorViewModel(Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllMentors = mRepository.getAllMentors();
    }
    public MentorViewModel(Application application, int courseId) {
        super(application);
        mRepository = new AppRepository(application);
        mAllMentors = mRepository.getAllMentors();
        mAssociatedMentors = mRepository.getAllAssociatedMentors(courseId);
    }

    public LiveData<List<MentorEntity>> getAssociatedMentors(int courseId){
        return mRepository.getAllAssociatedMentors(courseId);
    }
    public LiveData<List<MentorEntity>> getAllMentors() {
        return mAllMentors;
    }

    public void insert(MentorEntity a) { mRepository.insert(a); }

    public void delete(MentorEntity a) { mRepository.delete(a); }

    public void deleteAllMentors() {
        mRepository.deleteAllMentors();
    }

    public void update(MentorEntity a) {
        mRepository.update(a);
    }

    public int lastId(){
        return mAllMentors.getValue().size();
    }
}
