package uef.com.studyapplication;

import static uef.com.studyapplication.LoginActivity.user;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<RowItem>  {
    private final Context context;

    private class ViewHolder {
        TextView txtCourse,txtYoutube,txtLevel;
        ImageButton btnDelete,btnEdit;
        RelativeLayout bgColor;
    }
    public CustomArrayAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }


    //    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.list_assignment, parent, false);
//        TextView firstline = (TextView) rowView.findViewById(R.id.firstLine);
//        TextView secondline = (TextView) rowView.findViewById(R.id.secondLine);
//        firstline.setText(titles[position]);
//        secondline.setText(details[position]);
//
//        return rowView;
//    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);
//        if(rowItem.getCourse() == null)
//            return convertView;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_assignment, null);
            holder = new ViewHolder();
            holder.bgColor = (RelativeLayout) convertView.findViewById(R.id.assignmentlayout);
            holder.txtYoutube = (TextView) convertView.findViewById(R.id.secondline);
            holder.txtCourse = (TextView) convertView.findViewById(R.id.firstline);
//            holder.txtDate = (TextView) convertView.findViewById(R.id.thirdline);
            holder.txtLevel = (TextView) convertView.findViewById(R.id.fourthline);
            holder.btnDelete = (ImageButton) convertView.findViewById(R.id.delete_btn);
            holder.btnEdit = (ImageButton) convertView.findViewById(R.id.edit_btn);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.btnDelete.setOnClickListener(view -> {

        });
        holder.txtCourse.setText(rowItem.getCourse());
        holder.txtLevel.setText(rowItem.getLevel());
        try {
            switch (rowItem.getLevel()) {
                case "Dễ":
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor));
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    break;
                case "Trung Bình":
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor));
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    break;
                case "Khó":
                    holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor));
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    break;
//            default:
//                holder.bgColor.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.assignmentblockbgcolor));
            }
            if(user.getUsername().equals("admin")||user.getUsername().equals("admin1")) {
//                holder.txtDate.setText("Deadline: " + rowItem.getDate());
                holder.txtYoutube.setText(""+rowItem.getYoutube());
            }else {
                holder.txtYoutube.setText("Answer: "+rowItem.getYoutube());
                holder.btnDelete.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            Log.v("mAdapter","No type");
        }
        return convertView;
    }
}