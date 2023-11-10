package com.example.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = AddNoteActivity.class.getSimpleName();
    private static final String PREFS_NAME = "NotesPrefs";

    private EditText editTextNoteName;
    private EditText editTextNoteContent;
    private Button btnSaveNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        editTextNoteName = findViewById(R.id.editTextNoteName);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);
        btnSaveNote = findViewById(R.id.btnSaveNote);

        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String noteName = editTextNoteName.getText().toString().trim();
        String noteContent = editTextNoteContent.getText().toString().trim();

        if (!noteName.isEmpty() && !noteContent.isEmpty()) {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(noteName, noteContent);
            editor.apply();

            Log.i(TAG, "Note saved: " + noteName);

            finish();
        } else {
            Log.e(TAG, "Note not saved. Please enter both name and content.");
        }
    }
}
