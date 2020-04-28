package com.joshuatheengineer.dodotodo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.joshuatheengineer.dodotodo.database.NoteEntity;
import com.joshuatheengineer.dodotodo.databinding.ActivityMainBinding;
import com.joshuatheengineer.dodotodo.utilities.SampleData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // declare binding
    private ActivityMainBinding binding;

    // will hold notes data
    private List<NoteEntity> notesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets DataBinding to the content view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // the order matters, bind first then add toolbar binding
        // https://stackoverflow.com/questions/34636934/android-data-binding-setsupportactionbar
        setSupportActionBar(binding.toolbar);

        // Method to initialize Recyclerview
        initRecyclerView();

        // example of using DataBinding with the fab btn
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // gets all Notes
        notesData.addAll(SampleData.getNotes());
        for( NoteEntity note : notesData){
            // Print in console to see if it works
            Log.i("DodoTodo", note.toString());
        }
    }

    /**
     * Initializes RecyclerView
     */
    private void initRecyclerView() {
        // Create a layout manager to determine how recycler view's laid out
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // 'setHasFixedSize' prevents rows resizing
        binding.contentmain.recyclerView.setHasFixedSize(true);
        binding.contentmain.recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
