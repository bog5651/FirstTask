package com.example.firsttask;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

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

        tvHours = findViewById(R.id.tvHours);
        tvMin = findViewById(R.id.tvMin);
        tvSec = findViewById(R.id.tvSec);


       /* Animation inAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation outAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        tvSec.setInAnimation(inAnimation);
        tvSec.setOutAnimation(outAnimation);
        tvSec.setFactory(this);*/

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
        setZero();
        startTimer.setEnabled(true);
    }

    private void setZero() {
        tvHours.setText("00");
        tvMin.setText("00");
        tvSec.setText("00");
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(LOG_TAG, "time set: time = " + (hourOfDay * 60 + minute) * 60 * 1000);
        startTimer.setEnabled(false);
        timer = new LocalTimer(this);
        timer.execute((long) (hourOfDay * 60 + minute) * 60 * 1000);
    }

    /*@Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        //textView.setPadding(0, 4, 0, 0);
        //textView.setTextScaleX((float) 1.2);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        //textView.setTypeface(ResourcesCompat.getFont(this, R.font.pfs_quare_sans_pro));
        textView.setTextSize(36, TypedValue.COMPLEX_UNIT_SP);
        //textView.setTextColor(ContextCompat.getColor(this, R.color.greyish_brown));
        return textView;
    }*/
}
