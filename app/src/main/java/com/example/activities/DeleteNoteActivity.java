package com.example.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Map;

public class DeleteNoteActivity extends AppCompatActivity {

    private static final String TAG = DeleteNoteActivity.class.getSimpleName();
    private static final String PREFS_NAME = "NotesPrefs";

    private Spinner spinnerNotes;
    private Button btnDeleteNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerNotes = findViewById(R.id.spinnerNotes);
        btnDeleteNote = findViewById(R.id.btnDeleteNote);

        populateSpinner();

        btnDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });
    }

    private void populateSpinner() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        ArrayList<String> noteNames = new ArrayList<>(allEntries.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, noteNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerNotes.setAdapter(adapter);
    }

    private void deleteNote() {
        String selectedNoteName = spinnerNotes.getSelectedItem().toString();

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(selectedNoteName);
        editor.apply();

        finish();
    }
}