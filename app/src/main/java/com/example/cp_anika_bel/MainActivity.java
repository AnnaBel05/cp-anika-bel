package com.example.cp_anika_bel;

import static com.example.cp_anika_bel.CalendarUtils.daysInMonthArray;
import static com.example.cp_anika_bel.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = LocalDate.now();
            CalendarUtils.selectedDate.plusDays(1);
        }
        setMonthView();
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateEventArray();
        sqLiteManager.populateNoteListArray();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(            CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate =             CalendarUtils.selectedDate.minusMonths(1);
        }
        setMonthView();
    }

    public void nextMonthAction(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate =             CalendarUtils.selectedDate.plusMonths(1);
        }
        setMonthView();
    }

//    @Override
//    public void onItemClick(int position, String dayText) {
//
//            String message = "Selected Date " + dayText + " " + monthYearFromDate(            CalendarUtils.selectedDate);
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//
//    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

    public void noteAction(View view) {
        startActivity(new Intent(this,NoteActivity.class));
    }
}