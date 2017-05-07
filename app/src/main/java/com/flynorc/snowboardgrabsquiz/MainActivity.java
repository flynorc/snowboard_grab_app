package com.flynorc.snowboardgrabsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int MIN_NR_QUESTIONS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
         * add the seek bar functionality
         * set up initial value, set up max value and add the onChange listeners
         */
        final SeekBar seekBar = (SeekBar)findViewById(R.id.number_of_questions_seekbar);
        final TextView seekBarValue = (TextView)findViewById(R.id.number_of_questions_textview);
        seekBarValue.setText(MIN_NR_QUESTIONS + "");
        seekBar.setMax( new SnowboardGrabs(this).getNrDefinedGrabs() - MIN_NR_QUESTIONS);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //update the text so user knows how many questions to expect
                seekBarValue.setText(String.valueOf(progress + MIN_NR_QUESTIONS));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // at the moment we only implement this to satisfy the OnSeekBarChangeListener requirement
                // (implement all abstract methods)
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // at the moment we only implement this to satisfy the OnSeekBarChangeListener requirement
                // (implement all abstract methods)
            }
        });


        /*
         * add the click listeners for buttons
         */
        findViewById(R.id.learn_grabs_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ExplainGrabsActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.take_quiz_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, QuizActivity.class);
                i.putExtra("nrQuestionsInQuiz", seekBar.getProgress() + MIN_NR_QUESTIONS);
                startActivity(i);
            }
        });

        findViewById(R.id.take_rubric_quiz_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RubricQuizActivity.class);
                startActivity(i);
            }
        });
    }
}
