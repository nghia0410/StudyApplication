package uef.com.studyapplication.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import uef.com.studyapplication.AssignmentList;
import uef.com.studyapplication.R;
import uef.com.studyapplication.dto.User;

public class LoginActivity extends AppCompatActivity {
    static FirebaseStorage storage = FirebaseStorage.getInstance();;
    StorageReference storageRef = storage.getReference();
    FirebaseFirestore db;
    static DocumentSnapshot userDocument;
    FirebaseAuth auth;

    public static List mList = new ArrayList<AssignmentList>();
    public static User user = new User();
//    private LinearLayout layoutSignUp;
    private TextView tvSignUp, tvUsername, tvPassword, tvEmail;
    private LinearLayout layoutSignUp, layoutForgotPass;
    private Button btn_Login;
    CheckBox rememberCheck;
    String TAG = "LoginAct";

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
                String email = tvEmail.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill username", Toast.LENGTH_SHORT).show();
                    return;
                }
//                                 else {
//                    // Truy vấn tài liệu trong Firestore để kiểm tra thông tin đăng nhập.
//                    db.collection("users")
//                            .whereIn("username", Arrays.asList(username, email))
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        if (!task.getResult().isEmpty()) {
//                                            userDocument = task.getResult().getDocuments().get(0); // Lấy tài liệu đầu tiên (nếu có).
//
//                                            user = userDocument.toObject(User.class);
//                                            Log.v(TAG, "user data fetched");
//                                            if (user != null && user.getPassword().equals(password)) {
//                                                // Đăng nhập thành công.
////                                                AnimationForLoginSuccess();
//                                                try {
//                                                    syncCloud();
//                                                } catch (IOException e) {
//                                                    throw new RuntimeException(e);
//                                                }
//                                                if (rememberCheck.isChecked()) {
//                                                    // Ghi chú TT và trạng thái người dùng vào SharedPreferences
//                                                    setBooleanDefaults(getString(R.string.userlogged), true, LoginActivity.this);
//                                                    setStringDefaults("userid",userDocument.getId(), LoginActivity.this);
//                                                    Log.v("Login State", "true");
//                                                } else {
//                                                    setBooleanDefaults(getString(R.string.userlogged), false, LoginActivity.this);
//                                                    Log.v("Login State", "false");
//                                                }
//                                                UserList.UpdateL(db, LoginActivity.this);
//                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                                startActivity(intent);
//                                                Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                // Sai mật khẩu.
//                                                //    AnimationForLoginFail();
//                                                Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
//                                            }
//                                        } else {
//                                            // Sai tên đăng nhập.
//                                            //   AnimationForLoginFail();
//                                            Toast.makeText(LoginActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
//                                        }
//                                    } else {
//                                        // Xử lý lỗi.
//                                        Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
                    signIn(email, password);

            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        initUi();
        initListener();
    }
    private void signIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password are required ", Toast.LENGTH_LONG).show();

            return;
        }
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            if (authResult.getUser() == null) {
                Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                return;
            }
            user = new User(authResult.getUser());
            db.collection("users")
                    .document(user.getUuid())
                    .get()
                    .addOnCompleteListener(runnable -> {
                        userDocument = runnable.getResult();
                        user.setType(userDocument.getString("type"));
                        user.setFullname(userDocument.getString("fullname"));
                        user.setPhone(userDocument.getString("phone"));

                        //UserList.UpdateL(db, LoginActivity.this);

                        try {
                            syncCloud();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();

                    });


        }).addOnFailureListener(runnable -> {
            Toast.makeText(this, runnable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        });
    }
    private void addControl() {
        // Code using Cloud Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        tvSignUp = findViewById(R.id.tvSignUp);
        tvUsername = findViewById(R.id.Username);
        tvEmail = findViewById(R.id.Username);
        tvPassword = findViewById(R.id.Password);
        btn_Login = findViewById(R.id.btn_Login);
    }
    private void syncCloud() throws IOException {
        setupDir();
        File pfp = new File(getApplicationInfo().dataDir + "/user/pfp/userpfp.jpg");
        if(!pfp.exists()){
            downloadPfp();
        }
        else{
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void setupDir() throws IOException {
        Files.createDirectories(Paths.get(getApplicationInfo().dataDir + "/user/pfp"));
        Files.createDirectories(Paths.get(getApplicationInfo().dataDir + "/user/assignments"));
    }
    private void downloadPfp(){
        StorageReference pfpRef = storageRef.child(userDocument.getId()+"/userpfp.jpg");
        File localFile = new File(getApplicationInfo().dataDir + "/user/pfp/userpfp.jpg");
        pfpRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            // Local temp file has been created
//            UserList.UpdateL(db,LoginActivity.this);
            Log.v("DownloadPfp","pfp downloaded");
        }).addOnFailureListener(exception -> {
            // Handle any errors
//            UserList.UpdateL(db,LoginActivity.this);
            Log.v("DownloadPfp","failed");
        });
    }
    private void initUi() {
        layoutSignUp = findViewById(R.id.layout_signUp);
        layoutForgotPass = findViewById(R.id.layout_forgot_pass);
    }
    private void initListener() {
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        layoutForgotPass.setOnClickListener(view -> {
            if (tvEmail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter email first", Toast.LENGTH_LONG).show();
                return;
            }

            auth.sendPasswordResetEmail(tvEmail.getText().toString()).addOnCompleteListener(runnable -> {
                if (runnable.isSuccessful()) {
                    Toast.makeText(this, "Email for reset password sent", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(runnable -> {
                Toast.makeText(this, runnable.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            });
        });
    }

}