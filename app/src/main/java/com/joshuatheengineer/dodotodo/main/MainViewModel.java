package com.joshuatheengineer.dodotodo.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.joshuatheengineer.dodotodo.database.AppRepository;
import com.joshuatheengineer.dodotodo.database.NoteEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
    }

    public void deleteAllTodoNotes() {
        mRepository.deleteNotesByStatus(0);
    }

    public void deleteAllCompletedNotes() {
        mRepository.deleteNotesByStatus(1);
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }

    public void getAllToDoNotes() {
        mNotes = mRepository.getNotesByStatus(0);
    }

    public void getAllCompletedNotes() {
        mNotes = mRepository.getNotesByStatus(1);
    }
}
