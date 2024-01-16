package uef.com.studyapplication.acitivity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import uef.com.studyapplication.adapter.UserArrayAdapter;
import uef.com.studyapplication.databinding.ActivityAdminAssignmentBinding;
import uef.com.studyapplication.dto.NewAssignment;
import uef.com.studyapplication.dto.User;
import uef.com.studyapplication.service.AssignmentService;

public class AdminAssignmentActivity extends AppCompatActivity {
    ActivityAdminAssignmentBinding binding;
    AssignmentService service = AssignmentService.getInstance();
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try{
            // Lấy danh sách bài tập từ người dùng
            NewAssignment assignment = getIntent().getParcelableExtra("assignment");
            users = getIntent().getParcelableArrayListExtra("users");

            // Lấy chứng chỉ của User
            UserArrayAdapter userArrayAdapter = new UserArrayAdapter(this, users, assignment);
            binding.lvUsers.setAdapter(userArrayAdapter);
            binding.txtCourse.setText(assignment.getCourse());
            binding.btnBack.setOnClickListener(view -> {
                finish();
            });
        }catch (Exception e){
            Log.w("AdminAssignment","Nothing to be seen");
        }
    }
}