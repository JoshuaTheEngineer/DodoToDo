package com.joshuatheengineer.dodotodo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.joshuatheengineer.dodotodo.database.NoteEntity;
import com.joshuatheengineer.dodotodo.databinding.ActivityEditorBinding;
import com.joshuatheengineer.dodotodo.viewmodel.EditorViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;
import android.view.View;

import static com.joshuatheengineer.dodotodo.utilities.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewModel;
    private ActivityEditorBinding binding;

    private boolean mNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(EditorViewModel.class);
        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                if(noteEntity != null) {
                    binding.contentEditor.noteText.setText(noteEntity.getText());
                }
            }
        });

        // Loads Editor View model for selected Note
        Bundle extras = getIntent().getExtras();
        if (extras == null){
            // changes title of activity
            setTitle("New Note");
            mNewNote = true;
        } else {
            setTitle("Edit note");
            int noteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    /**
     * When user selects home button or the check mark, it saves the data then returns
     * to note list activity
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * If user clicks Android's back button, it saves the note and returns to Note screen
     */
    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    /**
     * closes the current activity and return to Notes list
     */
    private void saveAndReturn() {
        mViewModel.saveNote(binding.contentEditor.noteText.getText().toString());
        finish();
    }
}
