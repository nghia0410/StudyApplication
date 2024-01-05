package uef.com.studyapplication;

import static uef.com.studyapplication.LoginActivity.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GradeAssignment extends AppCompatActivity {

    double percentage;
    int total_question, correct_answers;
    ImageView iv_certificate;
    TextView tv_percentage, tv_total_question, tv_correct_answers, tv_result, tv_wrong_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_assignment);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            total_question = extras.getInt("NUM_OF_QUESTION");
            correct_answers = extras.getInt("CORRECT_ANSWER");
            percentage = extras.getDouble("PERCENTAGE_SCORE");
        }

        tv_correct_answers = findViewById(R.id.textViewCorrectAnswer);
        tv_percentage =findViewById(R.id.textViewPercentScore);
        tv_total_question = findViewById(R.id.textViewTotalQuestion);
        tv_result = findViewById(R.id.textViewResultMessage);
        tv_wrong_answer = findViewById(R.id.textViewWrongAnswer);
        iv_certificate = findViewById(R.id.imageViewCertificate);

        tv_correct_answers.setText(String.valueOf(correct_answers));

        if(percentage == 1)
            tv_percentage.setText(String.format("100%"));
        else
            tv_percentage.setText(String.format("%.0f%%",percentage));

        tv_total_question.setText(String.valueOf(total_question));
        tv_wrong_answer.setText(String.valueOf(total_question - correct_answers));

        if(percentage > 0.8){
            tv_result.setText("Chúc mừng bạn đạt được chứng chỉ!");
            int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, this.getResources().getDisplayMetrics());
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setSubpixelText(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(textSize);
            paint.setColor(Color.BLACK);


            Bitmap certificate = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.certificate);
            Bitmap mutableBitmap = certificate.copy(Bitmap.Config.ARGB_8888, true);

            Canvas myCanvas = new Canvas(mutableBitmap);

            myCanvas.drawText(user.getUsername(), 1350, 1300, paint);

            iv_certificate.setImageDrawable(new BitmapDrawable(getResources(), mutableBitmap));
        }
        else {
            tv_result.setText("Rất tiếc, Vui lòng làm lại bài tập");
        }

    }
}