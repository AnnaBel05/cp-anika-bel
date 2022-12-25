package com.example.cp_anika_bel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            time = LocalTime.now();
        }
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        String eventName = eventNameET.getText().toString();
        int id = Event.eventList.size();

        Event newEvent = new Event(id, eventName, CalendarUtils.selectedDate, time);

        Event.eventList.add(newEvent);
        sqLiteManager.addEventToDB(newEvent);

        // NC = next cycle
        String eventNameNC = "Cycle starts today!";
        int idNC = Event.eventList.size();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate dateNC = CalendarUtils.selectedDate.plusDays(30);
            Event newEvencNC = new Event(idNC, eventNameNC, dateNC, time);
            Event.eventList.add(newEvencNC);
            sqLiteManager.addEventToDB(newEvencNC);
            scheduleNotification(getNotification("Cycle starts todayyy!!"), time, dateNC);
//            scheduleNotification(getNotification("Cycle starts todayyy!!"), time.plusSeconds(5),CalendarUtils.selectedDate);
        }
        finish();
    }

    private void scheduleNotification(Notification notification, LocalTime time, LocalDate date) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;

        LocalDateTime localDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = time.atDate(date);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Europe/Moscow"));
            long millis = zonedDateTime.toInstant().toEpochMilli();
            alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        }

//        String title = "Cycle alarm";
//        String message = "Cycle starts today!";

//        @SuppressLint("UnspecifiedImmutableFlag")
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            LocalDateTime localDateTime = time.atDate(date);
//            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Europe/Moscow"));
//            long millis = zonedDateTime.toInstant().toEpochMilli();
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,millis,pendingIntent);
//        }
//        startActivity(new Intent(this, MainActivity.class));
    }

    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle("Cycle alarm");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }
}