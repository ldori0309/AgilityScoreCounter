package com.example.android.agilityscorecounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Counter extends AppCompatActivity {

    private static Context context;
    int courseFaults = 0;
    String name;
    String breed;
    int time;
    int timeFaults = 0;
    Timer timer = new Timer();
    TextView sctTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        setContentView(R.layout.activity_counter);
        name = getIntent().getStringExtra("name");
        breed = getIntent().getStringExtra("breed");
        String sct = getIntent().getStringExtra("sct");
        displayName(name);
        displayBreed(breed);
        sctTextView = (TextView) findViewById(R.id.sctTextView);
        time = Integer.parseInt(sct.trim());
        sctTextView.setText("" + time);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time--;
                        if (time < 0) {
                            //sctTextView.setTextColor(Color.parseColor("#F44336"));
                            sctTextView.setTextColor(ContextCompat.getColor(Counter.context, R.color.red));
                            newTimeFault(-time);
                        }
                        displayTime(time);
                    }
                });
            }
        }, 1000, 1000);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("Time", time);
        savedInstanceState.putInt("CourseFaults", courseFaults);
        savedInstanceState.putInt("TimeFaults", timeFaults);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState !=null) {
            time = savedInstanceState.getInt("Time");
            courseFaults = savedInstanceState.getInt("CourseFaults");
            timeFaults = savedInstanceState.getInt("TimeFaults");
            if (time < 0)
                sctTextView.setTextColor(ContextCompat.getColor(Counter.context, R.color.red));
            displayFaults(courseFaults);
            displayTimeFaults(timeFaults);
            displayTime(time);
        }
    }

    public void displayTime(int time) {
        sctTextView.setText("" + time);
    }

    public void displayName(String name){
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        nameTextView.setText("" + name);
    }

    public void displayBreed(String breed){
        TextView breedTextView = (TextView) findViewById(R.id.breedTextView);
        breedTextView.setText("" + breed);
    }

    public void newFault(View view) {
        courseFaults = courseFaults + 5;
        displayFaults(courseFaults);
    }

    public void newTimeFault(int time) {
        timeFaults = time;
        displayTimeFaults(timeFaults);
    }

    public void displayFaults(int courseFaults) {
        TextView faultsTextView = (TextView) findViewById(R.id.faultsTextView);
        faultsTextView.setText("" + courseFaults);
    }

    public void displayTimeFaults(int timeFaults) {
        TextView timeFaultsTextView = (TextView) findViewById(R.id.timeFaultsTextView);
        timeFaultsTextView.setText("" + timeFaults);
    }

    public void finishCourse(View view) {
        timer.cancel();
        finish();
        Intent newResults = new Intent(this, Results.class);
        newResults.putExtra("name",name);
        newResults.putExtra("breed",breed);
        newResults.putExtra("totalFaults",courseFaults+timeFaults);
        startActivity(newResults);
    }

    public void elimination(View view) {
        courseFaults = 100;
        Button button = (Button) findViewById(R.id.finishButton);
        button.performClick();
    }



}
