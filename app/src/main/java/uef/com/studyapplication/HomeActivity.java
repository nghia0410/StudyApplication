package uef.com.studyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity  extends AppCompatActivity {
    private Button btnWork,btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_trac_nghiem);
        btnAdd = findViewById(R.id.btn_Add);
        btnWork = findViewById(R.id.btn_Work);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,CreateQuestionActivity.class);
                startActivity(intent);
            }
        });btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuizListActivity.class);
                startActivity(intent);
            }
        });

    }


}