package uef.com.studyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateActivity extends AppCompatActivity {

    private ImageButton return_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        return_btn = findViewById(R.id.returnButton);
//        tagSpinner = findViewById(R.id.tagSpinner);
//        customTagEditText = findViewById(R.id.customTagEditText);
//        customTagLayout = findViewById(R.id.customTagLayout);
//        okButton = findViewById(R.id.okButton);
//        selectionPrompt = findViewById(R.id.selectionPrompt);
//        btn1=  findViewById(R.id.createnew_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}