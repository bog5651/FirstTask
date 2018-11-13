package com.example.firsttask;

import java.util.concurrent.TimeUnit;

public class TimeFormat {

    public static String[] format(long time) {
        String Result[] = new String[3];
        long clock[] = new long[3];
        clock[0] = TimeUnit.MILLISECONDS.toHours(time); // часы
        clock[1] = TimeUnit.MILLISECONDS.toMinutes(time - clock[0] * 60 * 60 * 1000); //минуты
        clock[2] = TimeUnit.MILLISECONDS.toSeconds(time - clock[0] * 60 * 60 * 1000 - clock[1] * 60 * 1000); //секунды
        for (int i = 0; i < 3; i++) {
            if (clock[i] < 10) {
                Result[i] = "0" + String.valueOf(clock[i]);
            } else {
                Result[i] = String.valueOf(clock[i]);
            }
        }
        return Result;
    }
}
