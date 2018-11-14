package com.example.flipclock;

import android.os.CountDownTimer;

public class LocalTimer extends CountDownTimer {

    public interface onTimeChangeEventListener {
        void onTimeChange(long time);

        void onTimeEnd();
    }

    private String LOG_TAG = "LocalTimer";
    private onTimeChangeEventListener callback;

    private long time;
    private long countDownInterval;

    public LocalTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        time = millisInFuture;
        this.countDownInterval = countDownInterval;
    }

    public void setOnTimeChangeEventListener(onTimeChangeEventListener callback) {
        this.callback = callback;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (callback != null) {
            callback.onTimeChange(millisUntilFinished);
        }
        time = millisUntilFinished;
    }

    @Override
    public void onFinish() {
        if (callback != null) {
            callback.onTimeEnd();
        }
    }

    public long getTime() {
        return time;
    }

    public long getCountDownInterval() {
        return countDownInterval;
    }


}
