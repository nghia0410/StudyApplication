package uef.com.studyapplication;

import static uef.com.studyapplication.CreateActivity.link_edt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewAssignment extends AppCompatActivity {
    private ImageButton playytb_btn;
    private String editLink ;
    private WebView webView;
    private Button back_btn,done_btn;

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


    public void YoutubeButton(View view){
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
                "          videoId: '" + id +"',\n" +
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
 }
