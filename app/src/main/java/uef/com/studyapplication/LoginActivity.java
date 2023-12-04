package uef.com.studyapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
public class LoginActivity extends AppCompatActivity {
    //    static FirebaseStorage storage = FirebaseStorage.getInstance();;
//    StorageReference storageRef = storage.getReference();
    FirebaseFirestore db;
    static DocumentSnapshot userDocument;
    static User user = new User();
    private LinearLayout layoutSignUp;
    private TextView tvSignUp, tvUsername, tvPassword;
    private Button btn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tvUsername.getText().toString();
                String password = tvPassword.getText().toString();
                if (username.isEmpty()) {
//                    AnimationForLoginFail();
                    Toast.makeText(LoginActivity.this, "Please fill username", Toast.LENGTH_SHORT).show();
                } else {
                    // Truy vấn tài liệu trong Firestore để kiểm tra thông tin đăng nhập.
                    db.collection("users")
                            .whereEqualTo("username", username)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (!task.getResult().isEmpty()) {
                                            userDocument = task.getResult().getDocuments().get(0); // Lấy tài liệu đầu tiên (nếu có).
                                            user = userDocument.toObject(User.class);
//                                            Log.v(TAG, "user data fetched");
                                            if (user != null && user.getPassword().equals(password)) {
                                                // Đăng nhập thành công.
//                                                AnimationForLoginSuccess();
//                                                try {
////                                                    syncCloud();
//                                                } catch (IOException e) {
//                                                    throw new RuntimeException(e);
//                                                }
//                                                if (rememberCheck.isChecked()) {
//                                                    // Ghi chú TT và trạng thái người dùng vào SharedPreferences
//                                                    setBooleanDefaults(getString(R.string.userlogged), true, LoginActivity.this);
//                                                    setStringDefaults(getString(R.string.userid), userDocument.getId(), LoginActivity.this);
//                                                    Log.v("Login State", "true");
//                                                } else {
//                                                    setBooleanDefaults(getString(R.string.userlogged), false, LoginActivity.this);
//                                                    Log.v("Login State", "false");
//                                                }
//                                                PopulateList.UpdateL(db, LoginActivity.this);
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Sai mật khẩu.
                                                //    AnimationForLoginFail();
                                                Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Sai tên đăng nhập.
                                            //   AnimationForLoginFail();
                                            Toast.makeText(LoginActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Xử lý lỗi.
                                        Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
//                startActivity(intent);
            }
        });
//            initUi();
//       initListener();
    }
    private void addControl() {
        // Code using Cloud Firebase
        db = FirebaseFirestore.getInstance();
        tvSignUp = findViewById(R.id.tvSignUp);
        tvUsername = findViewById(R.id.Username);
        tvPassword = findViewById(R.id.Password);
        btn_Login = findViewById(R.id.btn_Login);
//        rememberCheck = findViewById(R.id.checkBox);
    }
//    private void initListener() {
////        layoutSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
////                startActivity(intent);
//            }
//        });
//    }
}