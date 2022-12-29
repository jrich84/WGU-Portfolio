package com.example.vulcanuniversity.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    private LiveData<List<NoteEntity>> mAllNotes;
    private LiveData<List<NoteEntity>> mAssociatedNotes;
    protected int courseId;

    public NoteViewModel(Application application, int courseId) {
        super(application);
        mRepository = new AppRepository(application);
        mAssociatedNotes = mRepository.getAllAssociatedNotes(courseId);
    }
    public NoteViewModel(Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllNotes = mRepository.getAllNotes();
        mAssociatedNotes = mRepository.getAllAssociatedNotes(courseId);
    }

    public LiveData<List<NoteEntity>> getAssociatedNotes(int courseId){
        return mRepository.getAllAssociatedNotes(courseId);
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(NoteEntity n) {
        mRepository.insert(n);
    }

    public void delete(NoteEntity n) { mRepository.delete(n); }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }

    public void update(NoteEntity n) {
        mRepository.update(n);
    }


    public int lastId(){
        return mAllNotes.getValue().size();
    }

}
