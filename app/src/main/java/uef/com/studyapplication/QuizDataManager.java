package uef.com.studyapplication;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizDataManager {
    private DatabaseReference databaseReference;

    public QuizDataManager() {
        databaseReference = FirebaseDatabase.getInstance().getReference("quizzes");
    }

    public void addQuestion(String quizTitle, Question newQuestion) {
        String questionKey = databaseReference.child(quizTitle).child("questions").push().getKey();
        if (questionKey != null) {
            databaseReference.child(quizTitle).child("questions").child(questionKey).setValue(newQuestion);
        }
    }

    public void retrieveQuestions(String quizTitle, final OnQuestionsRetrievedListener listener) {
        databaseReference.child(quizTitle).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Question> questionList = new ArrayList<>();
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    if (question != null) {
                        questionList.add(question);
                    }
                }
                listener.onQuestionsRetrieved(questionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public interface OnQuestionsRetrievedListener {
        void onQuestionsRetrieved(List<Question> questionList);
    }
}
