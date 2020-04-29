package com.joshuatheengineer.dodotodo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.joshuatheengineer.dodotodo.database.AppRepository;
import com.joshuatheengineer.dodotodo.database.NoteEntity;
import com.joshuatheengineer.dodotodo.utilities.SampleData;

import java.util.List;

/**
 * ViewModels store and manage UI Related data in the lifecycle
 */
public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;

    // passes the app repo and notes
    public MainViewModel(@NonNull Application application) {
        super(application);

        // create an app repository instance to pass data
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
    }

    /**
     * Will add sample data
     */
    public void addSampleData() {
        mRepository.addSampleData();
    }

    /**
     * Will delete all data
     */
    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
