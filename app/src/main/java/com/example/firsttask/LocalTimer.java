package com.example.firsttask;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class LocalTimer extends AsyncTask<Long, Long, Void> {

    public interface onTimeChangeEventListner {
        void onTimeChange(long time);

        void onTimeEnd();
    }

    private String LOG_TAG = "LocalTimer";

    private onTimeChangeEventListner callback;

    public LocalTimer(onTimeChangeEventListner callback) {
        this.callback = callback;
        Log.d(LOG_TAG, "LocalTimer: constr");
    }

    @Override
    protected Void doInBackground(Long... longs) {
        long time = longs[0];
        while (time > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            time = time - 1000;
            publishProgress(time);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        callback.onTimeEnd();
    }

    @Override
    protected void onProgressUpdate(Long... longs) {
        super.onProgressUpdate(longs);
        callback.onTimeChange(longs[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onTimeEnd();
    }
}
