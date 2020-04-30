package com.joshuatheengineer.dodotodo;

import android.content.Intent;
import android.os.Bundle;

import com.joshuatheengineer.dodotodo.database.NoteEntity;
import com.joshuatheengineer.dodotodo.databinding.ActivityMainBinding;
import com.joshuatheengineer.dodotodo.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        // add view model
        initRecyclerView();
        initViewModel();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);

                if( mAdapter == null){
                    // initializes with data
                    mAdapter = new NotesAdapter(notesData, MainActivity.this);
                    binding.contentmain.recyclerView.setAdapter(mAdapter);
                } else {
                    // used when data set changes
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // will get notes through Publisher-Subscriber model
        mViewModel.mNotes.observe(this, notesObserver);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contentmain.recyclerView.setHasFixedSize(true);
        binding.contentmain.recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

    private void deleteAllData() {
        mViewModel.deleteAllNotes();
    }
}
