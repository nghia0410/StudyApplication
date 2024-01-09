package uef.com.studyapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import uef.com.studyapplication.R;
import uef.com.studyapplication.dto.Question;

//public class QuizAdapter extends BaseAdapter {
//    private List<Question> questions;
//    public QuizAdapter(List<Question> questions) {
//        this.questions = questions;
//    }
//    @Override
//    public int getCount() {
//        return questions.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return questions.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Tạo view mới nếu chưa tồn tại
//        if (convertView == null) {
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_quiz_user, parent, false);
//        }
//
//        // Lấy các view từ layout
//        TextView questionTextView = convertView.findViewById(R.id.edit_question);
//        RadioButton option1RadioButton = convertView.findViewById(R.id.edit_option1);
//        RadioButton option2RadioButton = convertView.findViewById(R.id.edit_option2);
//        RadioButton option3RadioButton = convertView.findViewById(R.id.edit_option3);
//        RadioButton option4RadioButton = convertView.findViewById(R.id.edit_option4);
//
//        // Lấy câu hỏi hiện tại
//        Question question = (Question) getItem(position);
//
//        // Thiết lập nội dung cho các view
//        questionTextView.setText(question.getQuestion());
//        option1RadioButton.setText(question.getOptions().get(0));
//        option2RadioButton.setText(question.getOptions().get(1));
//        option3RadioButton.setText(question.getOptions().get(2));
//        option4RadioButton.setText(question.getOptions().get(3));
//
//        // Bật hoặc tắt RadioButton tùy theo lựa chọn đúng
//        if (question.getAnswer() == 0) {
//            option1RadioButton.setChecked(true);
//        } else if (question.getAnswer() == 1) {
//            option2RadioButton.setChecked(true);
//        } else if (question.getAnswer() == 2) {
//            option3RadioButton.setChecked(true);
//        } else {
//            option4RadioButton.setChecked(true);
//        }
//
//        return convertView;
//    }
//}
public class QuizAdapter extends BaseAdapter {
    private List<Question> questions;

    public QuizAdapter(List<Question> questions) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tạo view mới nếu chưa tồn tại
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_quiz_user, parent, false);
        }

        // Lấy các view từ layout
        TextView questionTextView = convertView.findViewById(R.id.edit_question);
        RadioButton option1RadioButton = convertView.findViewById(R.id.edit_option1);
        RadioButton option2RadioButton = convertView.findViewById(R.id.edit_option2);
        RadioButton option3RadioButton = convertView.findViewById(R.id.edit_option3);
        RadioButton option4RadioButton = convertView.findViewById(R.id.edit_option4);
        RadioGroup radioGroup = convertView.findViewById(R.id.list_quiz_answer);
        // Lấy câu hỏi hiện tại
        Question question = (Question) getItem(position);

        // Thiết lập nội dung cho các view
        questionTextView.setText(question.getQuestion());
        option1RadioButton.setText(question.getOptions().get(0));
        option2RadioButton.setText(question.getOptions().get(1));
        option3RadioButton.setText(question.getOptions().get(2));
        option4RadioButton.setText(question.getOptions().get(3));

        // Bật hoặc tắt RadioButton tùy theo lựa chọn đúng
        for (int pos : question.getAnswer()) {
            Toast.makeText(convertView.getContext(), String.valueOf(pos),Toast.LENGTH_LONG).show();
            switch (pos) {
                case 0:
                    option1RadioButton.setChecked(true);
                    option1RadioButton.setEnabled(false);
                    break;
                case 1:
                    option2RadioButton.setChecked(true);
                    option2RadioButton.setEnabled(false);
                    break;
                case 2:
                    option3RadioButton.setChecked(true);
                    option3RadioButton.setEnabled(false);
                    break;
                case 3:
                    option4RadioButton.setChecked(true);
                    option4RadioButton.setEnabled(false);
                    break;
            }
        }


        return convertView;
    }
}
