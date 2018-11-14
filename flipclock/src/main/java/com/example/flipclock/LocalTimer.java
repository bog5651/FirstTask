package com.example.flipclock;

import android.os.CountDownTimer;

public class LocalTimer extends CountDownTimer {

    public interface onTimeChangeEventListener {
        void onTimeChange(long time);

        void onTimeEnd();
    }

    private String LOG_TAG = "LocalTimer";
    private onTimeChangeEventListener callback;

    public void setOnTimeChangeEventListner(onTimeChangeEventListener callback) {
        this.callback = callback;
    }

    public LocalTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (callback != null) {
            callback.onTimeChange(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (callback != null) {
            callback.onTimeEnd();
        }
    }

}
