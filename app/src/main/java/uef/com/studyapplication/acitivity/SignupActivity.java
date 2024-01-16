package uef.com.studyapplication.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import uef.com.studyapplication.R;
import uef.com.studyapplication.dto.Assignment;
import uef.com.studyapplication.dto.User;

public class SignupActivity extends AppCompatActivity {

    TextView tvLogin;

    EditText etUsername, etPassword, etConfirmPassword;

    Button btnSignup;
    DocumentReference docRef;
    public static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");


    // Code using Cloud Firebase
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addControl();
        addEvent();
    }

    private void initUi() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
    }

    private void initListener() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignup();
            }
        });
    }

    private void onClickSignup() {

    }

    private void addEvent() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Code using Cloud fire database
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmpassword = etConfirmPassword.getText().toString();

                if (confirmpassword.isEmpty() || password.isEmpty() || username.isEmpty()) {
//                    AnimationForSignUpFail();
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra xem người dùng đã cung cấp đủ thông tin chưa.
                    if (password.isEmpty() || username.isEmpty()) {
//                        AnimationForSignUpFail();
                        Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    } else if (!confirmpassword.equals(password)) {
//                        AnimationForSignUpFail();
                        Toast.makeText(SignupActivity.this, "Passwords do not match together", Toast.LENGTH_SHORT).show();
                    } else {
                        signUpWithFirebaseAuth(username, password);
//                         Trước khi thêm tài khoản, kiểm tra xem tài khoản đã tồn tại chưa.
//            db.collection("users")
//                    .whereEqualTo("username", username)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                      @Override
//                      public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                          if (!task.getResult().isEmpty()) {
//                            // Tài khoản đã tồn tại.
////                                                AnimationForSignUpFail();
//                            Toast.makeText(SignupActivity.this, "Username is already taken", Toast.LENGTH_SHORT).show();
//                          } else {
//                            // Tài khoản chưa tồn tại, thêm vào Firestore.
//                            User user = new User(username, password);
//
//                            db.collection("users")
//                                    .add(user)
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                      @Override
//                                      public void onSuccess(DocumentReference documentReference) {
////                                                                AnimationForSignUpSuccess();
//                                        docRef = documentReference;
//                                        firstMsg();
//                                        Toast.makeText(SignupActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
//
//                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//                                        startActivity(intent);
//                                      }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                      @Override
//                                      public void onFailure(@NonNull Exception e) {
////                                                                AnimationForSignUpFail();
//                                        Toast.makeText(SignupActivity.this, "Fail to register", Toast.LENGTH_SHORT).show();
//                                      }
//                                    });
//                          }
//                        } else {
//                          // Xử lý lỗi.
//                          Toast.makeText(SignupActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                      }
//                    });

//                        signUpWithFirebaseAuth(username, password);
                    }
                }
            }

        });
    }

    ///sử dụng firebase auth để đăng ký
    private void signUpWithFirebaseAuth(String email, String password) {
        auth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = new User(firebaseUser);
                            user.setType("user");
                            saveUserToDatabase(user);
                        }

                    } else {
                        Log.w("SIGN UP", "Failed to sign up", task.getException());
                        Toast.makeText(this, "Account already exists.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToDatabase(User user) {
        db.collection("users")
                .document(user.getUuid())
                .set(user)
                .addOnSuccessListener(runnable -> {
                    docRef = db.collection("users").document(user.getUuid());
                    firstMsg();
                    Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void addControl() {
        // Code using Cloud Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        tvLogin = findViewById(R.id.tvLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
    }

    private void firstMsg() {
        // Lấy ID của người dùng hiện tại từ Firebase Authentication
        String id = docRef.getId();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Tạo một Object chứa dữ liệu để lưu vào Firestore
        Assignment assignmentData = new Assignment();
        assignmentData.setCourse("Welcome to EduLearn ");
        assignmentData.setCreateTime(String.valueOf(timestamp));

        // Lưu dữ liệu vào Firestore trong bảng "assignments" của người dùng hiện tại
        db.collection("users").document(id)
                .collection("assignment")
                .add(assignmentData)
                .addOnSuccessListener(documentReference -> {
                    // Xử lý khi dữ liệu được lưu thành công
                    Toast.makeText(SignupActivity.this, "Dữ liệu đã được lưu thành công vào Firestore.", Toast.LENGTH_SHORT).show();
                    // Điều hướng hoặc thực hiện các hành động cần thiết sau khi lưu dữ liệu thành công
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi dữ liệu không thể được lưu vào Firestore
                    Toast.makeText(SignupActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}