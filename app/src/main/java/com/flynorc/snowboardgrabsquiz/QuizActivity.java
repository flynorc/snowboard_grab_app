package com.flynorc.snowboardgrabsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuizActivity extends AppCompatActivity {
    private ArrayList<SnowboardGrab> mGrabs;
    private int[] mQuestionIndexes;
    private int[] mPossibleAnswersForCurrentQuestion;
    private int mCurrentQuestionNr;
    private int mNrQuestionsAll;
    private int mNrQuestionsQuiz;
    private int mCorrectAnswer;
    private int mNrCorrectAnswers;
    private int mNrAnswers; //TODO - this could maybe be removed/replaced with mCurrentQuestionNr

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("currentQuestionNr", mCurrentQuestionNr);
        savedInstanceState.putInt("nrAnswers", mNrAnswers);
        savedInstanceState.putInt("nrCorrectAnswers", mNrCorrectAnswers);

        //savedInstanceState.putBoolean("MyBoolean", true);
        //savedInstanceState.putDouble("myDouble", 1.9);
        //savedInstanceState.putInt("MyInt", 1);
        //savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initialize();

        displayQuestion();
    }

    /*
     * initialize all class variables that will be needed for the quiz
     */
    private void initialize() {
        mGrabs = new SnowboardGrabs(this).getSnowboardGrabs();
        mNrQuestionsAll = mGrabs.size();
        mNrQuestionsQuiz = 10; //TODO change this - so that user can chose how many questions he wants
        mCorrectAnswer = -1;

        mCurrentQuestionNr = 0;
        mNrAnswers = 0;
        mNrCorrectAnswers = 0;

        //create the array holding indexes for questions (that we shuffle and have random order of questions for each quiz)
        mQuestionIndexes = new int[mNrQuestionsAll];
        for(int i = 0; i < mQuestionIndexes.length; i++) {
            mQuestionIndexes[i] = i;
        }
        shuffleArray(mQuestionIndexes);
    }

    /*
     * take question, generate the correct and false answers and update the layout
     */
    private void displayQuestion() {
        //get a question from the arrayList
        mCorrectAnswer = mQuestionIndexes[mCurrentQuestionNr];
        SnowboardGrab currentGrab = mGrabs.get(mCorrectAnswer);

        //update question number
        TextView nrQuestionsView = (TextView) findViewById(R.id.question_number);
        nrQuestionsView.setText( (mCurrentQuestionNr + 1) + " / " + mNrQuestionsQuiz);

        //update question image
        ImageView grabImageView = (ImageView) findViewById(R.id.question_image);
        grabImageView.setImageResource(currentGrab.getImageResourceId());

        //update rider orientation text
        TextView grabOrientationView = (TextView) findViewById(R.id.rider_orientation);
        if(currentGrab.riderIsRegular()) {
            grabOrientationView.setText(R.string.regular);
        }
        else {
            grabOrientationView.setText(R.string.goofy);
        }

        //update the buttons, set the text and the tag
        mPossibleAnswersForCurrentQuestion = generatePossibleAnswerIndexes();
        for (int i=0; i< mPossibleAnswersForCurrentQuestion.length; i++) {
            int resID = getResources().getIdentifier("quiz_button_" + i, "id" , getPackageName());
            Button currentButton = (Button) findViewById(resID);
            currentButton.setText(mGrabs.get(mPossibleAnswersForCurrentQuestion[i]).getName());
            currentButton.setTag(mPossibleAnswersForCurrentQuestion[i]);
        }
    }

    /*
     * function used for generating the possible answers for the current quiz question
     * it will return an array of integers, one of the elements will be the index of the correct answer (in the mGrabs arrayList)
     * other 3 will contain 3 other (unique) indices (the wrong answers shown)
     */
    private int[] generatePossibleAnswerIndexes() {
        //all grabs are possible candidates, except the correct answer, that one needs to be included for sure
        int[] allFalseOptions = new int[mNrQuestionsAll -1];
        int falseOptionsIndex = 0;
        for(int i=0; i<mNrQuestionsAll; i++) {
            if(mQuestionIndexes[i] != mCorrectAnswer) {
                allFalseOptions[falseOptionsIndex] = mQuestionIndexes[i];
                falseOptionsIndex++;
            }
        }

        //shuffle the all false options
        shuffleArray(allFalseOptions);

        //prepare the array for the results (the chosen 4)
        int[] selectedIndexes = new int[4];
        //take the first 3 false options from the shuffled array
        for(int i=0; i<3; i++) {
            selectedIndexes[i] = allFalseOptions[i];
        }

        //last element is the correct answer (before we shuffle the options)
        selectedIndexes[3] = mCorrectAnswer;

        shuffleArray(selectedIndexes);

        return selectedIndexes;
    }

    /*
     * function is triggered anytime user clicks on one of the answer buttons in the quiz
     * first it checks if the answer was correct or false and then shows next question or the quiz results
     * depending if there are any questions left
     */
    public void checkAnswer(View v) {
        int answer = Integer.parseInt(v.getTag().toString());
        mNrAnswers++;
        if(answer == mCorrectAnswer) {
            mNrCorrectAnswers++;
        }

        //check if all questions were answered
        if(mNrAnswers == mNrQuestionsQuiz) {
            finishQuiz();
        }
        else {
            mCurrentQuestionNr++;
            displayQuestion();
        }
    }

    /*
     * show the results of the quiz
     */
    private void finishQuiz() {
        setContentView(R.layout.quiz_results);

        //update the percentage
        TextView resultView = (TextView) findViewById(R.id.quiz_result_value);
        int successPercentage = Math.round((float) mNrCorrectAnswers/mNrAnswers * 100);
        resultView.setText(successPercentage + " %");

        //update the result message based on the "grade"
        String grade = getGradeIdentifier(successPercentage);
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
        Intent i = new Intent(QuizActivity.this, MainActivity.class);
        startActivity(i);
    }

    /*
     * helper method for shuffling arrays
     * used to randomize the order of questions and choosing different (random) answers for each question
     * implementation of Fisher–Yates shuffle
     */
    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    /*
     * helper function to map the result percentage to a grade (letter/string)
     * used to show the corresponding result text and image
     */
    static String getGradeIdentifier (int result) {
        if(result == 100)
            return "perfect";

        if(result >= 90)
            return "a";

        if(result >= 80)
            return "b";

        if(result >= 65)
            return "c";

        if(result >= 50)
            return "d";

        return "f";
    }


}
