package com.joshuatheengineer.dodotodo.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.joshuatheengineer.dodotodo.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repositories are used to decide where the true data source is.
 *
 * Helps when you scale your app and want to include cloud and use local caching.
 */
public class AppRepository {
    // change how to send an instance of repo to get database
    private static AppRepository ourInstance;

    public LiveData<List<NoteEntity>> mNotes;

    private AppDatabase mDb;

    // to ensure that database operations run in background thread
    private Executor executor
             = Executors.newSingleThreadExecutor();

    // passes the App Repo
    public static AppRepository getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mNotes = getAllNotes();
    }

    // Adds sample data
    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertAll(SampleData.getNotes());
            }
        });
    }

    /**
     * GetAllNotes will get the database's notes
     */
    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }

    /**
     * Since deleteAll returns a primitive type, it should be handled with an executor
     */
    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteAll();
            }
        });
    }

    /**
     *
     * @param noteId
     * @return NoteEntity, selected for editing
     */
    public NoteEntity getNoteById(int noteId) {
        return mDb.noteDao().getNoteById(noteId);
    }
}
