package com.example.cp_anika_bel;

import java.util.ArrayList;

public class Note {
    public static ArrayList<Note> noteArrayList = new ArrayList<>();

    private int id;
    private String headline;
    private String maintext;

    public Note(int id, String headline, String maintext) {
        this.id = id;
        this.headline = headline;
        this.maintext = maintext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }
}
