package com.example.firsttask;

import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener, LocalTimer.onTimeChangeEventListner, TimePickerDialog.OnTimeSetListener {

    private LocalTimer timer;
    private String LOG_TAG = "TimerAct";

    private Button startTimer;

    private TextView tvMin;
    private TextView tvSec;
    private TextView tvHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        startTimer = findViewById(R.id.btnStart);
        startTimer.setOnClickListener(this);

        Button cancelTimer = findViewById(R.id.btnCancel);
        cancelTimer.setOnClickListener(this);

        tvMin = findViewById(R.id.tvMin);
        tvSec = findViewById(R.id.tvSec);
        tvHours = findViewById(R.id.tvHours);

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
        Log.d(LOG_TAG, "onClick: time = " + time);
        if (time == 0) {
            Toast.makeText(this, "Timer end", Toast.LENGTH_LONG).show();
            Log.d(LOG_TAG, "Timer end");
        } else {
            String times[] = TimeFormat.format(time);
            tvHours.setText(times[0]);
            tvMin.setText(times[1]);
            tvSec.setText(times[2]);
        }
    }

    @Override
    public void onTimeEnd() {
        Toast.makeText(this, "Timer end", Toast.LENGTH_LONG).show();
        Log.d(LOG_TAG, "Timer end");
        tvHours.setText("00");
        tvMin.setText("00");
        tvSec.setText("00");
        startTimer.setEnabled(true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(LOG_TAG, "time set: time = " + (hourOfDay * 60 + minute) * 60 * 1000);
        startTimer.setEnabled(false);
        timer = new LocalTimer(this);
        timer.execute((long) (hourOfDay * 60 + minute) * 60 * 1000);
    }
}
