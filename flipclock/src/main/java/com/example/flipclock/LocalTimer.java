package com.example.flipclock;

import android.os.CountDownTimer;

public class LocalTimer extends CountDownTimer {

    public interface onTimeChangeEventListener {
        void onTimeChange(long time);

        void onTimeEnd();
    }

    private String LOG_TAG = "LocalTimer";
    private onTimeChangeEventListener callback;

    private long timeInMilliSec;
    private long countDownInterval;

    public LocalTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        timeInMilliSec = millisInFuture;
        this.countDownInterval = countDownInterval;
    }

    public void setOnTimeChangeEventListener(onTimeChangeEventListener callback) {
        this.callback = callback;
    }

    public void removeOnTimeChangeEventListener() {
        this.callback = null;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (callback != null) {
            callback.onTimeChange(millisUntilFinished);
        }
        timeInMilliSec = millisUntilFinished;
    }

    @Override
    public void onFinish() {
        if (callback != null) {
            callback.onTimeEnd();
        }
    }

    public long getTime() {
        return timeInMilliSec;
    }

    public long getCountDownInterval() {
        return countDownInterval;
    }
}
