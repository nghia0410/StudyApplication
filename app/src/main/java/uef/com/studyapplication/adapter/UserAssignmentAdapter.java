package uef.com.studyapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import uef.com.studyapplication.R;
import uef.com.studyapplication.dto.UserAssignment;

public class UserAssignmentAdapter extends ArrayAdapter<UserAssignment> {
    private final Context context;

    public UserAssignmentAdapter(@NonNull Context context, int resource, List<UserAssignment> items) {
        super(context,resource,items);
        this.context = context;
    }

    public UserAssignmentAdapter(@NonNull Context context, int resource){
        super(context,resource);
        this.context = context;
    }

    private class ViewHolder {
        TextView txtCourse, txtYoutube, txtLevel, txtDate;
        ImageView btn_delete, btn_edit;
        RelativeLayout bgColor;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        UserAssignment rowItem = getItem(position);
//        if(rowItem.getCourse() == null)
//            return convertView;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_assignment, null);
            holder = new ViewHolder();
            holder.bgColor = (RelativeLayout) convertView.findViewById(R.id.assignmentlayout);

            holder.txtCourse = (TextView) convertView.findViewById(R.id.firstline);
            holder.txtDate = (TextView) convertView.findViewById(R.id.secondline);
            holder.txtLevel = (TextView) convertView.findViewById(R.id.fourthline);

            holder.btn_delete = (ImageView) convertView.findViewById(R.id.delete_btn);
            holder.btn_edit = (ImageView) convertView.findViewById(R.id.edit_btn);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.btn_edit.setVisibility(View.INVISIBLE);
        holder.btn_delete.setVisibility(View.INVISIBLE);

        holder.txtCourse.setText(rowItem.getCourse());
        holder.txtLevel.setText(rowItem.getLevel().toString());
        try {
            switch (rowItem.getLevel()) {
                case easy:
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor));
                    break;
                case medium:
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor1));
                    break;
                case hard:
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor2));
                    break;
            }

            holder.txtDate.setText("Submitted at: " + rowItem.getSubmitTime());

        } catch (Exception e) {
            Log.e("EROOR", e.getMessage());
            for (StackTraceElement element : e.getStackTrace()) {
                Log.e("EROOR", element.toString());

            }
            Log.v("mAdapter", "No type");
        }
        return convertView;
    }

}