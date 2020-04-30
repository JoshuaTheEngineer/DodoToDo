package com.joshuatheengineer.dodotodo.database;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * For upgrading / updating your Database, use Migrations
 * https://developer.android.com/training/data-storage/room/migrating-db-versions
 */
public class AppDbMigration {
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            /**
             * The following would add columns when you migrate from version 1 to 2
             * Careful, it's CASE SENSITIVE... TOOK ME HOURS TO SOLVE IT!!!
             */
            database.execSQL("ALTER TABLE notes " + " ADD COLUMN name TEXT");
            database.execSQL("ALTER TABLE notes " + " ADD COLUMN status INTEGER");
            database.execSQL("ALTER TABLE notes " + " ADD COLUMN numofunits INTEGER");
            database.execSQL("ALTER TABLE notes " + " ADD COLUMN goalofunits INTEGER");
            database.execSQL("ALTER TABLE notes " + " ADD COLUMN typeofunits TEXT");
        }
    };
}
