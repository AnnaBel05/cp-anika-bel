package com.example.cp_anika_bel;

import android.os.Build;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils {
    public static LocalDate selectedDate;

    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.format(formatter);
        }
        else return "";
    }

    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return time.format(formatter);
        }
        else return "";
    }

    public static String formattedShortTime(LocalTime time) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return time.format(formatter);
        }
        else return "";
    }

    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.format(formatter);
        }
        else return "";
    }

    public static String monthDayFromDate(LocalDate date) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM d");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.format(formatter);
        }
        else return "";
    }

    public static ArrayList<LocalDate> daysInMonthArray() {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            yearMonth = YearMonth.from(selectedDate);
        }
        int daysInMonth = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            daysInMonth = yearMonth.lengthOfMonth();
        }

        LocalDate prevMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prevMonth = selectedDate.minusMonths(1);
        }
        LocalDate nextMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nextMonth = selectedDate.plusMonths(1);
        }

        YearMonth prevYearMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prevYearMonth = YearMonth.from(prevMonth);
        }
        int prevDaysInMonth = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prevDaysInMonth = prevYearMonth.lengthOfMonth();
        }

        LocalDate firstOfMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            firstOfMonth =             CalendarUtils.selectedDate.withDayOfMonth(1);
        }
        int dayOfWeek = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        }
        for(int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    daysInMonthArray.add(LocalDate.of(prevMonth.getYear(),prevMonth.getMonth(), prevDaysInMonth + i - dayOfWeek));
                }
            }
            else if (i > daysInMonth + dayOfWeek) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    daysInMonthArray.add(LocalDate.of(nextMonth.getYear(),nextMonth.getMonth(), i - dayOfWeek - daysInMonth));
                }
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(), i - dayOfWeek));
                }
            }
        }
        return daysInMonthArray;
    }


    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endDate = current.plusWeeks(1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (current.isBefore(endDate)) {
                days.add(current);
                current = current.plusDays(1);
            }
        }

        return days;
    }

    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate oneWeekAgo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            oneWeekAgo = current.minusWeeks(1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (current.isAfter(oneWeekAgo)) {
                if (current.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    return current;
                }
                current = current.minusDays(1);
            }
        }

        return null;
    }
}
