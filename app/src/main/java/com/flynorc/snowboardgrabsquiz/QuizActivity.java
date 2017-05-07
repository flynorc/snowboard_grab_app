package com.flynorc.snowboardgrabsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

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
    private SnowboardGrab mCurrentGrab;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("currentQuestionNr", mCurrentQuestionNr);
        savedInstanceState.putInt("nrAnswers", mNrAnswers);
        savedInstanceState.putInt("nrCorrectAnswers", mNrCorrectAnswers);
        savedInstanceState.putInt("correctAnswer",mCorrectAnswer);

        savedInstanceState.putSerializable("questionIndexes", mQuestionIndexes);
        savedInstanceState.putSerializable("possibleAnswersForCurrentQuestion", mPossibleAnswersForCurrentQuestion);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //get the number of questions
        mNrQuestionsQuiz =  getIntent().getIntExtra("nrQuestionsInQuiz",5);

        /*
         * restoring saved state of the quiz in case phone changes rotation, activity is paused,...
         * check if saved state exists. if so, load it, otherwise start new quiz
         */
        if (savedInstanceState == null) {
            //no saved state
            initializeNewQuiz();
            generateVariablesForNextQuestion();
            displayQuestion();
        }
        else {
            //call initialization function that only loads variables not changed by quiz
            initialize();

            //existing state, load it and then display question
            mCurrentQuestionNr = savedInstanceState.getInt("currentQuestionNr");
            mNrAnswers = savedInstanceState.getInt("nrAnswers");
            mNrCorrectAnswers = savedInstanceState.getInt("nrCorrectAnswers");
            mCorrectAnswer = savedInstanceState.getInt("correctAnswer");

            mQuestionIndexes = (int[]) savedInstanceState.getSerializable("questionIndexes");
            mPossibleAnswersForCurrentQuestion = (int[]) savedInstanceState.getSerializable("possibleAnswersForCurrentQuestion");;

            mCurrentGrab = mGrabs.get(mCorrectAnswer);

            displayQuestion();
        }

        initialize();
    }

    private void initializeNewQuiz() {
        //also call the general initialize method
        initialize();

        //initialize all the variables that should only be initialized if we are creating a new quiz (not continuing existing quiz)
        mCurrentQuestionNr = 0;
        mNrAnswers = 0;
        mNrCorrectAnswers = 0;
        mCorrectAnswer = -1;

        //create the array holding indexes for questions (that we shuffle and have random order of questions for each quiz)
        mQuestionIndexes = new int[mNrQuestionsAll];
        for(int i = 0; i < mQuestionIndexes.length; i++) {
            mQuestionIndexes[i] = i;
        }
        shuffleArray(mQuestionIndexes);
    }

    /*
     * initialize all class variables that are not changing, and can be always created new
     * so there is no need to pass them to the saveInstanceState
     */
    private void initialize() {
        mGrabs = new SnowboardGrabs(this).getSnowboardGrabs();
        mNrQuestionsAll = mGrabs.size();
    }

    private void generateVariablesForNextQuestion() {
        //get a question from the arrayList
        mCorrectAnswer = mQuestionIndexes[mCurrentQuestionNr];
        mCurrentGrab = mGrabs.get(mCorrectAnswer);
        mPossibleAnswersForCurrentQuestion = generatePossibleAnswerIndexes();
    }

    /*
     * take question, generate the correct and false answers and update the layout
     */
    private void displayQuestion() {
        //update question number
        TextView nrQuestionsView = (TextView) findViewById(R.id.question_number);
        nrQuestionsView.setText( (mCurrentQuestionNr + 1) + " / " + mNrQuestionsQuiz);

        //update question image
        ImageView grabImageView = (ImageView) findViewById(R.id.question_image);
        grabImageView.setImageResource(mCurrentGrab.getImageResourceId());

        //update rider orientation text
        TextView grabOrientationView = (TextView) findViewById(R.id.rider_orientation);
        if(mCurrentGrab.riderIsRegular()) {
            grabOrientationView.setText(R.string.regular);
        }
        else {
            grabOrientationView.setText(R.string.goofy);
        }

        //update the buttons, set the text and the tag

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

            Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_correct,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 150);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_wrong,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 150);
            toast.show();
        }

        //check if all questions were answered
        if(mNrAnswers == mNrQuestionsQuiz) {
            finishQuiz();
        }
        else {
            mCurrentQuestionNr++;
            generateVariablesForNextQuestion();
            displayQuestion();
        }
    }

    /*
     * show the results of the quiz
     * start the ResultActivity, pass the needed parameters
     */
    private void finishQuiz() {
        int successPercentage = Math.round((float) mNrCorrectAnswers/mNrAnswers * 100);
        String grade = getGradeIdentifier(successPercentage);

        Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
        i.putExtra("successPercentage", successPercentage);
        i.putExtra("grade", grade);
        startActivity(i);
        finish();
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
