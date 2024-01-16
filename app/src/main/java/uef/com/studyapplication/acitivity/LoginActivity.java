package uef.com.studyapplication.acitivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogInflater = inflater.inflate(R.layout.dialog_change_password, null);
            builder.setView(dialogInflater);
            builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                EditText emailInput = dialogInflater.findViewById(R.id.edit_change_email);
                if (emailInput == null) return;
                String email = emailInput.getText().toString();
                // Validate email address using a regular expression
                Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    Toast.makeText(this, "Invalid email", Toast.LENGTH_LONG).show();
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(runnable -> {
                    Toast.makeText(this, "An email sent for reset password", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(runnable -> {
                    Toast.makeText(this, runnable.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                });
            });
            builder.show();

        });
    }

}