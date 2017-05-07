package com.flynorc.snowboardgrabsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RubricQuizActivity extends AppCompatActivity {
    private static final int NR_QUESTIONS = 4;
    private String mName;
    private int mScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubric_quiz);



        //add the onclick handler for submit button
        findViewById(R.id.back_to_main_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RubricQuizActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        findViewById(R.id.basic_quiz_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuiz();
            }
        });

    }

    /*
     * primitive implementation of "hardcoded" checking which checkboxes are checked and comparing the input of EditText to predefined strings
     * and checking how many answers are correct, then displaying a toast
     * references to checkboxes and radio buttons should be stored to class variables in the onCreate method, if they were meant to be reused
     * since this is just a demo quiz, I left them in the functions (that are not meant to run more than a few times in the next years)
     */
    private void checkQuiz() {
        EditText nameField = (EditText) findViewById(R.id.person_name);
        mName = nameField.getText().toString();

        mScore = 0;
        mScore += checkAnswer1();
        mScore += checkAnswer2();
        mScore += checkAnswer3();
        mScore += checkAnswer4();

        int result = Math.round((float) mScore/NR_QUESTIONS * 100);

        Toast toast = Toast.makeText(getApplicationContext(), String.format(getResources().getString(R.string.basic_result), mName, result),Toast.LENGTH_LONG);
        toast.show();
    }

    //hardcoded check for correct answer for question 1
    private int checkAnswer1() {
        RadioButton correctRadioButton = (RadioButton) findViewById(R.id.question_1_right);
        if(correctRadioButton.isChecked()) {
            return 1;
        }
        return 0;
    }

    //hardcoded check for correct answer for question 2
    private int checkAnswer2() {
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox_2_1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox_2_2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox_2_3);
        CheckBox checkbox4 = (CheckBox) findViewById(R.id.checkbox_2_4);

        //condition fails as soon as one checkbox is not correct
        if(checkbox1.isChecked() && !checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked()) {
            return 1;
        }

        return 0;
    }

    //hardcoded check for correct answer for question 3
    private int checkAnswer3() {
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox_3_1);
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox_3_2);
        CheckBox checkbox3 = (CheckBox) findViewById(R.id.checkbox_3_3);
        CheckBox checkbox4 = (CheckBox) findViewById(R.id.checkbox_3_4);

        //condition fails as soon as one checkbox is not correct
        if(checkbox1.isChecked() && checkbox2.isChecked() && !checkbox3.isChecked() && !checkbox4.isChecked()) {
            return 1;
        }

        return 0;
    }

    //hardcoded check for correct answer for question 4
    private int checkAnswer4() {
        EditText answerField = (EditText) findViewById(R.id.question_4_edittext);
        String answerVal = answerField.getText().toString();
        if(answerVal.equals(getString(R.string.answer_4_1)) || answerVal.equals(getString(R.string.answer_4_2)) || answerVal.equals(getString(R.string.answer_4_3))) {
            return 1;
        }

        return 0;
    }
}
