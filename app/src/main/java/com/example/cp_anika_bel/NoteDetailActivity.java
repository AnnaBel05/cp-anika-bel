package com.example.cp_anika_bel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class NoteDetailActivity extends AppCompatActivity {

    private EditText headlineET, maintextET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        initWidgets();
    }

    private void initWidgets() {
        headlineET = findViewById(R.id.headlineET);
        maintextET = findViewById(R.id.maintextET);
    }

    public void saveNote(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String headline = String.valueOf(headlineET.getText());
        String maintext = String.valueOf(maintextET.getText());

        int id = Note.noteArrayList.size();
        Note newNote = new Note(id,headline,maintext);
        Note.noteArrayList.add(newNote);
        sqLiteManager.addNoteToDB(newNote);
        finish();
    }
}