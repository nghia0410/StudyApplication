package uef.com.studyapplication;

import static uef.com.studyapplication.acitivity.LoginActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uef.com.studyapplication.acitivity.CertificateActivity;
import uef.com.studyapplication.acitivity.MainActivity;

public class GradeAssignment extends AppCompatActivity {

    double percentage;
    int total_question, correct_answers;
    Button btn_back, btn_certificate;
    TextView tv_percentage, tv_total_question, tv_correct_answers, tv_result, tv_wrong_answer;
    String course_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_assignment);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            total_question = extras.getInt("NUM_OF_QUESTION");
            correct_answers = extras.getInt("CORRECT_ANSWER");
            percentage = extras.getDouble("PERCENTAGE_SCORE");
            course_name = extras.getString("CERTIFICATE_NAME");
        }

        tv_correct_answers = findViewById(R.id.textViewCorrectAnswer);
        tv_percentage =findViewById(R.id.textViewPercentScore);
        tv_total_question = findViewById(R.id.textViewTotalQuestion);
        tv_result = findViewById(R.id.textViewResultMessage);
        tv_wrong_answer = findViewById(R.id.textViewWrongAnswer);
        btn_back = findViewById(R.id.btn_returnToQuiz);
        btn_certificate = findViewById(R.id.btn_getCertificate);

        tv_correct_answers.setText(String.valueOf(correct_answers));

        if(percentage == 1)
            tv_percentage.setText("100%");
        else
            tv_percentage.setText(String.format("%.0f%%",percentage * 100));

        tv_total_question.setText(String.valueOf(total_question));
        tv_wrong_answer.setText(String.valueOf(total_question - correct_answers));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GradeAssignment.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if(percentage > 0.8){
//            tv_result.setText("Chúc mừng bạn đạt được chứng chỉ!");
//            int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, this.getResources().getDisplayMetrics());
//            Paint paint = new Paint();
//            paint.setAntiAlias(true);
//            paint.setSubpixelText(true);
//            paint.setTextAlign(Paint.Align.CENTER);
//            paint.setStyle(Paint.Style.FILL);
//            paint.setTextSize(textSize);
//            paint.setColor(Color.BLACK);
//
//
//            Bitmap certificate = BitmapFactory.decodeResource(this.getResources(),
//                    R.drawable.certificate);
//            Bitmap mutableBitmap = certificate.copy(Bitmap.Config.ARGB_8888, true);
//
//            Canvas myCanvas = new Canvas(mutableBitmap);
//
//            myCanvas.drawText(user.getEmail(), 1350, 1300, paint);
//
//            iv_certificate.setImageDrawable(new BitmapDrawable(getResources(), mutableBitmap));
            tv_result.setText("Congratulations on achieving your certification!");
            btn_certificate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GradeAssignment.this, CertificateActivity.class);
                    intent.putExtra("user_name",user.getEmail());
                    intent.putExtra("CERTIFICATE_NAME",course_name);

                    startActivity(intent);
                }
            });

        }
        else {
            tv_result.setText("Sorry, please return to the course");
            btn_certificate.setVisibility(View.INVISIBLE);
        }

    }
}