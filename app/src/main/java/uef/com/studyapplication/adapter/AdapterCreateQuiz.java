package uef.com.studyapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import uef.com.studyapplication.R;
import uef.com.studyapplication.dto.Question;

public class AdapterCreateQuiz extends BaseAdapter {

    private List<Question> questions;

    public AdapterCreateQuiz(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Consider implementing proper logic for unique IDs
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_quiz, parent, false);
        } else {
            view = convertView;
        }

        Question question = questions.get(position);

        TextView etQuestion = view.findViewById(R.id.edit_question);
        etQuestion.setText(question.getQuestion());

        RadioButton rbAnswer1 = view.findViewById(R.id.edit_option1);
        RadioButton rbAnswer2 = view.findViewById(R.id.edit_option2);
        RadioButton rbAnswer3 = view.findViewById(R.id.edit_option3);
        RadioButton rbAnswer4 = view.findViewById(R.id.edit_option4);

        rbAnswer1.setText(question.getOptions().get(0));
        rbAnswer2.setText(question.getOptions().get(1));
        rbAnswer3.setText(question.getOptions().get(2));
        rbAnswer4.setText(question.getOptions().get(3));

        return view;
    }
}
