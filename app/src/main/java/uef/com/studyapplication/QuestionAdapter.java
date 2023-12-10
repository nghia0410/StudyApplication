package uef.com.studyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questions;

    // Constructor to initialize the list of questions
    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    // Create ViewHolder for the item_question.xml layout
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView editTitle;
        public TextView editQuestion;
        public TextView editOption1;
        public TextView editOption2;
        public TextView editOption3;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            editTitle = itemView.findViewById(R.id.edit_Title);
            editQuestion = itemView.findViewById(R.id.edit_question);
            editOption1 = itemView.findViewById(R.id.edit_option1);
            editOption2 = itemView.findViewById(R.id.edit_option2);
            editOption3 = itemView.findViewById(R.id.edit_option3);
        }
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        if (position < questions.size()) {
            Question question = questions.get(position);
            holder.editQuestion.setText(question.getQuestionText());
            List<String> options = question.getOptions();
            if (options.size() > 0) {
                holder.editOption1.setText(options.get(0));
            }
            if (options.size() > 1) {
                holder.editOption2.setText(options.get(1));
            }
            if (options.size() > 2) {
                holder.editOption3.setText(options.get(2));
            }
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
