package uef.com.studyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuizListAdapter adapter;
    private DatabaseReference databaseReference;
    private List<String> quizKeys;
    private Button btnLamBai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quizz);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        quizKeys = new ArrayList<>();
        adapter = new QuizListAdapter(quizKeys); // Create an Adapter to display the list of quizKeys
        recyclerView.setAdapter(adapter);

        // Initialize the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("quizzes");

        // Listen for changes in the Firebase Database to retrieve the list of quizKeys
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quizKeys.clear(); // Clear the old list
                for (DataSnapshot quizSnapshot : snapshot.getChildren()) {
                    String quizKey = quizSnapshot.getKey();
                    quizKeys.add(quizKey); // Add quizKey to the list
                }
                adapter.notifyDataSetChanged(); // Update the interface when there is a change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });

        // Set up the button to start a quiz
        btnLamBai = new Button(this);
        btnLamBai.setText("Start Quiz");
        btnLamBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to start a quiz
                if (!quizKeys.isEmpty()) {
                    startQuiz(quizKeys.get(0)); // For example, pass the first quizKey to the startQuiz method
                }
            }
        });
    }

    private void startQuiz(String quizKey) {
        // Pass the quiz key to the new activity
        Intent intent = new Intent(QuizListActivity.this, QuizActivity.class);
        intent.putExtra("quizKey", quizKey);
        startActivity(intent);
    }
}
