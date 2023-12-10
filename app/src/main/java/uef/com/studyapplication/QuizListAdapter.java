package uef.com.studyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder> {
    private List<String> quizKeys;

    public QuizListAdapter(List<String> quizKeys) {
        this.quizKeys = quizKeys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String quizKey = quizKeys.get(position);
        holder.quizNameTextView.setText(quizKey);
    }

    @Override
    public int getItemCount() {
        return quizKeys.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView quizNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quizNameTextView = itemView.findViewById(R.id.quiz_title);
        }
    }
}
