package com.joshuatheengineer.dodotodo.viewmodel;

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
                // triggers observer's change data
                mLiveNote.postValue(note);
            }
        });
    }

    /**
     * Saves note whether new or edited
     * @param noteText
     */
    public void saveNote(String noteText) {
        // if empty, end add/edit note action
        if (TextUtils.isEmpty(noteText.trim())) {
            return;
        }

        // else set new text
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            // new note

            // creates new note (shouldn't be just spaces)
            note = new NoteEntity(new Date(), noteText.trim());
        } else {
            // existing note
            note.setText(noteText);
        }
        mRepository.insertNote(note);
    }
}
