package uef.com.studyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuestionViewHolder> {
    private List<Question> questionList;

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questionList != null ? questionList.size() : 0;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTitleTextView;
        private TextView questionTextView;
        private TextView optionATextView;
        private TextView optionBTextView;
        private TextView optionCTextView;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitleTextView = itemView.findViewById(R.id.edit_Title);
            questionTextView = itemView.findViewById(R.id.edit_question);
            optionATextView = itemView.findViewById(R.id.edit_option1);
            optionBTextView = itemView.findViewById(R.id.edit_option2);
            optionCTextView = itemView.findViewById(R.id.edit_option3);
        }

        public void bind(Question question) {
            questionTitleTextView.setText(question.getQuestionTitle());
            questionTextView.setText(question.getQuestionText());
            List<String> options = question.getOptions();
            optionATextView.setText(options.get(0));
            optionBTextView.setText(options.get(1));
            optionCTextView.setText(options.get(2));
        }
    }
}
