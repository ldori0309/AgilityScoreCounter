package com.example.android.agilityscorecounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText dogName;
    EditText dogBreed;
    EditText sctTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dogName = (EditText) findViewById(R.id.dogName);
        dogBreed = (EditText) findViewById(R.id.dogBreed);
        sctTime = (EditText) findViewById(R.id.sctTime);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void showRules(View view) {
        Intent newRules = new Intent(this, Rules.class);
        startActivity(newRules);
    }

    public void showObstacles(View view){
        Intent newObstacles = new Intent(this, Obstacles.class);
        startActivity(newObstacles);
    }

    public void startRound(View view) {
        String name = dogName.getText().toString();
        if(name.trim().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.tyepName), Toast.LENGTH_SHORT).show();
            return;
        }

        String breed = dogBreed.getText().toString();
        if(breed.trim().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.typeBreed), Toast.LENGTH_SHORT).show();
            return;
        }

        String sct = sctTime.getText().toString();
        if(sct.trim().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.typeSct), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int time = Integer.parseInt(sct.trim());
        }
        catch (NumberFormatException nfe) {
            Toast.makeText(this, getResources().getString(R.string.validSct), Toast.LENGTH_SHORT).show();
            return;
        }

        finish();
        String strings;
        strings = name + "|";
        strings += breed + "|";
        strings += sct;
        Intent newCounter = new Intent(this, Counter.class);
        newCounter.putExtra("strings",strings);
        /*newCounter.putExtra("name",name);
        newCounter.putExtra("breed",breed);
        newCounter.putExtra("sct",sct);*/
        startActivity(newCounter);
    }

}
