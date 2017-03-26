package com.example.android.agilityscorecounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String name;
    private String breed;
    private String sct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showRules(View view) {
        setContentView(R.layout.activity_rules);
    }

    public void showObstacles(View view){
        setContentView(R.layout.activity_obstacles);
    }

    public void backToMain(View view){
        setContentView(R.layout.activity_main);
    }

    public void startRound(View view) {
        EditText dogName = (EditText) findViewById(R.id.dogName);
        name = dogName.getText().toString();
        if(name.trim().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.tyepName), Toast.LENGTH_SHORT).show();
            return;
        }

        EditText dogBreed = (EditText) findViewById(R.id.dogBreed);
        breed = dogBreed.getText().toString();
        if(breed.trim().equals("")) {
            Toast.makeText(this, getResources().getString(R.string.typeBreed), Toast.LENGTH_SHORT).show();
            return;
        }

        EditText sctTime = (EditText) findViewById(R.id.sctTime);
        sct = sctTime.getText().toString();
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
        Intent newCounter = new Intent(this, Counter.class);
        newCounter.putExtra("name",name);
        newCounter.putExtra("breed",breed);
        newCounter.putExtra("sct",sct);
        startActivity(newCounter);
    }

}
