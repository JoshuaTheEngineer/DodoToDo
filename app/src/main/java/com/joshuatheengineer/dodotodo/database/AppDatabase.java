package com.joshuatheengineer.dodotodo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import static com.joshuatheengineer.dodotodo.database.AppDbMigration.MIGRATION_1_2;

// upgrade db version to allow migration
@Database(entities = {NoteEntity.class}, version=2)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;
    public abstract NoteDao noteDao();

    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2).build(); // add migrations due to new database design
                }
            }
        }
        return instance;
    }
}
