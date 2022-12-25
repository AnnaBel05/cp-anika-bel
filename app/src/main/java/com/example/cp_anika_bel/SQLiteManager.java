package com.example.cp_anika_bel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "CP_DB_2";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "event";
    private static final String TABLE_NAME_2= "notes";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String DATE_FIELD = "date";
    private static final String TIME_FIELD = "time";

    private static final String ID_FIELD_2 = "id";
    private static final String NAME_FIELD_2 = "name";
    private static final String TEXT_FIELD = "text";

    private static DateTimeFormatter dateFormatter;
    private static DateTimeFormatter timeFormatter;



    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if(sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append(" CREATE TABLE ")
                .append(TABLE_NAME)
                .append(" ( ")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" DATE, ")
                .append(TIME_FIELD)
                .append(" TIME ) ");
        sqLiteDatabase.execSQL(sql.toString());

        StringBuilder sql2;
        sql2 = new StringBuilder()
                .append(" CREATE TABLE ")
                .append(TABLE_NAME_2)
                .append(" ( ")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT ")
                .append(ID_FIELD_2)
                .append(" INT, ")
                .append(NAME_FIELD_2)
                .append(" TEXT, ")
                .append(TEXT_FIELD)
                .append(" TEXT) ");
        sqLiteDatabase.execSQL(sql2.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        switch (oldVersion) {
//            case 1:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }
    }

    public void addEventToDB(Event event) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, event.getId());
        contentValues.put(NAME_FIELD, event.getName());
        contentValues.put(DATE_FIELD, getStringFromDate(event.getDate()));
        contentValues.put(TIME_FIELD, getStringFromTime(event.getTime()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void addNoteToDB(Event event) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, event.getId());
        contentValues.put(NAME_FIELD, event.getName());
        contentValues.put(DATE_FIELD, getStringFromDate(event.getDate()));
        contentValues.put(TIME_FIELD, getStringFromTime(event.getTime()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateEventArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME, null)) {
            if(result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String stringDate = result.getString(3);
                    String stringTime = result.getString(4);

                    LocalDate date = getDateFromString(stringDate);
                    LocalTime time = getTimeFromString(stringTime);

                    Event event = new Event(id,name,date,time);
                    Event.eventList.add(event);

                }
            }
        }
    }

    public void updateEventInDB(Event event) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, event.getId());
        contentValues.put(NAME_FIELD, event.getName());
        contentValues.put(DATE_FIELD, getStringFromDate(event.getDate()));
        contentValues.put(TIME_FIELD, getStringFromTime(event.getTime()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + "=?", new String[]{String.valueOf(event.getId())});
    }

    private String getStringFromDate(LocalDate date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            if (date == null )  {
                return null;
            }
            else {
                return dateFormatter.format(date);
            }
        }
        else return "";
    }

    private LocalDate getDateFromString(String string) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            try {
//                return (LocalDate) dateFormatter.parse(string);
                return LocalDate.parse(string, dateFormatter);
            } catch (NullPointerException e) {
                return null;
            }
        }
        else return null;
    }

    private String getStringFromTime(LocalTime time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            if (time == null) {
                return null;
            } else {
                return timeFormatter.format(time);
            }
        }
        else return "";
    }

    private LocalTime getTimeFromString(String string) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            try {
                return LocalTime.parse(string, timeFormatter);
            } catch (NullPointerException e) {
                return null;
            }
        }
        else return null;
    }
}
