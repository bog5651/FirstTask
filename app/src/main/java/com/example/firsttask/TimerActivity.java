package com.example.firsttask;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.flipclock.FlipTimer;
import com.example.flipclock.LocalTimer;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, LocalTimer.onTimeChangeEventListener {

    private String LOG_TAG = "TimerAct";

    private FlipTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer = findViewById(R.id.Time);

        timer.setOnTimeChangeEventListener(this);

        Button setTimer = findViewById(R.id.btnSetTimer);
        Button cancelTimer = findViewById(R.id.btnCancel);
        Button pause = findViewById(R.id.btnPause);
        Button add = findViewById(R.id.btnAdd);
        Button start = findViewById(R.id.btnStart);

        setTimer.setOnClickListener(this);
        cancelTimer.setOnClickListener(this);
        pause.setOnClickListener(this);
        add.setOnClickListener(this);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSetTimer:
                TimePickerDialog dialog = new TimePickerDialog(this, this, 0, 0, true);
                dialog.show();
                break;
            case R.id.btnCancel:
                timer.stop();
                break;
            case R.id.btnPause:
                if (timer.isPaused()) {
                    timer.resume();
                } else {
                    timer.pause();
                }
                break;
            case R.id.btnAdd:
                timer.addTime(((1 * 60 + 1) * 60 + 20) * 1000);
                break;
            case R.id.btnStart:
                timer.start();
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timer.setTimer((hourOfDay * 60 + minute) * 60 * 1000);
//        timer.setTimer((89 * 60 + 34) * 60 * 1000);
    }

    @Override
    public void onTimeChange(long time) {
    }

    @Override
    public void onTimeEnd() {
        Toast.makeText(this, "Timer End", Toast.LENGTH_LONG).show();
    }
}
