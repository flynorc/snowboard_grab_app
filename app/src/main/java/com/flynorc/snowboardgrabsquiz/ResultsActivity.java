package com.flynorc.snowboardgrabsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //get the parameters
        int successPercentage =  getIntent().getIntExtra("successPercentage",0);
        String grade = getIntent().getStringExtra("grade");

        //update the percentage
        TextView resultView = (TextView) findViewById(R.id.quiz_result_value);
        resultView.setText(successPercentage + " %");

        //update the result message based on the "grade"
        TextView resultMessageView = (TextView) findViewById(R.id.quiz_result_message);
        int resID = getResources().getIdentifier("grade_" + grade, "string", getPackageName());
        resultMessageView.setText(resID);

        //update the result image based on the "grade"
        ImageView resultImageView = (ImageView) findViewById(R.id.quiz_result_image);
        resID = getResources().getIdentifier("grade_" + grade, "drawable", getPackageName());
        resultImageView.setImageResource(resID);
    }


    /*
     * onclick handler to go back to the main activity
     */
    public void backToMain(View v) {
        Intent i = new Intent(ResultsActivity.this, MainActivity.class);
        startActivity(i);
    }
}
