package com.joshuatheengineer.dodotodo.noteeditor;

import android.content.DialogInterface;
import android.os.Bundle;

import com.joshuatheengineer.dodotodo.R;
import com.joshuatheengineer.dodotodo.database.NoteEntity;
import com.joshuatheengineer.dodotodo.databinding.ActivityEditorBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import static com.joshuatheengineer.dodotodo.utilities.Constants.EDITING_KEY;
import static com.joshuatheengineer.dodotodo.utilities.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewModel;
    private ActivityEditorBinding binding;

    private AlertDialog deleteDialog;

    private boolean mNewNote, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();
        initBtnListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // below prevents window leaks
        if(deleteDialog != null) {
            deleteDialog.dismiss();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(EditorViewModel.class);
        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                if(noteEntity != null && !mEditing) {
                    binding.contentEditor.noteName.setText(noteEntity.getName());
                    binding.contentEditor.noteStatus.setChecked(noteEntity.getStatus().equals(1));
                    binding.contentEditor.noteTypeOfUnits.setText(noteEntity.getTypeofunits());
                    binding.contentEditor.contentEditNumUnits.tvUnits.setText(noteEntity.getNumofunits().toString());
                    mViewModel.mNumUnits.postValue(Integer.parseInt(
                            noteEntity.getNumofunits().toString()
                    ));
                    binding.contentEditor.contentEditGoalUnits.tvGoalUnits.setText(noteEntity.getGoalofunits().toString());
                    mViewModel.mGoalUnits.postValue(Integer.parseInt(
                            noteEntity.getGoalofunits().toString()
                    ));
                    binding.contentEditor.noteText.setText(noteEntity.getText());
                }
            }
        });

        mViewModel.mNumUnits.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer numUnits) {
                binding.contentEditor.contentEditNumUnits.tvUnits.setText(numUnits.toString());
            }
        });

        mViewModel.mGoalUnits.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer goalUnits) {
                binding.contentEditor.contentEditGoalUnits.tvGoalUnits.setText(goalUnits.toString());
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            setTitle(getString(R.string.new_note));
            mNewNote = true;
        } else {
            setTitle(getString(R.string.edit_note));
            int noteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    private void initBtnListeners() {
        binding.contentEditor.contentEditNumUnits.fabIncrementNumUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.incrementUnits();
            }
        });
        binding.contentEditor.contentEditNumUnits.fabDecrementNumUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.decrementUnits();
            }
        });
        binding.contentEditor.contentEditGoalUnits.fabIncrementGoalUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.incrementGoal();
            }
        });
        binding.contentEditor.contentEditGoalUnits.fabDecrementGoalUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.decrementGoal();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            deleteNoteData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(binding.contentEditor.noteText.getText().toString(),
                binding.contentEditor.noteName.getText().toString(),
                binding.contentEditor.noteStatus.isChecked(),
                Integer.parseInt(binding.contentEditor.contentEditNumUnits.tvUnits.getText().toString()),
                Integer.parseInt(binding.contentEditor.contentEditGoalUnits.tvGoalUnits.getText().toString()),
                binding.contentEditor.noteTypeOfUnits.getText().toString());
        finish();
    }

    private void deleteNoteData() {
        deleteDialog = new AlertDialog.Builder(EditorActivity.this)
                .setTitle("Delete Note Entry")
                .setMessage("Would you like to delete this note?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.deleteNote();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_delete_error)
                .show();
    }
}
