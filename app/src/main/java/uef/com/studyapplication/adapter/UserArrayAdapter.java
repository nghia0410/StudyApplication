package uef.com.studyapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import uef.com.studyapplication.acitivity.CertificateActivity;
import uef.com.studyapplication.databinding.ItemUserBinding;
import uef.com.studyapplication.dto.User;

public class UserArrayAdapter extends BaseAdapter {

    Context context;
    List<User> users;
    String assignmentId;
    private FirebaseFirestore _db = FirebaseFirestore.getInstance();

    public UserArrayAdapter(Context context, List<User> users, String assignmentId) {
        this.context = context;
        this.users = users;
        this.assignmentId = assignmentId;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemUserBinding binding;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            binding = ItemUserBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemUserBinding) convertView.getTag();
        }


        binding.txtName.setText(getItem(position).getNameOrEmail());

        _db.collection("users")
                .document(getItem(position).getUuid())
                .collection("assignment")
                .document(assignmentId)
                .get().addOnSuccessListener(runnable -> {
                    binding.txtPoints.setText(String.format("Điểm: %s", runnable.get("points",Integer.class)));
                    binding.txtSubmitAt.setText(runnable.getString("submitTime"));
                }).addOnFailureListener(runnable -> {
                    binding.txtPoints.setText(String.format("Điểm: %s", 0));
                });
        binding.btnShowCert.setOnClickListener(view -> {
            Intent intent = new Intent(context, CertificateActivity.class);
            intent.putExtra("user_name",getItem(position).getNameOrEmail());
            context.startActivity(intent);
        });
        return convertView;
    }
}

