package com.example.annotiondemo;

import android.util.Log;

public class WeekDayDemo {
    private static WeekDay weekDay;
    enum WeekDay{
        SATURDAY, SUNDAY
    }

    public static WeekDay getWeekDay(){
        return weekDay;
    }

    public static void setWeekDay(WeekDay weekDay) {
        WeekDayDemo.weekDay = weekDay;
    }
}
