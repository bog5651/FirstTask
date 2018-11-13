package com.example.firsttask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimeFragment extends Fragment {

    private final String LOG_TAG = "TimeFragment";

    private TextView tvTime;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clock_fragmet, null);
        Log.d(LOG_TAG, "onCreateView");
        tvTime = v.findViewById(R.id.tvTime);
        return v;
    }

    public void setTime(String time) {
        tvTime.setText(time);
    }

    public void setColor(Context context, int id) {
        tvTime.setTextColor(ContextCompat.getColor(context, id));
    }
}
