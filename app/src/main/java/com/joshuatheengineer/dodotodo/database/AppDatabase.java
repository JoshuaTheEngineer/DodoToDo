package com.joshuatheengineer.dodotodo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

// This will be a Singleton class
// that way the same database will pass info around app
@Database(entities = {NoteEntity.class}, version=1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";

    // 'volatile' so its stored only to device memory
    private static volatile AppDatabase instance;

    // Gets NoteDAO
    public abstract NoteDao noteDao();

    /**
     * Below code ensures that only one reference to the database is passed around app.
     */
    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            // synchronized prevents raise conditions in different parts of app
            synchronized (LOCK) {
                // if instance doesn't exist, create a new one
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
