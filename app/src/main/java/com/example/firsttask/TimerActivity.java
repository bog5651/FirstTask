package com.example.firsttask;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener, LocalTimer.onTimeChangeEventListner, TimePickerDialog.OnTimeSetListener {

    private LocalTimer timer;
    private String LOG_TAG = "TimerAct";

    private Button startTimer;

    private TimeFragment hourFragment;
    private TimeFragment minFragment;
    private TimeFragment secFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        startTimer = findViewById(R.id.btnStart);
        startTimer.setOnClickListener(this);

        Button cancelTimer = findViewById(R.id.btnCancel);
        cancelTimer.setOnClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

        hourFragment = (TimeFragment) fragmentManager.findFragmentById(R.id.fragmentHour);
        minFragment = (TimeFragment) fragmentManager.findFragmentById(R.id.fragmentMin);
        secFragment = (TimeFragment) fragmentManager.findFragmentById(R.id.fragmentSec);

        if (secFragment != null)
            secFragment.setColor(R.color.greyish_brown);

        setZero();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                TimePickerDialog dialog = new TimePickerDialog(this, this, 0, 0, true);
                dialog.show();
                break;
            case R.id.btnCancel:
                if (timer != null)
                    if (timer.getStatus() == AsyncTask.Status.RUNNING) {
                        startTimer.setEnabled(true);
                        timer.cancel(true);
                        timer = null;
                    }
                break;
        }
    }

    @Override
    public void onTimeChange(long time) {
        Log.d(LOG_TAG, "onTimeChange: time = " + time);
        String times[] = TimeFormat.format(time);
        hourFragment.setTime(times[0]);
        minFragment.setTime(times[1]);
        secFragment.setTime(times[2]);
    }

    @Override
    public void onTimeEnd() {
        Toast.makeText(this, "Timer end", Toast.LENGTH_LONG).show();
        Log.d(LOG_TAG, "Timer end");
        setZero();
        startTimer.setEnabled(true);
    }

    private void setZero() {
        hourFragment.setTime("00");
        minFragment.setTime("00");
        secFragment.setTime("00");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(LOG_TAG, "time set: time = " + (hourOfDay * 60 + minute) * 60 * 1000);
        startTimer.setEnabled(false);
        timer = new LocalTimer(this);
        timer.execute((long) (hourOfDay * 60 + minute) * 60 * 1000);
    }
}
