//package uef.com.studyapplication;
//
//import static uef.com.studyapplication.acitivity.LoginActivity.mList;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import org.joda.time.DateTimeComparator;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//
//import uef.com.studyapplication.adapter.AdapterNotification;
//import uef.com.studyapplication.dto.Assignment;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link NotificationFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class NotificationFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private ListView lv;
//    View parentholder;
//    Date[] dates = {new Date(), new Date()};
//    //    LocalTime[] times = { LocalTime.now() , LocalTime.now()};
//    String[] descriptions = {"Đã tạo bài tập IT_03 ", "Đã nộp bài tập IT_03"};
//    Integer[] bgColor = {0, 1, 2, 0};
//    List<RowItem> rowItems;
//    List<AssignmentList> rowSubmitItems, rowAssignedItems;
//
//
//    public NotificationFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment NotificationFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static NotificationFragment newInstance(String param1, String param2) {
//        NotificationFragment fragment = new NotificationFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        parentholder = inflater.inflate(R.layout.fragment_notification, container, false);
//
//        lv = (ListView) parentholder.findViewById(R.id.ListViewNotification);
//        rowItems = new ArrayList<RowItem>();
//        try {
//            rowAssignedItems = mList;
//            for (Object obj : rowAssignedItems) {
//                AssignmentList assignments = (AssignmentList) obj;
////                if(!assignments.getAssignment().getCourse().isEmpty()) {
//                    Assignment assignment = assignments.getAssignment();
//                    if (((AssignmentList) obj).getAssignment().getSubmitTime() != null) {
//                        RowItem item = new RowItem(assignment.getCourse(), 1, assignment.getSubmitTime());
//                        rowItems.add(item);
//                    }
//                if(((AssignmentList) obj).getAssignment().getLevel() == null) {
//                    RowItem item = new RowItem(assignment.getCourse(), 2, assignment.getCreateTime());
//                    rowItems.add(item);
//                }else {
//                    RowItem item = new RowItem(assignment.getCourse(), 0, assignment.getCreateTime());
//                    rowItems.add(item);
//                }
//            }
//            rowItems.sort(new Comparator<RowItem>() {
//                @Override
//                public int compare(RowItem t0, RowItem t1) {
//                    DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();
//                    Date date1 = null;
//                    Date date0 = null;
//                    try {
//                        date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t0.getDate() + " " + t1.getDate());
//                        date0 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t1.getDate() + " " + t0.getDate());
//                        Log.v("Sort history ", "Debugging." +
//                                "\nt0:" + t0.getCourse() +
//                                "\nt1:" + t1.getCourse());
//                    } catch (ParseException e) {
//                        Log.e("Sort history ", "Something went wrong with sorting." +
//                                "\nt0:" + t0.getDate() +
//                                "\nt1:" + t1.getDate());
//                        return 0;
//                    }
//                    int retVal = dateTimeComparator.compare(date1, date0);
////                            Log.v("retVal",String.valueOf(retVal));
//                    if (retVal == 0)
//                        //both dates are equal
//                        return 0;
//                    else if (retVal < 0)
//                        //myDateOne is before myDateTwo
//                        return 1;
//                    else if (retVal > 0)
//                        //myDateOne is after myDateTwo
//                        return -1;
//                    return 0;
//                }
//            });
//        } catch (Exception e) {
//            Log.v("Assignment", "Empty");
//        }
//        ArrayAdapter<RowItem> mAdapter =
//                new AdapterNotification(getContext(), R.id.ListViewNotification, rowItems);
//        lv.setAdapter(mAdapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
////                TextView tv_selected = (TextView) view;
////                Toast.makeText(parentholder.getContext(), tv_selected.getText(),
////                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        // Inflate the layout for this fragment
//        return parentholder;
//    }
//}
package uef.com.studyapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uef.com.studyapplication.adapter.AdapterNotification;
import uef.com.studyapplication.dto.NewAssignment;
import uef.com.studyapplication.service.AssignmentService;


public class NotificationFragment extends Fragment {


    private ListView lv;
    View parentholder;
    Date[] dates = {new Date(), new Date()};
    //    LocalTime[] times = { LocalTime.now() , LocalTime.now()};
    String[] descriptions = {"Đã tạo bài tập IT_03 ", "Đã nộp bài tập IT_03"};
    Integer[] bgColor = {0, 1, 2, 0};
    List<RowItem> rowItems;
    List<NewAssignment> rowSubmitItems, rowAssignedItems;
    AssignmentService service = AssignmentService.getInstance();


    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentholder = inflater.inflate(R.layout.fragment_notification, container, false);

        lv = (ListView) parentholder.findViewById(R.id.ListViewNotification);
        rowItems = new ArrayList<RowItem>();
        try {
            rowAssignedItems =  new ArrayList<>(service.assignments);

            for (NewAssignment obj : rowAssignedItems) {
//                if(!assignments.getAssignment().getCourse().isEmpty()) {

                RowItem item;
                if (obj.getLevel() == null) {
                    item = new RowItem(obj.getCourse(), 2, obj.getCreateTime());
                }else {
                    item = new RowItem(obj.getCourse(), 0, obj.getCreateTime());
                }
                rowItems.add(item);
            }
//            rowItems.sort(new Comparator<RowItem>() {
//                @Override
//                public int compare(RowItem t0, RowItem t1) {
//                    DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();
//                    Date date1 = null;
//                    Date date0 = null;
//                    try {
//                        date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t0.getDate() + " " + t1.getDate());
//                        date0 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(t1.getDate() + " " + t0.getDate());
//                        Log.v("Sort history ", "Debugging." +
//                                "\nt0:" + t0.getCourse() +
//                                "\nt1:" + t1.getCourse());
//                    } catch (ParseException e) {
//                        Log.e("Sort history ", "Something went wrong with sorting." +
//                                "\nt0:" + t0.getDate() +
//                                "\nt1:" + t1.getDate());
//                        return 0;
//                    }
//                    int retVal = dateTimeComparator.compare(date1, date0);
////                            Log.v("retVal",String.valueOf(retVal));
//                    if (retVal == 0)
//                        //both dates are equal
//                        return 0;
//                    else if (retVal < 0)
//                        //myDateOne is before myDateTwo
//                        return 1;
//                    else if (retVal > 0)
//                        //myDateOne is after myDateTwo
//                        return -1;
//                    return 0;
//                }
//            });
        } catch (Exception e) {
            Log.v("Assignment", "Empty");
        }
        ArrayAdapter<RowItem> mAdapter =
                new AdapterNotification(getContext(), R.id.ListViewNotification, rowItems);

        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                TextView tv_selected = (TextView) view;
//                Toast.makeText(parentholder.getContext(), tv_selected.getText(),
//                        Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return parentholder;
    }
}