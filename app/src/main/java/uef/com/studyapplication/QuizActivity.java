package uef.com.studyapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;
    private QuizDataManager quizDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String quizKey = intent.getStringExtra("quizKey");
        if (quizKey != null) {
            DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("quizzes").child(quizKey);
            quizRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Quiz quiz = dataSnapshot.getValue(Quiz.class);
                        // Display the quiz using the retrieved data
                    } else {
                        // Handle the case where quiz data doesn't exist
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle potential errors when accessing the database
                }
            });
        }
    }
}
