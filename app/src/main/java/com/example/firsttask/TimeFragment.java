package com.example.firsttask;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class TimeFragment extends Fragment implements ViewSwitcher.ViewFactory {

    private final String LOG_TAG = "TimeFragment";
    private Context context;

    private TextSwitcher tsTime1;
    private TextSwitcher tsTime2;
    private int colorId;
    private String time = "AA";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clock_fragmet, null);
        Log.d(LOG_TAG, "onCreateView");
        context = getActivity();

        tsTime1 = v.findViewById(R.id.tvTime1);
        tsTime2 = v.findViewById(R.id.tvTime2);

        setColor(R.color.tangerine); //цвет по умолчанию

        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.flip_in);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.flip_out);
        tsTime1.setInAnimation(inAnimation);
        tsTime1.setOutAnimation(outAnimation);
        tsTime1.setFactory(this);

        tsTime2.setInAnimation(inAnimation);
        tsTime2.setOutAnimation(outAnimation);
        tsTime2.setFactory(this);
        return v;
    }

    public void setTime(String time) {
        if (this.time.charAt(0) != (time.charAt(0)))
            tsTime1.setText("" + time.charAt(0));
        if (this.time.charAt(1) != (time.charAt(1)))
            tsTime2.setText("" + time.charAt(1));
        this.time = time;
    }

    public void setColor(int id) {
        colorId = id;
        for (int i = 0; i < tsTime2.getChildCount(); i++) {
            TextView tv = (TextView) tsTime2.getChildAt(i);
            tv.setTextColor(Color.parseColor(context.getString(colorId)));
        }
        for (int i = 0; i < tsTime1.getChildCount(); i++) {
            TextView tv = (TextView) tsTime1.getChildAt(i);
            tv.setTextColor(Color.parseColor(context.getString(colorId)));
        }
    }

    @Override
    public View makeView() {
        Log.d(LOG_TAG, "makeView: color " + colorId);
        TextView textView = new TextView(context);
        textView.setTextColor(Color.parseColor(context.getString(colorId)));
        textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        textView.setPadding(0, 2, 0, 0);
        textView.setLetterSpacing((float) 0.2);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.pfs_quare_sans_pro));
        textView.setTextSize(36);
        return textView;
    }
}
