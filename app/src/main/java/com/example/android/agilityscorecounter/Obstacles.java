package com.example.android.agilityscorecounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Obstacles extends AppCompatActivity {

    String name;
    String breed;
    String sct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obstacles);
    }

    public void backToMain(View view){
        finish();
        Intent newMain = new Intent(this, MainActivity.class);
        startActivity(newMain);
    }
}
