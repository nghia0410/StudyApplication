package uef.com.studyapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionActivity extends AppCompatActivity {
    private EditText editTitle, editQuestion, editOption1, editOption2, editOption3;
    private Button btnSaveQuestion;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<CheckBox> checkBoxList;
    private List<Question> questionList; // Thêm biến questionList ở đây
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        editTitle = findViewById(R.id.edit_Title);
        editQuestion = findViewById(R.id.edit_question);
        editOption1 = findViewById(R.id.edit_option1);
        editOption2 = findViewById(R.id.edit_option2);
        editOption3 = findViewById(R.id.edit_option3);
        btnSaveQuestion = findViewById(R.id.btn_saveQuestion);
        recyclerView = findViewById(R.id.recycler_view);
        checkBoxList = new ArrayList<>();
        checkBoxList.add(findViewById(R.id.checkbox_option1));
        checkBoxList.add(findViewById(R.id.checkbox_option2));
        checkBoxList.add(findViewById(R.id.checkbox_option3));

        // Khởi tạo Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("quizzes"); // Thay đổi đường dẫn đến nút chứa bài kiểm tra

        btnSaveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestion();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        questionList = new ArrayList<>(); // Khởi tạo questionList ở đây
        adapter = new QuestionAdapter(questionList); // Khởi tạo adapter ở đây
        recyclerView.setAdapter(adapter);

        // Lắng nghe sự thay đổi trên Firebase Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Không cần làm gì ở đây vì chúng ta sẽ thêm câu hỏi mới thủ công
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }

    private void saveQuestion() {
        String Title = editTitle.getText().toString().trim();
        String question = editQuestion.getText().toString().trim();
        String option1 = editOption1.getText().toString().trim();
        String option2 = editOption2.getText().toString().trim();
        String option3 = editOption3.getText().toString().trim();

        if (TextUtils.isEmpty(question)) {
            Toast.makeText(this, "Vui lòng nhập câu hỏi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(option1) || TextUtils.isEmpty(option2) || TextUtils.isEmpty(option3)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ các phương án trả lời", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);
        options.add(option3);

        List<Integer> correctAnswerIndices = new ArrayList<>();

        for (int i = 0; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isChecked()) {
                correctAnswerIndices.add(i);
            }
        }


        // Tạo đối tượng Question mới và lưu vào Firebase
        Question newQuestion = new Question(Title, question, options, correctAnswerIndices);
        questionList.add(newQuestion); // Thêm câu hỏi mới vào questionList
        adapter.notifyItemInserted(questionList.size() - 1); // Thông báo cho adapter biết rằng đã có thêm một item mới được thêm vào ở vị trí cuối cùng của danh sách

        // Tiếp tục với các dòng code hiện tại
        String quizKey = Title; // Sử dụng title làm key cho bài kiểm tra
        String questionKey = databaseReference.child(quizKey).child("questions").push().getKey(); // Lấy key mới cho câu hỏi
        if (questionKey != null) {
            databaseReference.child(quizKey).child("questions").child(questionKey).setValue(newQuestion); // Lưu câu hỏi vào Firebase
        }

        // Xóa dữ liệu đã nhập trong EditText
        editQuestion.setText("");
        editOption1.setText("");
        editOption2.setText("");
        editOption3.setText("");

        // Xóa trạng thái của các checkbox
        for (CheckBox checkBox : checkBoxList) {
            checkBox.setChecked(false);
        }
    }
}
