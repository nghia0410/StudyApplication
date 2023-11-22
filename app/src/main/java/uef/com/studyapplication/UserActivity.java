package uef.com.studyapplication;

import static uef.com.studyapplication.LoginActivity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;

public class UserActivity extends AppCompatActivity {
    private EditText fullName, phoneNumber, email;
    int SELECT_PICTURE = 200;
    Button apply_btn;
    ImageButton uploadImage_btn,return_btn,logout_btn;
    ImageView uploadedImage_view;
    FirebaseFirestore db;

    // Create a storage reference from our app
//    StorageReference storageRef;
    // Save login/logout state
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize variables
        initializeVar();

        if (user.getFullname() != null) fullName.setHint(user.getFullname());
        if (user.getEmail() != null) email.setHint(user.getEmail());
        if (user.getPhone() != null) phoneNumber.setHint(user.getPhone());

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update login state to false
//                setBooleanDefaults(getString(R.string.userlogged),false,UserActivity.this);
                Log.v("Login state","false");
                // Wipe files of current user
//                try {
//                    File dir = new File(getApplicationInfo().dataDir + "/user");
//                    FileUtils.cleanDirectory(dir);
//                    Log.v("UserAct","Directory deleted");
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void initializeVar(){
        fullName = findViewById(R.id.editText_FullName);
        phoneNumber = findViewById(R.id.editText_Phone);
        email = findViewById(R.id.editText_Email);
        apply_btn = findViewById(R.id.done_btn);
        uploadImage_btn = findViewById(R.id.uploadImagebtn);
        uploadedImage_view = findViewById(R.id.uploadedImage);
        return_btn = findViewById(R.id.returnButton);
        logout_btn = findViewById(R.id.logoutButton);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        db = FirebaseFirestore.getInstance();
//        storageRef = storage.getReference();

        try{
            File image = new File(getApplicationInfo().dataDir + "/user/pfp/userpfp.jpg");
            if(image.exists()){
                uploadedImage_view.setImageURI(Uri.fromFile(image));
            }
        }
        catch (Exception e){
            Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}