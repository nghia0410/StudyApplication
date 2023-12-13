package uef.com.studyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainQuizActivity extends AppCompatActivity {

    private Button startBtn;
    private TextView ScoreTextView;
    private TextView AlertTextView;
    private TextView QuestionTextView;
    private TextView FinalScoreTextView;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private CountDownTimer countDownTimer;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout lastLayout;
    private Button buttonPlayAgain;


    Random random =new Random();
    int a;
    int b;
    int indexOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int points = 0;
    int totalQuestions = 0;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);
        startBtn=(Button)findViewById(R.id.btnStart);

        ScoreTextView = findViewById(R.id.ScoreTextView);
        FinalScoreTextView=findViewById(R.id.FinalscoreTextView);
        AlertTextView = findViewById(R.id.AlertTextView);
        QuestionTextView =findViewById(R.id.QuestionTextView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttonPlayAgain=findViewById(R.id.buttonPlayAgain);

        constraintLayout=findViewById(R.id.quizUi);
        lastLayout=findViewById(R.id.lastUi);

        lastLayout.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);


    }

    @SuppressLint("SetTextI18n")
    public void NextQuestion(){
        a = random.nextInt(10);
        b = random.nextInt(10);
        QuestionTextView.setText(a+"+"+b);

        indexOfCorrectAnswer = random.nextInt(4);
        answers.clear();
        for(int i = 0; i<4; i++){

            if(indexOfCorrectAnswer == i){
                answers.add(a+b);
            }else {
                int wrongAnswer = random.nextInt(20);
                while(wrongAnswer==a+b){

                    wrongAnswer = random.nextInt(20);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void optionSelect(View view){
        totalQuestions++;
        if(Integer.toString(indexOfCorrectAnswer).equals(view.getTag().toString())){
            points++;
            AlertTextView.setText("Correct");

        }else {
            AlertTextView.setText("Wrong");
        }

        ScoreTextView.setText(Integer.toString(points)+"/"+Integer.toString(totalQuestions));

        NextQuestion();
    }

    public void playAgain(View view){
        points=0;
        totalQuestions=0;
        ScoreTextView.setText(Integer.toString(points)+"/"+Integer.toString(totalQuestions));
        countDownTimer.start();
        lastLayout.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);

    }





    public void start(View view) {
        startBtn.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        NextQuestion();


    }
}