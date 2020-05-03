package com.joshuatheengineer.dodotodo.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.joshuatheengineer.dodotodo.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;
    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        // want to filter 'To Do' first
        mNotes = getNotesByStatus(0);
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertAll(SampleData.getNotes());
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteAll();
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return mDb.noteDao().getNoteById(noteId);
    }

    public void insertNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertNote(note);
            }
        });
    }

    public void deleteNote(final NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteNote(note);
            }
        });
    }

    /**
     * Get notes by completion status filter
     * 0 is to do
     * 1 is complete
     */
    public void deleteNotesByStatus(final int status) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteNotesByStatus(status);
            }
        });
    }

    /**
     * Get notes based off completion
     * 0 is to do
     * 1 is complete
     */
    public LiveData<List<NoteEntity>> getNotesByStatus(int status) {
        return mDb.noteDao().getNotesByStatus(status);
    }
}
