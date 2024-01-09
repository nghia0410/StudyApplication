package uef.com.studyapplication;

import static uef.com.studyapplication.acitivity.LoginActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import uef.com.studyapplication.acitivity.AdminAssignmentActivity;
import uef.com.studyapplication.acitivity.EditActivity;
import uef.com.studyapplication.adapter.CustomArrayAdapter;
import uef.com.studyapplication.dto.NewAssignment;
import uef.com.studyapplication.dto.User;
import uef.com.studyapplication.service.AssignmentService;

public class AssignmentTab extends Fragment {

    private ListView lv;
    //    List<RowItem> rowItems;
    View parentholder;
    private AssignmentService service = AssignmentService.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public AssignmentTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void getAssignments() {
        service.getAssignments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentholder = inflater.inflate(R.layout.fragment_assignment_tab, container, false);
        lv = parentholder.findViewById(R.id.ListViewAssignment);
        if (!user.getType().equals("admin")) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position
                        , long l) {
//                    Log.v("listview", "item clicked");
                    Intent intent = new Intent(getActivity(), ViewAssignment.class);
                    NewAssignment assignment = service.assignments.get(position);
                    intent.putExtra("assignment", assignment);
                    startActivity(intent);
                }
            });
        } else {
            lv.setOnItemClickListener((adapterView, view, position, l) -> {
                NewAssignment assignment = service.assignments.get(position);

                db.collection("users")
                        .get()
                        .addOnSuccessListener(runnable -> {
                            ArrayList<User> users = new ArrayList<>();
                            for (DocumentSnapshot snap: runnable.getDocuments()){
                                User u = snap.toObject(User.class);
                                if(assignment.getSubmitter().contains(u.getUuid())){
                                    users.add(u);
                                }
                            }
                            Intent intent = new Intent(getActivity(), AdminAssignmentActivity.class);
                            intent.putExtra("assignment", assignment);
                            intent.putParcelableArrayListExtra("users", users);
                            startActivity(intent);
                        });

            });
        }
        service.allAssignmentAdapter =
                new CustomArrayAdapter(getContext(), R.id.assignmenttab_layout) {
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        View inflatedView = super.getView(position, convertView, parent);
                        if (user.getType().equals("admin")) {
                            ImageButton deletebtn = inflatedView.findViewById(R.id.delete_btn);
                            deletebtn.setFocusable(false);
                            deletebtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String id = service.assignments.get(position).getUuid();
                                    if (id != null)
                                        service.deleteAssignment(id);
                                    getAssignments();
                                }
                            });
                            //Update button
                            ImageButton editbtn = inflatedView.findViewById(R.id.edit_btn);
                            editbtn.setFocusable(false);
                            editbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), EditActivity.class);
                                    NewAssignment assignment = service.assignments.get(position);

                                    intent.putExtra("assignment", assignment);
                                    startActivity(intent);
                                }
                            });
                        }
                        return inflatedView;

                    }
                };
        lv.setAdapter(service.allAssignmentAdapter);


        getAssignments();
        // Inflate the layout for this fragment
        return parentholder;

    }
}