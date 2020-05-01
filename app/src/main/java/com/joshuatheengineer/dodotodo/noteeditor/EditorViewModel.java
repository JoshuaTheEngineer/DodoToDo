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
    public MutableLiveData<Integer> mNumUnits = new MutableLiveData<>();
    public MutableLiveData<Integer> mGoalUnits = new MutableLiveData<>();

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
        // initializes with base values for num and goal of units
        // will change if there's existing note data (for example, when editing)
        // the null conditionals will ensure it sets these values if you are not
        // changing orientation
        if(mNumUnits.getValue() == null || mGoalUnits.getValue() == null) {
            mNumUnits.postValue(0);
            mGoalUnits.postValue(1);
        }
    }

    public void loadData(final int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
                // updates number and goal of units if there's existing note data
                mNumUnits.postValue(note.getNumofunits());
                mGoalUnits.postValue(note.getGoalofunits());
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

    /**
     * the below methods control the incrementing and decrementing of units
     */
    public void incrementUnits() {
        int numUnits = mNumUnits.getValue();
        // num units SHOULD NOT increment above the goal
        if(numUnits < mGoalUnits.getValue()) mNumUnits.postValue(numUnits + 1);
    }

    public void decrementUnits() {
        int numUnits = mNumUnits.getValue();
        // num units SHOULD NOT decrement below 0
        if(numUnits > 0) mNumUnits.postValue(numUnits - 1);
    }

    public void incrementGoal() {
        int goal = mGoalUnits.getValue();
        mGoalUnits.postValue(mGoalUnits.getValue() + 1);
    }

    public void decrementGoal() {
        int goal = mGoalUnits.getValue();
        // the goal SHOULD NOT decrement below 1, there should be at least one
        // NOR can it decrement below the current num value
        if(goal > 1 && goal > mNumUnits.getValue()) mGoalUnits.postValue(goal - 1);
    }
}
