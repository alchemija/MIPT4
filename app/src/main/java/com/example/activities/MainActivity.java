package com.example.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PREFS_NAME = "NotesPrefs";

    private ListView notesListView;
    private ArrayList<String> noteNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notesListView = findViewById(R.id.notesListView);
        loadNotes();
        notesListView.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedNoteName = noteNames.get(position);
            Log.i(TAG, "Note selected: " + selectedNoteName);
            String noteContent = getNoteContent(selectedNoteName);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_create_note) {
            Log.i(TAG, "Create Note option selected");
            Intent addNoteIntent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(addNoteIntent);
            return true;
        } else if (itemId == R.id.menu_delete_note) {
            Log.i(TAG, "Delete Note option selected");
            Intent deleteNoteIntent = new Intent(MainActivity.this, DeleteNoteActivity.class);
            startActivity(deleteNoteIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        noteNames = new ArrayList<>(allEntries.keySet());

        ArrayList<String> notesList = new ArrayList<>();
        for (String noteName : noteNames) {
            String noteContent = preferences.getString(noteName, "");
            notesList.add(noteName + "\n" + noteContent);
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notesList);

        notesListView.setAdapter(adapter);
    }

    private String getNoteContent(String noteName) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getString(noteName, "");
    }
}
