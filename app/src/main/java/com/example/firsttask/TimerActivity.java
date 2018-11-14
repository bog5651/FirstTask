package com.example.firsttask;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Button startTimer = findViewById(R.id.btnStart);
        Button cancelTimer = findViewById(R.id.btnCancel);

        startTimer.setOnClickListener(this);
        cancelTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                TimePickerDialog dialog = new TimePickerDialog(this, this, 0, 0, true);
                dialog.show();
                break;
            case R.id.btnCancel:
                timer.stop();
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timer.setTimer((hourOfDay * 60 + minute) * 60 * 1000);
        timer.start();
    }

    @Override
    public void onTimeChange(long time) {
    }

    @Override
    public void onTimeEnd() {
        Toast.makeText(this, "Timer End", Toast.LENGTH_LONG).show();
    }
}
