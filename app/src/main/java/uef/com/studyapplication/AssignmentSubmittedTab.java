package uef.com.studyapplication;

import static uef.com.studyapplication.acitivity.LoginActivity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import uef.com.studyapplication.acitivity.CertificateActivity;
import uef.com.studyapplication.adapter.UserAssignmentAdapter;
import uef.com.studyapplication.databinding.FragmentAssignmentSubmittedTabBinding;
import uef.com.studyapplication.dto.NewAssignment;
import uef.com.studyapplication.service.AssignmentService;


public class AssignmentSubmittedTab extends Fragment {
    private AssignmentService service = AssignmentService.getInstance();
    FragmentAssignmentSubmittedTabBinding binding;

    String getCountText() {
        StringBuilder builder = new StringBuilder();
        builder.append(service.userAssignments.size());
        builder.append('/');
        builder.append(service.assignments.size());
        builder.append(" Done");
        return builder.toString();
    }

    public AssignmentSubmittedTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAssignmentSubmittedTabBinding.inflate(inflater, container, false);
//        binding.txtCountDone.setText(getCountText());
        service.allSubmittedAdapter = new UserAssignmentAdapter(getContext(), R.id.assignmenttab_layout, service.userAssignments);
        binding.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CertificateActivity.class);
                NewAssignment assignment = service.userAssignments.get(position);
                intent.putExtra("user_name",user.getEmail());
                intent.putExtra("CERTIFICATE_NAME",assignment.getCourse());

                startActivity(intent);
            }
        });
        binding.lv.setAdapter(service.allSubmittedAdapter);

        service.getUserAssignments(user.getUuid());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Giải phóng binding khi Fragment bị hủy
        binding = null;
    }
}