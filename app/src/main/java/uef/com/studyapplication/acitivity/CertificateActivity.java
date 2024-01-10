package uef.com.studyapplication.acitivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uef.com.studyapplication.databinding.ActivityCertificateBinding;

public class CertificateActivity extends AppCompatActivity {
    ActivityCertificateBinding binding;
    String userName;
    String course_name;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertificateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();

        if (getIntent().hasExtra("user_name")) {
            userName = getIntent().getStringExtra("user_name");
            course_name = extras.getString("CERTIFICATE_NAME");
        } else {
            userName = "";
            course_name = "";
        }

        String issuedDate = simpleDateFormat.format(new Date());
        String expiryDate = simpleDateFormat.format( Date.parse("1/1/2040"));
        binding.txtIssueDate.setText(String.format("Issued Date: %s", issuedDate));
        binding.txtExpiryDate.setText(String.format("Expiry Date: %s", expiryDate));
        binding.txtUserName.setText(userName);
        binding.txtCertificateName.setText(course_name);

        binding.btnSave.setOnClickListener(view -> {
            convertXmlToBitmap();
        });
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void convertXmlToBitmap() {
        // Lấy chiều rộng và chiều cao của layout
        int width = binding.certificate.getWidth();
        int height = binding.certificate.getHeight();

        // Tạo một bitmap mới với kích thước của layout
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Tạo một canvas để vẽ layout lên bitmap
        Canvas canvas = new Canvas(bitmap);

        // Vẽ layout lên canvas
        binding.certificate.draw(canvas);

        // Bạn có thể lưu hoặc sử dụng bitmap theo nhu cầu
        // Ví dụ: Lưu bitmap vào bộ nhớ
        String imagePath = saveBitmap(bitmap, userName);
        // Bạn có thể sử dụng đường dẫn imagePath để làm gì đó khác
        Log.d("Certificate Path", imagePath);
    }

    // Phương thức để lưu bitmap vào bộ nhớ
    public String saveBitmap(Bitmap bitmap, String fileName) {
        String filePath = null;

        try {
            // Lấy thư mục lưu trữ hình ảnh của ứng dụng
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "SelfStudy");

            // Tạo thư mục nếu nó chưa tồn tại
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }

            // Tạo tệp tin để lưu bitmap
            File imageFile = new File(storageDir, fileName + ".png");

            // Tạo đường dẫn tuyệt đối của tệp tin
            filePath = imageFile.getAbsolutePath();

            // Mở FileOutputStream để ghi dữ liệu vào tệp tin
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                // Lưu bitmap vào tệp tin với định dạng PNG
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
            Toast.makeText(this, "Save image successful", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

}