package uef.com.studyapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

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

        rbAnswer1.setText(question.getOptions()[0]);
        rbAnswer2.setText(question.getOptions()[1]);
        rbAnswer3.setText(question.getOptions()[2]);
        rbAnswer4.setText(question.getOptions()[3]);

        switch (question.getAnswer()){
            case 0:
                rbAnswer1.setChecked(true);
                break;
            case 1:
                rbAnswer2.setChecked(true);
                break;
            case 2:
                rbAnswer3.setChecked(true);
                break;
            case 3:
                rbAnswer4.setChecked(true);
                break;
        }
        return view;
    }
}
