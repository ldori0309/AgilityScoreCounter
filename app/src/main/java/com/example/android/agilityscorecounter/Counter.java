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
    String strings;
    String[] stringsArray = new String[3];
    int time;
    int timeFaults = 0;
    Timer timer = new Timer();

    TextView nameTextView;
    TextView breedTextView;
    TextView faultsTextView;
    TextView timeFaultsTextView;
    TextView sctTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        setContentView(R.layout.activity_counter);

        Bundle bundle = getIntent().getExtras();
        strings = bundle.getString("strings");
        stringsArray = strings.split("\\|");
/*        name = getIntent().getStringExtra("name");
        breed = getIntent().getStringExtra("breed");
        sct = getIntent().getStringExtra("sct");*/

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        breedTextView = (TextView) findViewById(R.id.breedTextView);
        faultsTextView = (TextView) findViewById(R.id.faultsTextView);
        timeFaultsTextView = (TextView) findViewById(R.id.timeFaultsTextView);
        sctTextView = (TextView) findViewById(R.id.sctTextView);

        displayString(stringsArray[0], nameTextView);
        displayString(stringsArray[1], breedTextView);

        time = Integer.parseInt(stringsArray[2].trim());
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
                            sctTextView.setTextColor(ContextCompat.getColor(Counter.context, R.color.red));
                            newTimeFault(-time);
                        }
                        displayString("" + time, sctTextView);
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
            displayString("" + courseFaults, faultsTextView);
            displayString("" + timeFaults, timeFaultsTextView);
            displayString("" + time, sctTextView);
        }
    }

    public void newFault(View view) {
        courseFaults += 5;
        displayString("" + courseFaults, faultsTextView);
    }

    public void newTimeFault(int time) {
        timeFaults = time;
        displayString("" + timeFaults, timeFaultsTextView);
    }

    public void displayString(String myString, TextView myTextView) {
        myTextView.setText(myString);
    }

    public void finishCourse(View view) {
        timer.cancel();
        finish();
        Intent newResults = new Intent(this, Results.class);
        String strings;
        strings = stringsArray[0] + "|";
        strings += stringsArray[1] + "|";
        strings += "" + (courseFaults + timeFaults);
        newResults.putExtra("strings",strings);
        startActivity(newResults);
    }

    public void elimination(View view) {
        courseFaults = 100;
        Button button = (Button) findViewById(R.id.finishButton);
        button.performClick();
    }

}
