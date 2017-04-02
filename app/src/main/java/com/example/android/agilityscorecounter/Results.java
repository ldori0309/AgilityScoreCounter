package com.example.android.agilityscorecounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    String strings;
    String[] stringsArray = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);

        Bundle bundle = getIntent().getExtras();
        strings = bundle.getString("strings");
        stringsArray = strings.split("\\|");
/*        name = getIntent().getStringExtra("name");
        breed = getIntent().getStringExtra("breed");
        totalFaults = getIntent().getIntExtra("totalFaults", 0);*/

        setResultText();
    }

    public void setResultText() {
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        String qualification = calculateQualification(Integer.parseInt(stringsArray[2].trim()));
        if (!qualification.equals("No Qualification")) {
            //resultTextView.setText("Congratulations! The dog named " + name.toUpperCase() + " of breed " + breed.toUpperCase() + " has earned the qualification " + qualification.toUpperCase() + " with " + (courseFaults + timeFaults) + " faults!");
            resultTextView.setText(getResources().getString(R.string.resultMessage1, stringsArray[0].toUpperCase(), stringsArray[1].toUpperCase(), qualification.toUpperCase(), Integer.parseInt(stringsArray[2].trim())));
        }
        else {
            //resultTextView.setText("The dog " + name.toUpperCase() + " of breed " + breed.toUpperCase() + " has not earned a qualification this time! Don't give up, next time you'll do better!");
            resultTextView.setText(getResources().getString(R.string.resultMessage2, stringsArray[0].toUpperCase(), stringsArray[1].toUpperCase()));
        }
    }

    public String calculateQualification(int faults) {
        if (faults < 6) return "Excellent";
        else if (faults < 16) return "Very Good";
        else if (faults < 26) return "Good";
        else return "No Qualification";
    }

    public void newRound(View v) {
        finish();
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }

}
