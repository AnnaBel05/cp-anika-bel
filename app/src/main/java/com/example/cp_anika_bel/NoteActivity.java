package com.example.cp_anika_bel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class NoteActivity extends AppCompatActivity {
    
    private ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initWidgets();
        setNoteAdapter();
    }

    private void setNoteAdapter() {
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.noteArrayList);
        noteListView.setAdapter(noteAdapter);
    }

    private void initWidgets() {
        noteListView = findViewById(R.id.noteListView);
        
    }

    public void newNote(View view) {
        Intent newNoteIntent = new Intent(this,NoteDetailActivity.class);
    }

    public void returnToMainActivity(View view) {
    }
}