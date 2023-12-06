package uef.com.studyapplication;

import static uef.com.studyapplication.CreateActivity.link_edt;
import static uef.com.studyapplication.LoginActivity.mList;
import static uef.com.studyapplication.LoginActivity.userDocument;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ViewAssignment extends AppCompatActivity {
    private ImageButton playytb_btn;
    private String editLink;
    private EditText customTagEditText;
    private EditText course;

    private Spinner level;
    private WebView webView;
    private Button back_btn, done_btn;
    private static final int PICK_FILES_REQUEST_CODE = 1;
    private FirebaseFirestore db;
    private List<Uri> selectedFiles = new ArrayList<>(); // Danh sách các tệp đã chọn
    private List<String> selectedFileNames = new ArrayList<>(); // Danh sách các tên tệp đã chọn
    private TextView attachmentTextView;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignment);

        // Lấy đối tượng TextView
        TextView textView = findViewById(R.id.testViewCourse);
        TextView textView2 = findViewById(R.id.tagSpinner);
        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        // Lấy giá trị của id
        String id = intent.getStringExtra("id");
        String tag = intent.getStringExtra("tag");

        // Hiển thị giá trị của id
        textView.setText(id);
        textView2.setText(tag);
        Bundle extras = getIntent().getExtras();

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        playytb_btn = findViewById(R.id.YoutubeButton);

        back_btn = findViewById(R.id.backButton);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAssignment.this, CreateActivity.class);
                startActivity(intent);
            }
        });
        done_btn = findViewById(R.id.doneButton);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ViewAssignment.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    public void YoutubeButton(View view) {
        editLink = link_edt.getText().toString();
        // Tách URL thành mảng các phần
        String[] parts = editLink.split("/");

        // Lấy phần thứ hai của mảng
        String id = parts[3];
        Log.v("YTID", id);

        String stringJavaScript = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <body>\n" +
                "    <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->\n" +
                "    <div id=\"player\"></div>\n" +
                "\n" +
                "    <script>\n" +
                "      // 2. This code loads the IFrame Player API code asynchronously.\n" +
                "      var tag = document.createElement('script');\n" +
                "\n" +
                "      tag.src = \"https://www.youtube.com/iframe_api\";\n" +
                "      var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
                "      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
                "\n" +
                "      // 3. This function creates an <iframe> (and YouTube player)\n" +
                "      //    after the API code downloads.\n" +
                "      var player;\n" +
                "      function onYouTubeIframeAPIReady() {\n" +
                "        player = new YT.Player('player', {\n" +
                "          height: '195',\n" +
                "          width: '320',\n" +
                "          videoId: '" + id + "',\n" +
                "          playerVars: {\n" +
                "            'playsinline': 1\n" +
                "          },\n" +
                "          events: {\n" +
                "            'onReady': onPlayerReady,\n" +
                "            'onStateChange': onPlayerStateChange\n" +
                "          }\n" +
                "        });\n" +
                "      }\n" +
                "\n" +
                "      // 4. The API will call this function when the video player is ready.\n" +
                "      function onPlayerReady(event) {\n" +
                "        event.target.playVideo();\n" +
                "      }\n" +
                "\n" +
                "      // 5. The API calls this function when the player's state changes.\n" +
                "      //    The function indicates that when playing a video (state=1),\n" +
                "      //    the player should play for six seconds and then stop.\n" +
                "      var done = false;\n" +
                "      function onPlayerStateChange(event) {\n" +
                "        if (event.data == YT.PlayerState.PLAYING && !done) {\n" +
                "          setTimeout(stopVideo, 10000);\n" +
                "          done = true;\n" +
                "        }\n" +
                "      }\n" +
                "      function stopVideo() {\n" +
                "        player.stopVideo();\n" +
                "      }\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>";
        webView.loadData(stringJavaScript, "text/html", "utf-8");

    }

    // Phương thức cập nhật dữ liệu vào Firestore
    private void updateDataInFirestore(String course, String level) {
        // Lấy ID của người dùng hiện tại từ Firebase Authentication
        String userId = userDocument.getId();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Tạo một Map chứa dữ liệu để cập nhật vào Firestore
//        Map<String, Object> updatedData = new HashMap<>();
//        updatedData.put("title", title);
//        updatedData.put("topic", topic);
//        updatedData.put("startDate", startDate);
//        updatedData.put("startTime", startTime);
//        updatedData.put("endDate", endDate);
//        updatedData.put("endTime", endTime);
//        updatedData.put("category", category);
        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();
        // Lấy assignmentId từ Intent
        AssignmentList assignment = (AssignmentList) mList.get(Integer.parseInt(value));
        String assignmentId = assignment.getId();
        Assignment updatedData = new Assignment();
        updatedData.setCourse(course);
        updatedData.setLevel(level);

        // Cập nhật dữ liệu vào Firestore trong bảng "assignments" của người dùng hiện tại
        db.collection("users").document(userId)
                .collection("assignment").document(assignmentId)
                .set(updatedData)
                .addOnSuccessListener(aVoid -> {
                    // Xử lý khi dữ liệu được cập nhật thành công
                    Toast.makeText(ViewAssignment.this, "Dữ liệu đã được cập nhật thành công.", Toast.LENGTH_SHORT).show();
                    // Điều hướng hoặc thực hiện các hành động cần thiết sau khi cập nhật dữ liệu thành công
                    UserList.UpdateL(db, ViewAssignment.this);
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi dữ liệu không thể được cập nhật vào Firestore
                    Toast.makeText(ViewAssignment.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILES_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri fileUri = clipData.getItemAt(i).getUri();
                    selectedFiles.add(fileUri); // Lưu trữ Uri của tệp đã chọn
                    selectedFileNames.add(getFileName(fileUri));
                }
                attachmentTextView.setText("Đã chọn " + selectedFiles.size() + " tệp");
                attachmentTextView.setVisibility(View.VISIBLE);
            } else if (data.getData() != null) {
                Uri fileUri = data.getData();
                selectedFiles.add(fileUri); // Lưu trữ Uri của tệp đã chọn
                String fileName = getFileName(fileUri);
                selectedFileNames.add(fileName);
                attachmentTextView.setText(fileName);
                attachmentTextView.setVisibility(View.VISIBLE);
            }
        }
        attachmentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectedFileList();
            }
        });
    }


    private void showSelectedFileList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Danh sách các tệp đã chọn");

        View selectedFilesView = getLayoutInflater().inflate(R.layout.selected_files_list, null);
        ListView selectedFilesListView = selectedFilesView.findViewById(R.id.selectedFilesListView);

        SelectedFilesAdapter adapter = new SelectedFilesAdapter(this, selectedFileNames, selectedFiles);
        selectedFilesListView.setAdapter(adapter);

        selectedFilesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi người dùng nhấp vào một tệp
                openSelectedFile(selectedFiles.get(position));
            }
        });

        builder.setView(selectedFilesView);
        builder.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng ấn nút "Xử lý"
                // Thêm mã xử lý ở đây
            }
        });

        builder.show();
    }


    private void openSelectedFile(Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ViewAssignment.this, "Không có ứng dụng nào có thể mở file này", Toast.LENGTH_SHORT).show();
        }
    }


    // Cập nhật TextView sau khi xóa tệp khỏi danh sách
    private void updateAttachmentTextView() {
        if (selectedFileNames.isEmpty()) {
            attachmentTextView.setText(""); // Nếu không có tệp nào, xóa nội dung TextView
            attachmentTextView.setVisibility(View.INVISIBLE);
        } else {
            attachmentTextView.setText("Đã chọn " + selectedFileNames.size() + " tệp");
        }
    }


    // Phương thức để lấy tên tệp từ Uri
    private String getFileName(Uri uri) {
        String result = null;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // Kiểm tra xem cột DISPLAY_NAME có tồn tại không
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (displayNameIndex >= 0) {
                    result = cursor.getString(displayNameIndex);
                } else {
                    // Nếu cột không tồn tại, xử lý tương ứng (ví dụ: lấy tên từ đường dẫn)
                    result = uri.getPath();
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }


    public class SelectedFilesAdapter extends BaseAdapter {
        private List<String> fileNames;
        private List<Uri> fileUris;
        private Context context;

        public SelectedFilesAdapter(Context context, List<String> fileNames, List<Uri> fileUris) {
            this.context = context;
            this.fileNames = fileNames;
            this.fileUris = fileUris;
        }

        @Override
        public int getCount() {
            return fileNames.size();
        }

        @Override
        public Object getItem(int position) {
            return fileNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_selected_file, parent, false);
            }

            TextView fileNameTextView = convertView.findViewById(R.id.fileNameTextView);
            ImageView deleteButton = convertView.findViewById(R.id.deleteButton);

            fileNameTextView.setText(fileNames.get(position));

            // Thêm sự kiện OnClickListener cho fileNameTextView
            fileNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Lấy Uri tương ứng với vị trí được nhấn trong danh sách
                    Uri fileUri = fileUris.get(position);
                    openSelectedFile(fileUris.get(position));
                    if (fileUri != null) {
                        // Xử lý khi người dùng nhấp vào tên tệp để mở tệp
                        try {
                            InputStream fileInputStream = context.getContentResolver().openInputStream(fileUri);
                            // Xử lý InputStream tại đây (ví dụ: đọc dữ liệu từ InputStream)
                            // Ví dụ: Đọc dữ liệu từ InputStream
                            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                stringBuilder.append(line).append("\n");
                            }
                            // stringBuilder.toString() chứa nội dung của tệp, bạn có thể xử lý nó theo nhu cầu của mình
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Xử lý lỗi khi không thể mở tệp
                            // Ví dụ: Hiển thị thông báo lỗi cho người dùng
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Xử lý khi người dùng ấn nút X để xóa tệp
                    fileNames.remove(position);
                    fileUris.remove(position); // Xóa Uri tương ứng
                    notifyDataSetChanged(); // Cập nhật danh sách
                    updateAttachmentTextView(); // Cập nhật TextView sau khi xóa tệp
                }
            });

            return convertView;
        }
    }
}
