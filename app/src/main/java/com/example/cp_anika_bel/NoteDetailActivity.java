package com.example.cp_anika_bel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        headlineET = findViewById(R.id.maintextET);
    }

    public void saveNote(View view) {
    }
}