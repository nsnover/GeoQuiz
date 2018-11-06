package com.example.student.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String MYTAG = "quizactivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String SCORE_INDEX = "score";
    private static final String TRUE_STATE = "truestate";
    private static final String FALSE_STATE = "falsestate";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private Button mCheatButton;
    private Button mCheckScore;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_europe, false),
            new Question(R.string.question_midearth, true),
            new Question(R.string.question_konoha, true),
            new Question(R.string.question_tea, false)
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private int mCurrentScore = 0;


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        saveInstanceState.putInt(SCORE_INDEX, mCurrentScore);
     /*   saveInstanceState.putBoolean (TRUE_STATE, mTrueButton.isEnabled());
        saveInstanceState.putBoolean (FALSE_STATE, mFalseButton.isEnabled()); */
    }


    private void togglebutton() {
        if (mTrueButton.isEnabled() == true) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else if (mTrueButton.isEnabled() == false) {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MYTAG, "called onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MYTAG, "called onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MYTAG, "called onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MYTAG, "called onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MYTAG, "called onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCurrentScore = savedInstanceState.getInt(SCORE_INDEX);
        /*    mTrueButton.setEnabled(savedInstanceState.getBoolean(TRUE_STATE));
            mFalseButton.setEnabled(savedInstanceState.getBoolean(FALSE_STATE)); */
        }

        Log.d(MYTAG, "called onCreate");

        setContentView(R.layout.activity_quiz);


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();


        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mCheckScore = (Button) findViewById(R.id.score_button);
        mCheckScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheckScore
                startActivity(new Intent(QuizActivity.this, CheckScore.class));
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                togglebutton();
                mPerformNextAction();

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                togglebutton();
                mPerformNextAction();
            }
        });

/*        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNextAction();
            }
        });
*/
/*        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0)
                    mCurrentIndex = mQuestionBank.length;
                mCurrentIndex=(mCurrentIndex-1);
                updateQuestion();

            }
        });
*/
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (resultCode == Activity.RESULT_OK)
//                Toast.makeText(this,"You cheated",Toast.LENGTH_LONG).show();
                Toast.makeText(this, "" + data.getBooleanExtra("EXTRA_ANSWER_SHOWN", false), Toast.LENGTH_LONG).show();
        }
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        if (userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()) {
            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mCurrentScore = (mCurrentScore + 1);
        } else {
            Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();

        }

    }

    public void mShowOKDialog(String title, String message) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        // add a button
        builder.setPositiveButton("OK", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void mPerformNextAction() {
        mCurrentIndex = (mCurrentIndex + 1); /* %mQuestionBank.length; */
        if (mCurrentIndex == 10) {
            mShowOKDialog("Score", mGetScoreText());
            mReset();
        }
        mIsCheater = false;
        updateQuestion();
        togglebutton();
    }

    private String mGetScoreText() {
        int mScore= (int) (mCurrentScore * 100f) / mQuestionBank.length;
        mScoreTextView.setText(mScore);
        return "Total Questions:" + Integer.toString(mQuestionBank.length) + " , Number Correct:" +
                Integer.toString(mCurrentScore) + " , Percentage Correct:" + String.valueOf(mScore) + "%, Your Letter Grade is: " + mGetLetterGrade(mScore);

    }

    private String mGetLetterGrade(int pScore) {
        if (pScore < 60)
            return "F";
        else if (pScore >= 60 && pScore < 69)
            return "D";
        else if (pScore >= 70 && pScore < 79)
            return "C";
        else if (pScore >= 80 && pScore < 89)
            return "B";
        else
            return "A";
    }

    private void mReset() {
        mCurrentIndex = 0;
        mCurrentScore = 0;
    }

}
