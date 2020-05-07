package com.joshuatheengineer.dodotodo.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.joshuatheengineer.dodotodo.R;
import com.joshuatheengineer.dodotodo.database.NoteEntity;
import com.joshuatheengineer.dodotodo.databinding.ActivityMainBinding;
import com.joshuatheengineer.dodotodo.noteeditor.EditorActivity;
import com.joshuatheengineer.dodotodo.settings.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;

    private MainViewModel mViewModel;
    private AlertDialog deleteDialog;

    private boolean filterStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        initRecyclerView();
        initViewModel();

       filterStatus = false;
        if(savedInstanceState != null) {
            filterStatus = savedInstanceState.getBoolean("FilterStatus");
            if(!filterStatus){
                displayToDoData();
            } else {
                displayCompletedData();
            }
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // window leaks
        if(deleteDialog != null){
            deleteDialog.dismiss();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("FilterStatus",  filterStatus);
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {
        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);

                if( mAdapter == null){
                    mAdapter = new NotesAdapter(notesData, MainActivity.this);
                    binding.contentmain.recyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.mNotes.observe(this, notesObserver);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contentmain.recyclerView.setHasFixedSize(true);
        binding.contentmain.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(
                binding.contentmain.recyclerView.getContext(),
                layoutManager.getOrientation());
        binding.contentmain.recyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_filter_status_todo:
                displayToDoData();
                return true;

            case R.id.action_filter_status_completed:
                displayCompletedData();
                return true;

            case R.id.action_delete_all:
                deleteAllData();
                return true;

            case R.id.action_settings:
                goToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    private void displayToDoData() {
        setTitle("To Do List");
        filterStatus = false;
        mViewModel.getAllToDoNotes();
        initViewModel();
    }

    private void displayCompletedData() {
        setTitle("Completed List");
        filterStatus = true;
        mViewModel.getAllCompletedNotes();
        initViewModel();
    }

    private void deleteAllData() {
        String filterStr = filterStatus ? "Completed" : "To Do";
        deleteDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete entry")
                .setMessage("Would you like to delete all "+filterStr+" data?")

               .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(!filterStatus) mViewModel.deleteAllTodoNotes();
                        else mViewModel.deleteAllCompletedNotes();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_delete_error)
                .show();
    }
}
