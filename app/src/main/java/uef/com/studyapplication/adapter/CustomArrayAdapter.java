package uef.com.studyapplication.adapter;

import static uef.com.studyapplication.acitivity.LoginActivity.user;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import uef.com.studyapplication.R;
import uef.com.studyapplication.dto.NewAssignment;

public class CustomArrayAdapter extends ArrayAdapter<NewAssignment>  {
    private final Context context;

    private class ViewHolder {
        TextView txtCourse,txtYoutube,txtLevel,txtDate;
        ImageButton btnDelete,btnEdit;
        RelativeLayout bgColor;
    }
    public CustomArrayAdapter(Context context, int resourceId, List<NewAssignment> items) {
        super(context, resourceId, items);
        this.context = context;
    }
    public CustomArrayAdapter(Context context, int resourceId ) {
        super(context, resourceId);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        NewAssignment rowItem = getItem(position);
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
            holder.btnDelete = (ImageButton) convertView.findViewById(R.id.delete_btn);
            holder.btnEdit = (ImageButton) convertView.findViewById(R.id.edit_btn);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.btnDelete.setOnClickListener(view -> {

        });
        holder.txtCourse.setText(rowItem.getCourse());
        holder.txtLevel.setText(rowItem.getLevel().toString());
        try {
            switch (rowItem.getLevel()) {
                case easy:
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor));
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    break;
                case medium:
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor1));
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    break;
                case hard:
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor2));
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    break;
//            default:
//                holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor));
            }
            if(user.getType().equals("admin")) {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.txtDate.setText("Deadline: " + rowItem.getDate());
//                holder.txtYoutube.setText(""+rowItem.getYoutube());
            }
            else {
//                holder.txtDate.setText("Submitted at: "+((NewAssignment.UserAssignment) rowItem).getSubmitTime());
//                holder.txtYoutube.setText("Answer: "+rowItem.getYoutube());
                holder.btnDelete.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Log.e("EROOR", e.getMessage());
            for (StackTraceElement element : e.getStackTrace()){
                Log.e("EROOR", element.toString());

            }
            Log.v("mAdapter","No type");
        }
        return convertView;
    }
}