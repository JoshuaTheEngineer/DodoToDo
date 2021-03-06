package com.joshuatheengineer.dodotodo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity noteEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> notes);

    @Delete
    void deleteNote(NoteEntity noteEntity);

    @Query("SELECT * FROM notes WHERE id =:id")
    NoteEntity getNoteById(int id);

    @Query("SELECT * FROM notes ORDER BY date DESC")
    LiveData<List<NoteEntity>> getAll();

    /**
     * Delete only certain rows
     * https://www.tutorialspoint.com/sqlite/sqlite_delete_query.htm
     */
    @Query("DELETE FROM notes WHERE status =:status")
    int deleteNotesByStatus(int status);

    @Query("DELETE FROM notes")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();

    /**
     * Gets notes based off status
     * @param status
     * @return list of notes
     */
    @Query("SELECT * FROM notes WHERE status =:status")
    LiveData<List<NoteEntity>> getNotesByStatus(int status);
}
