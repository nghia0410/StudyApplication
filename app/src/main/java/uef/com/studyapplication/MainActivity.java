package uef.com.studyapplication;

import static uef.com.studyapplication.LoginActivity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView userPfp;
    private  TextView userName;
    private CardView userDetails;
    private FloatingActionButton create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost = findViewById(R.id.tabhost);
//
        tabHost.setup();
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Bài Tập");
        spec1.setIndicator("", getResources().getDrawable(R.drawable.book_icon_tabhost));
        spec1.setContent(R.id.assignmenttab);
//
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Hoạt Động");
        spec2.setIndicator("", getResources().getDrawable(R.drawable.history_icon_tabhost));
        spec2.setContent(R.id.activitytab);
//
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        final TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getTabCount(); i++) {
            final View tab = tabWidget.getChildTabViewAt(i);
            final TextView title = (TextView) tab.findViewById(android.R.id.title);
            title.setSingleLine();
        }
        viewPager  = findViewById(R.id.submitted_pager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        tabLayout = findViewById(R.id.assignmenttab_layout);
        tabLayout.setupWithViewPager(viewPager);
//
//        userPfp = findViewById(R.id.userpfp);
//        userName = findViewById(R.id.textView_username);
//        userName.setText(user.getUsername());

        // Begin the transaction
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
//        ft.replace(R.id.history_fragment, new HistoryFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
//        ft.commit();

//        try{
//            File image = new File(getApplicationInfo().dataDir + "/user/pfp/userpfp.jpg");
//            if(image.exists()){
//                userPfp.setImageURI(Uri.fromFile(image));
//            }
//        }
//        catch (Exception e){
//            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

        // onclick listener for user details
//        userDetails = (CardView) findViewById(R.id.userDetail);
//        userDetails.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, UserActivity.class);
//            startActivity(intent);
//        });
//
//        create= findViewById(R.id.floatingActionButton);
//        create.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
//            startActivity(intent);
//        });

//        imgButtonSort = findViewById(R.id.imageButtonSort);
//        imgButtonSort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dropDownMenu.show();
//            }
//        });


    }
}