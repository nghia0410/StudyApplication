package uef.com.studyapplication.acitivity;

import android.os.Bundle;

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

        NewAssignment assignment = getIntent().getParcelableExtra("assignment");
        users = getIntent().getParcelableArrayListExtra("users");

        UserArrayAdapter userArrayAdapter = new UserArrayAdapter(this, users, assignment);
        binding.lvUsers.setAdapter(userArrayAdapter);
        binding.txtCourse.setText(assignment.getCourse());
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }


}