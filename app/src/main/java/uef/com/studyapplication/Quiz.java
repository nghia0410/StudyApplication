package uef.com.studyapplication;

import java.util.List;

public class Quiz {
    private String title;
    private String description;
    private List<Question> questions;

    public Quiz() {
        // Default constructor required for calls to DataSnapshot.getValue(Quiz.class)
    }

    public Quiz(String title, String description, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    // Getters and setters for the fields
    // ...

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

