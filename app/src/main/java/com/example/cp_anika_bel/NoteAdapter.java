package com.example.cp_anika_bel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, List<Note> noteList) {
        super(context,0,noteList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);
        }

        TextView headline = convertView.findViewById(R.id.cellHeadline);
        TextView maintext = convertView.findViewById(R.id.cellMainText);

        headline.setText(note.getHeadline());
        maintext.setText(note.getMaintext());


        return convertView;

    }
}
