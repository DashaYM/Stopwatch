package ru.startandroid.stopwatch;

import android.content.res.Configuration;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    long stoppingTime = 0;
    long timeStart = 0;
    boolean isStarted;
    Chronometer myChron;
    final String LOG_TAG = "myLogs";

    private void setSizeDependOnOrientation(){

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            myChron.setTextSize((int) getResources().getDimension(R.dimen.chron_port));

            //myChron.setTextSize(R.dimen.chron_port);
            //Log.d(LOG_TAG, "port");
        }
        else {

            myChron.setTextSize((int) getResources().getDimension(R.dimen.chron_land));
            //Log.d(LOG_TAG, "land");
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment frag = getFragmentManager().findFragmentById(R.id.frgm);
        myChron = ((Chronometer) frag.getView().findViewById(R.id.chronometer1));

        setSizeDependOnOrientation();

        if (savedInstanceState == null) {
            myChron.setBase(SystemClock.elapsedRealtime());
            myChron.stop();
            isStarted = false;
        }
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("timeStop", stoppingTime);
        outState.putLong("timeStart", (myChron.getBase() - SystemClock.elapsedRealtime()));
        outState.putBoolean("isStarted", isStarted);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        stoppingTime = savedInstanceState.getLong("timeStop");
        timeStart = savedInstanceState.getLong("timeStart");
        isStarted = savedInstanceState.getBoolean("isStarted");

        if (isStarted) {
            myChron.setBase(SystemClock.elapsedRealtime() + timeStart);
            myChron.start();
        }
        else{
            myChron.setBase(SystemClock.elapsedRealtime() + stoppingTime);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                myChron.setBase(SystemClock.elapsedRealtime()+ stoppingTime);
                myChron.start();
                isStarted = true;
                stoppingTime = 0;
                return true;
            case R.id.item2:
                if (stoppingTime == 0) {
                    stoppingTime = myChron.getBase() - SystemClock.elapsedRealtime();
                }
                myChron.stop();
                isStarted = false;
                return true;
            case R.id.item3:
                stoppingTime = 0;
                myChron.setBase(SystemClock.elapsedRealtime());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

