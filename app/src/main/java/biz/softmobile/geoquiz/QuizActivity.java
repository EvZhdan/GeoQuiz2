package biz.softmobile.geoquiz;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button resetButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private TextView reportPercentTrue;
    private int trueAnswers;
    private int allClicks;
    private Button mCheatButton;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),};

    private int mCurrentIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.text);
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.id_true_button);
        mFalseButton = (Button) findViewById(R.id.id_false_button);

        resetButton = (Button) findViewById(R.id.id_reset);

        resetButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               reset();
               correct();
           }
       });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 checkAnswer(true);
                 view.setClickable(false);
                  mFalseButton.setClickable(false);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                view.setClickable(false);
                mTrueButton.setClickable(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.id_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                mFalseButton.setClickable(true);
                mTrueButton.setClickable(true);
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start CheatActivity
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.id_prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mCurrentIndex==0) mCurrentIndex = mQuestionBank.length;
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
                mFalseButton.setClickable(true);
                mTrueButton.setClickable(true);
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }
    private void updateQuestion(){
//        Log.d(TAG,"Updating question text", new Exception());
       int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        this.allClicks++;
        int messageResId = 0;

        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            trueAnswers++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        correct();
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    public void correct(){
        double percent = (double) trueAnswers/allClicks * 100;
        reportPercentTrue = (TextView) findViewById(R.id.percent);
        reportPercentTrue.setText("trueAnswers: "+trueAnswers+"; allClicks: " +allClicks  + "; " + String.format("%.2f",percent));
    }

    public void reset(){
        this.allClicks = 0;
        this.trueAnswers = 0;
    }

  @Override
  public void onResume(){
      super.onResume();
      Log.d(TAG,"onResume called");
  }
  @Override
  public void onPause(){
      super.onPause();
      Log.d(TAG,"onPause called");
  }
    @Override
    public  void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }
  @Override
  public void onStop(){
      super.onStop();
      Log.d(TAG,"onStop called");
  }

  @Override
  public void onDestroy(){
      super.onDestroy();
      Log.d(TAG,"onDestroy called");
  }




}
