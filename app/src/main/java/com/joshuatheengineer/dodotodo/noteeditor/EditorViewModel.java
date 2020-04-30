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

    public void saveNote(String noteText) {
        if (!TextUtils.isEmpty(noteText.trim())) {
            NoteEntity note = mLiveNote.getValue();
            if (note == null) {
                note = new NoteEntity(new Date(), noteText.trim());
            } else {
                note.setText(noteText);
            }
            mRepository.insertNote(note);
        }
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
