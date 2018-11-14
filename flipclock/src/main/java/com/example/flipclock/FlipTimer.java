package com.example.flipclock;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.concurrent.TimeUnit;

public class FlipTimer extends RelativeLayout implements LocalTimer.onTimeChangeEventListener {

    private LocalTimer timer;
    private String LOG_TAG = "TimerAct";

    private TimeFragment hourFragment;
    private TimeFragment minFragment;
    private TimeFragment secFragment;

    private LocalTimer.onTimeChangeEventListener callback;

    public FlipTimer(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.flip_clock, this);
        FragmentManager fragmentManager;
        try {
            fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            hourFragment = (TimeFragment) fragmentManager.findFragmentById(R.id.fragmentHour);
            minFragment = (TimeFragment) fragmentManager.findFragmentById(R.id.fragmentMin);
            secFragment = (TimeFragment) fragmentManager.findFragmentById(R.id.fragmentSec);
            if (secFragment != null)
                secFragment.setColor(R.color.greyish_brown);
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, "Can't get fragment manager");
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "NullPointerException");
        }
        String times[] = format(0);
        setTime(times);
    }

    @Override
    public void onTimeChange(long time) {
        Log.d(LOG_TAG, "onTimeChange: time = " + time);
        String times[] = format(time);
        setTime(times);
        if (callback != null) {
            callback.onTimeChange(time);
        }
    }

    @Override
    public void onTimeEnd() {
        Log.d(LOG_TAG, "Timer end");
        String times[] = format(0);
        setTime(times);
        if (callback != null) {
            callback.onTimeEnd();
        }
    }

    public void setOnTimeChangeEventListener(LocalTimer.onTimeChangeEventListener callback) {
        this.callback = callback;
    }

    public void removeOnTimeChangeEventListener() {
        this.callback = null;
    }

    public LocalTimer.onTimeChangeEventListener getOnTimeChangeEventListener() {
        return this.callback;
    }

    public long getTime() {
        return timer.getTime();
    }

    private void setTime(String time[]) {
        hourFragment.setTime(time[0]);
        minFragment.setTime(time[1]);
        secFragment.setTime(time[2]);
    }

    public void setTimer(long time) {
        Log.d(LOG_TAG, "time set: time = " + time);
        if (timer != null)
            timer.cancel();
        timer = new LocalTimer(time, 1000);
        timer.setOnTimeChangeEventListener(this);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        String times[] = format(0);
        setTime(times);
    }

    public void start() {
        if (timer != null)
            timer.start();
    }

    private String[] format(long time) {
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
