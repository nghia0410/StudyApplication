package uef.com.studyapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import uef.com.studyapplication.R;
import uef.com.studyapplication.dto.Question;

public class QuestionAdapter extends BaseAdapter {

    private Context context;
    private List<Question> questionList;
    private QuestionHandler questionHandler;

    public interface QuestionHandler {
        public void selectedAnswer(int questionPos, List<Integer> answers);
    }

    public QuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    public QuestionAdapter(Context context, List<Question> questionList, QuestionHandler handler) {
        this.context = context;
        this.questionList = questionList;
        this.questionHandler = handler;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Question getItem(int position) {
        return questionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_question, null);
        }

        // Lấy ra các View từ layout item_question.xml
        TextView questionText = view.findViewById(R.id.questionText);
        RadioGroup answerOptions = view.findViewById(R.id.answerOptions);

        // Lấy ra câu hỏi tại vị trí position
        Question question = questionList.get(position);

        // Thiết lập nội dung câu hỏi
        questionText.setText(String.format("Câu hỏi: %s", question.getQuestion()));

        // Thiết lập đáp án dựa vào loại câu hỏi
        answerOptions.removeAllViews(); // Xóa các view cũ trước khi thêm mới

        Log.d("Ping","Refreshed");

        for (int i = 0; i < question.getOptions().size(); i++) {
            String option = question.getOptions().get(i);

            if (question.getType().equals("single_choice")) {
                // Sử dụng RadioButton cho single_choice
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(option);
                answerOptions.addView(radioButton);

                try {
                    if (question.getAnswer() != null) {
                        for (int a = 0; a < question.getAnswer().size(); a++) {
                            Integer answer = question.getAnswer().get(a);
                            RadioButton button = (RadioButton) answerOptions.getChildAt(answer);
                            button.setChecked(true);
                            // Access both the answer and its index (i) here
                        }
                    }
                }
                catch (Exception e){
                    Log.w("QuestionAdapter",e.getMessage());
                }
                final int index = i;
                radioButton.setOnClickListener((view1) -> {
                    if(question.getAnswer()!= null)
                        question.getAnswer().clear();

                    if (radioButton.isChecked()) {
                        question.getAnswer().add(index);
                    }
                    else {
                        question.getAnswer().remove(Integer.valueOf(index));
                    }
                    Log.d("question",question.toString());

                    questionHandler.selectedAnswer(position, question.getAnswer());
                });
            } else if (question.getType().equals("multi_choice")) {
                // Sử dụng CheckBox cho multi_choice
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(option);
                answerOptions.addView(checkBox);

                final int index = i;
                try {
                    if (question.getAnswer().get(index) != null)
                        checkBox.setChecked(true);
                } catch (Exception e){
                    Log.w("QuestionAdapter",e.getMessage());
                }

                checkBox.setOnClickListener((view2) -> {
                    if (checkBox.isChecked()) {
                        question.getAnswer().add(index);
                    } else {
                        question.getAnswer().remove(Integer.valueOf(index));
                    }

                    questionHandler.selectedAnswer(position, question.getAnswer());
                });
            }
        }

        return view;
    }
}
