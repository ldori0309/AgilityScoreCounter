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
    int timeFaults;
    Timer timer = new Timer();

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
        final TextView sctTextView = (TextView) findViewById(R.id.sctTextView);
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
                        sctTextView.setText("" + time);
                    }
                });
            }
        }, 1000, 1000);
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
        setContentView(R.layout.activity_qualification);
        setResultText();
    }

    public void setResultText() {
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        int totalFaults = courseFaults + timeFaults;
        String qualification = calculateQualification(totalFaults);
        if (!qualification.equals("No Qualification")) {
            //resultTextView.setText("Congratulations! The dog named " + name.toUpperCase() + " of breed " + breed.toUpperCase() + " has earned the qualification " + qualification.toUpperCase() + " with " + (courseFaults + timeFaults) + " faults!");
            resultTextView.setText(getResources().getString(R.string.resultMessage1, name.toUpperCase(), breed.toUpperCase(), qualification.toUpperCase(), totalFaults));
        }
        else {
            //resultTextView.setText("The dog " + name.toUpperCase() + " of breed " + breed.toUpperCase() + " has not earned a qualification this time! Don't give up, next time you'll do better!");
            resultTextView.setText(getResources().getString(R.string.resultMessage2, name.toUpperCase(), breed.toUpperCase()));
        }
    }

    public String calculateQualification(int faults) {
        if (faults < 6) return "Excellent";
        else if (faults < 16) return "Very Good";
        else if (faults < 26) return "Good";
        else return "No Qualification";
    }

    public void elimination(View view) {
        courseFaults = 100;
        Button button = (Button) findViewById(R.id.finishButton);
        button.performClick();
    }

    public void newRound(View v) {
        finish();
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }

}
