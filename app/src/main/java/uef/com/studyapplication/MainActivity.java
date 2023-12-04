package uef.com.studyapplication;

//import static uef.com.studyapplication.LoginActivity.mList;
import static uef.com.studyapplication.LoginActivity.user;
import static uef.com.studyapplication.LoginActivity.userDocument;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView userPfp;
    private TextView userName;
    private CardView userDetails;
    private FloatingActionButton create;
    private ImageButton imgButtonSort;



        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            imgButtonSort = findViewById(R.id.imageButtonSort);

            final PopupMenu dropDownMenu = new PopupMenu(MainActivity.this, imgButtonSort);
            final Menu menu = dropDownMenu.getMenu();
            dropDownMenu.getMenuInflater().inflate(R.menu.sortlist, menu);
            create = findViewById(R.id.floatingActionButton);
            dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.s11) {
//                        Collections.sort(mList.subList(0,mList.size()-1), new Comparator<AssignmentList>(){
//                            @Override
//                            public int compare(AssignmentList t0, AssignmentList t1) {
//                                if(t0.getAssignment().getCategory().equals("Homework"))
//                                    return -1;
//                                return 1;
//                            }
//                        });
                        recreate();
                        Log.v("SubMenuClick", "Submenu Item: sort by homework");
                        return true;
                    } else if (itemId == R.id.s12) {
//                        Collections.sort(mList.subList(0,mList.size()-1), new Comparator<AssignmentList>(){
//                            @Override
//                            public int compare(AssignmentList t0, AssignmentList t1) {
//                                int i;
//                                try {
//                                    i = t0.getAssignment().getCategory().compareTo(t1.getAssignment().getCategory());
//                                }
//                                catch (Exception e){
//                                    return 0;
//                                }
//                                return i;
//                            }
//                        });
                        recreate();
                        Log.v("SubMenuClick", "Submenu Item: sort by essay");
                        return true;
                    } else if (itemId == R.id.s13) {
//                        Collections.sort(mList.subList(0,mList.size()-1), new Comparator<AssignmentList>(){
//                            @Override
//                            public int compare(AssignmentList t0, AssignmentList t1) {
//                                int i;
//                                try{
//                                    i = t0.getAssignment().getCategory().compareTo(t1.getAssignment().getCategory());
//                                }
//                                catch (Exception e){
//                                    return 0;
//                                }
//                                if (i != 0) return -i;
//                                return i;
//                            }
//                        });
                        recreate();
                        Log.v("SubMenuClick", "Submenu Item: sort by other");
                        return true;
                    } else if (itemId == R.id.s21) {
//                        Collections.sort(mList.subList(0,mList.size()-1), new Comparator<AssignmentList>() {
//                            @Override
//                            public int compare(AssignmentList t0, AssignmentList t1) {
////                                DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();
//                                Date date1 = null;
//                                Date date0 = null;
//                                String temp = t0.getAssignment().getEndDate() + t0.getAssignment().getEndTime();
//                                try {
//                                    date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t0.getAssignment().getEndDate() +" "+ t0.getAssignment().getEndTime());
//                                    date0 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t1.getAssignment().getEndDate() +" "+ t1.getAssignment().getEndTime());
//                                } catch (ParseException e) {
//                                    Log.e("Sort time","Something went wrong sorting nearest");
//                                    return 0;
//                                }
////                                int retVal = dateTimeComparator.compare(date1, date0);
////                            Log.v("retVal",String.valueOf(retVal));
////                                if(retVal == 0)
////                                    //both dates are equal
////                                    return 0;
////                                else if(retVal < 0)
////                                    //myDateOne is before myDateTwo
////                                    return 1;
////                                else if(retVal > 0)
////                                    //myDateOne is after myDateTwo
////                                    return -1;
//                                return 0;
//                            }
//                        });
                        recreate();
                        Log.v("SubMenuClick", "Submenu Item: sort by closest DL");
                        return true;
                    } else if (itemId == R.id.s22) {
//                        Collections.sort(mList.subList(0,mList.size()-1), new Comparator<AssignmentList>() {
//                            @Override
//                            public int compare(AssignmentList t0, AssignmentList t1) {
////                                DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();
//                                Date date1 = null;
//                                Date date0 = null;
//                                try {
//                                    date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t0.getAssignment().getEndDate() +" "+ t0.getAssignment().getEndTime());
//                                    date0 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t1.getAssignment().getEndDate() +" "+ t1.getAssignment().getEndTime());
//                                } catch (ParseException e) {
//                                    Log.e("Sort time","Something went wrong sorting furthest");
//                                    return 0;
//                                }
////                                int retVal = dateTimeComparator.compare(date0, date1);
////                            Log.v("retVal",String.valueOf(retVal));
////                                if(retVal == 0)
////                                    //both dates are equal
////                                    return 0;
////                                else if(retVal < 0)
////                                    //myDateOne is before myDateTwo
////                                    return 1;
////                                else if(retVal > 0)
////                                    //myDateOne is after myDateTwo
////                                    return -1;
//                                return 0;
//                            }
//                        });
                        recreate();
                        Log.v("SubMenuClick", "Submenu Item: sort by furthest DL");
                        return true;
                    }
                    return false;
                }
            });


            TabHost tabHost = findViewById(R.id.tabhost);
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

            if (user.getUsername().equals("admin")) {
                viewPager = findViewById(R.id.submitted_pager);
                viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
                tabLayout = findViewById(R.id.assignmenttab_layout);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.removeTabAt(1);


                create.setOnClickListener(view -> {
                    Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                    startActivity(intent);
                });

            } else {
                viewPager = findViewById(R.id.submitted_pager);
                viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
                tabLayout = findViewById(R.id.assignmenttab_layout);
                tabLayout.setupWithViewPager(viewPager);
                create.hide();

            }
            userDetails = (CardView) findViewById(R.id.userDetail);
            userDetails.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            });
            create= findViewById(R.id.floatingActionButton);
            create.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            });

            imgButtonSort = findViewById(R.id.imageButtonSort);
            imgButtonSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dropDownMenu.show();
                }
            });


        }
    }
