package com.example.vulcanuniversity.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vulcanuniversity.database.AppRepository;
import com.example.vulcanuniversity.database.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    private LiveData<List<TermEntity>> mAllTerms;

    public TermViewModel (Application application) {
        super(application);
        mRepository = new AppRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() { return mAllTerms; }

    public void insert(TermEntity term) { mRepository.insert(term); }

    public void delete(TermEntity term) { mRepository.delete(term); }
    public void deleteTermById(int term) {mRepository.deleteTermById(term);}

    public void deleteAllTerms() {
        mRepository.deleteAllTerms();
    }

    public void update(TermEntity term) {
        mRepository.update(term);
    }


    public int lastId(){
        return mAllTerms.getValue().size();
    }

}
