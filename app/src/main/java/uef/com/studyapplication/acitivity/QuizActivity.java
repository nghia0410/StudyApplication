package uef.com.studyapplication.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uef.com.studyapplication.R;
import uef.com.studyapplication.adapter.AdapterCreateQuiz;
import uef.com.studyapplication.dto.Question;
import uef.com.studyapplication.dto.QuestionType;

public class QuizActivity extends AppCompatActivity {


    private ListView listView;
    private ArrayList<Question> questions;
    private AdapterCreateQuiz adapter;
    private Button save_btn, done_btn;
    private EditText et_question, et_answer1, et_answer2, et_answer3, et_answer4;
    private RadioGroup rd_answer;
    private List<String> data;
    private Spinner questionTypeSpinner;
    private LinearLayout multipleChoiceLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        // Thêm các câu hỏi vào danh sách
        questions = getIntent().getParcelableArrayListExtra("questions");
//        questions.add(new Question(et_question.getText().toString(), new String[]{et_answer1.getText().toString(), et_answer2.getText().toString(),et_answer3.getText().toString(),et_answer4.getText().toString()}, 0));


//        questions.add(new Question("Hình tròn có bao nhiêu đường đối xứng?", new String[]{"1", "2", "3", "4"}, 3));
//        questions.add(new Question("Công thức tính diện tích hình tròn là?", new String[]{"pi * r * r", "pi * r", "r * r", "2 * r"}, 0));

        // Tạo một đối tượng QuizAdapter và gắn nó vào ListView
        adapter = new AdapterCreateQuiz(questions);
        save_btn = findViewById(R.id.btn_saveQuestion);
        listView = findViewById(R.id.listView2);
        listView.setAdapter(adapter);
        et_question = findViewById(R.id.edit_question);
        et_answer1 = findViewById(R.id.edit_option1);
        et_answer2 = findViewById(R.id.edit_option2);
        et_answer3 = findViewById(R.id.edit_option3);
        et_answer4 = findViewById(R.id.edit_option4);
        rd_answer = findViewById(R.id.radiogroup);
        // Xử lý các sự kiện click hoặc thay đổi trạng thái của RadioButton
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Kiểm tra đáp án
                Question question = questions.get(position);
                int selectedRadioButtonId = ((RadioButton) view.findViewById(R.id.checkbox_option1)).getId();
                if (question.getAnswer().get(0) == selectedRadioButtonId) {
                    // Người dùng chọn đáp án đúng
                } else {
                    // Người dùng chọn đáp án sai
                }
            }

        });
        rd_answer = findViewById(R.id.radiogroup);

        multipleChoiceLayout = findViewById(R.id.multi_choice_layout);
        questionTypeSpinner = findViewById(R.id.question_type);
        ArrayAdapter<CharSequence> questionTypesAdapter = ArrayAdapter.createFromResource(this, R.array.question_types, android.R.layout.simple_spinner_item);
        questionTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionTypeSpinner.setAdapter(questionTypesAdapter);
        questionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        rd_answer.setVisibility(View.VISIBLE);
                        multipleChoiceLayout.setVisibility(View.GONE);
                        break;
                    case 1:
                        rd_answer.setVisibility(View.GONE);
                        multipleChoiceLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //DO NOTHING HERE
            }
        });
        save_btn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question newQuestion = new Question();
                int selectedType = questionTypeSpinner.getSelectedItemPosition();
                List<String> options = new ArrayList<>();
                options.add( et_answer1.getText().toString());
                options.add( et_answer2.getText().toString());
                options.add( et_answer3.getText().toString());
                options.add( et_answer4.getText().toString());
                newQuestion.setOptions(options);
                switch (selectedType) {
                    case 0:
                        newQuestion.setType(QuestionType.singleChoice);
                        int selectedRadioButtonId = rd_answer.getCheckedRadioButtonId();
                        int selectedPos = rd_answer.indexOfChild(findViewById(selectedRadioButtonId));
                        newQuestion.setAnswer(new ArrayList<>(Collections.singleton(selectedPos)));
                        break;
                    case 1:
                        CheckBox multi1 = findViewById(R.id.multi_choice_1);
                        CheckBox multi2 = findViewById(R.id.multi_choice_2);
                        CheckBox multi3 = findViewById(R.id.multi_choice_3);
                        CheckBox multi4 = findViewById(R.id.multi_choice_4);
                        newQuestion.setType(QuestionType.multiChoice);
                        ArrayList<Integer> answers = new ArrayList<>();
                        if (multi1.isChecked())
                            answers.add(0);
                        if (multi2.isChecked())
                            answers.add(1);
                        if (multi3.isChecked())
                            answers.add(2);
                        if (multi4.isChecked())
                            answers.add(3);
                        newQuestion.setAnswer(answers);
                        break;
                    default:
                        break;
                }


                newQuestion.setQuestion(et_question.getText().toString());

                questions.add(newQuestion); // Thêm câu hỏi mới vào questionList
//                questions.add(new Question(et_question.getText().toString(), new String[]{et_answer1.getText().toString(), et_answer2.getText().toString(),et_answer3.getText().toString(),et_answer4.getText().toString()}, 0));

                // Cập nhật adapter
                adapter.notifyDataSetChanged();

                // Xóa dữ liệu nhập liệu
                et_question.setText("");
                rd_answer.clearCheck();

                // Hiển thị kết quả (tùy chọn)
                showResult();
            }

        });

        done_btn = findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gửi Intent
//                Intent intent = new Intent(QuizActivity.this, CreateActivity.class);
//                intent.putExtra("EXTRA_DATA", questions);
//                startActivity(intent);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("questions", questions);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    // Hiển thị kết quả
    private void showResult() {
        // ...
    }
}