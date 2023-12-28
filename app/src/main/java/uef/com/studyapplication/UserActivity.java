package uef.com.studyapplication;
import static uef.com.studyapplication.LoginActivity.user;
import static uef.com.studyapplication.LoginActivity.userDocument;
import static uef.com.studyapplication.Profile.setBooleanDefaults;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.FileUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UserActivity extends AppCompatActivity {
    private EditText fullName, phoneNumber, email;
    int SELECT_PICTURE = 200;
    Button apply_btn;
    ImageButton uploadImage_btn,return_btn,logout_btn;
    ImageView uploadedImage_view;
    FirebaseFirestore db;
    static FirebaseStorage storage = FirebaseStorage.getInstance();;

    // Create a storage reference from our app
    StorageReference storageRef;
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
                setBooleanDefaults(getString(R.string.userlogged),false,UserActivity.this);
                Log.v("Login state","false");
                // Wipe files of current user
                try {
                    File dir = new File(getApplicationInfo().dataDir + "/user");
                    FileUtils.cleanDirectory(dir);
                    Log.v("UserAct","Directory deleted");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        uploadImage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fullName.getText().toString();
                String phone = phoneNumber.getText().toString();
                String mail = email.getText().toString();
                String id = userDocument.getId();

                if (!name.equals("")) user.setFullname(name);
                if (!phone.equals("")) user.setPhone(phone);
                if (!mail.equals("")) user.setEmail(mail);

                if(selectedImageUri != null){
                    StorageReference riversRef = storageRef.child(id+"/userpfp.jpg");
                    UploadTask uploadTask = riversRef.putFile(selectedImageUri);
                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(UserActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            user.setImage("Yes");
                            db.collection("users").document(id).set(user);
                            // make a copy of image and move it to app's specific folder
                            try {
                                imageMover();
                            } catch (Exception e) {
                                StorageReference pfpRef = storageRef.child(userDocument.getId()+"/userpfp.jpg");
                                File localFile = new File(getApplicationInfo().dataDir + "/user/pfp/userpfp.jpg");
                                pfpRef.getFile(localFile).addOnSuccessListener(taskSnapshot1 -> {
                                    // Local temp file has been created
                                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    Log.v("DownloadPfp","pfp downloaded");
                                }).addOnFailureListener(exception -> {
                                    // Handle any errors
                                    Log.v("DownloadPfp","failed");
                                });
                                Log.v("ImageMover","Something went wrong, Resolve to download from cloud");
                            }
                            Toast.makeText(UserActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //Send the newly acquired data to the cloud
                else {
                    db.collection("users").document(id).set(user);
//                    finish();
//                    startActivity(getIntent());
                }
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void imageMover() throws Exception {
        String uriPath = getRealPathFromURI(selectedImageUri);

        File image =
                new File(uriPath);
        File targetlocation =
                new File(getApplicationInfo().dataDir + "/user/pfp/userpfp.jpg");

        Log.v("UserActivity", "sourceLocation: " + image);
        Log.v("UserActivity", "targetLocation: " + targetlocation);

        // make sure the target file exists
        if(image.exists()){

            InputStream in = new FileInputStream(image);
            OutputStream out = new FileOutputStream(targetlocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

            Log.v("UserActivity", "Copy file successful.");

        }else{
            Log.v("UserActivity", "Copy file failed. Source file missing.");
        }
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
        storageRef = storage.getReference();
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
    // this function is triggered when
    // the Select Image Button is clicked
    public void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    uploadedImage_view.setImageURI(selectedImageUri);
                }
            }
        }
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(filePathColumn[0]);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}