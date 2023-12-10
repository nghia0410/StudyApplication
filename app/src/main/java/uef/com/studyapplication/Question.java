package uef.com.studyapplication;

import java.util.List;

public class Question {
    private String questionTitle;
    private String questionText;
    private List<String> options;
    private List<Integer> correctAnswerIndices;
    private String question;
    private int correctAnswerIndex;
    private int selectedOptionIndex;

    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(Question.class)
    }
    public Question(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.selectedOptionIndex = -1; // Initialize selected option index to -1 (indicating no option selected)
    }


    public Question(String questionTitle, String questionText, List<String> options, List<Integer> correctAnswerIndices) {
        this.questionTitle = questionTitle;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndices = correctAnswerIndices;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getCorrectAnswerIndices() {
        return correctAnswerIndices;
    }

    public void setCorrectAnswerIndices(List<Integer> correctAnswerIndices) {
        this.correctAnswerIndices = correctAnswerIndices;
    }
    public String getQuestion() {
        return question;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }

}
