package com.joshuatheengineer.dodotodo.noteeditor;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.joshuatheengineer.dodotodo.database.AppRepository;
import com.joshuatheengineer.dodotodo.database.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
            }
        });
    }

    /**
     * Update what will be saved in note
     */
    public void saveNote(String noteText, String name, boolean status, int numofunits, int goalofunits, String typeofunits) {
        // 'checkForNonEmptyFields' won't save note if empty text (except for 'note text')
        if (checkForNonEmptyFields(name, typeofunits)) {
            NoteEntity note = mLiveNote.getValue();
            int completionstatus = status ? 1 : 0; // 0 is false, 1 is true
            if (note == null) {
                note = new NoteEntity(new Date(), noteText.trim(), name, completionstatus, numofunits, goalofunits, typeofunits);
            } else {
                note.setText(noteText);
                note.setName(name);
                note.setStatus(completionstatus);
                note.setNumofunits(numofunits);
                note.setGoalofunits(goalofunits);
                note.setTypeofunits(typeofunits);
            }
            mRepository.insertNote(note);
        }
    }

    /**
     * If one of the below params are empty, return false
     * @param name
     * @param typeofunits
     * @return
     */
    private boolean checkForNonEmptyFields(String name, String typeofunits){
        return !TextUtils.isEmpty(name.trim()) &&
                !TextUtils.isEmpty(typeofunits.trim());
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
