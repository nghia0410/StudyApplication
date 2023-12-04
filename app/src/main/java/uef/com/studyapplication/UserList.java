package uef.com.studyapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static uef.com.studyapplication.LoginActivity.mList;
import static uef.com.studyapplication.LoginActivity.userDocument;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Comparator;

public class UserList {
    public static void UpdateL(FirebaseFirestore db, Context cxt)
    {
//       List mList = new ArrayList<AssignmentList>();  //this is my arraylist
        db.collection("users")
                .document(userDocument.getId())
                .collection("assignment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mList.clear();
                            Intent intent = new Intent(cxt, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                mList.add(new AssignmentList(document.getId(),document.toObject(Assignment.class)));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            mList.sort(new Comparator<AssignmentList>(){
                                @Override
                                public int compare(AssignmentList t0, AssignmentList t1) {
                                    if(t1.getAssignment().getTitle() == null){
                                        Log.v("mList","neutral sort");
                                        return -1;
                                    }else if(t0.getAssignment().getTitle() == null){
                                        Log.v("mList","neutral sort");
                                        return 1;
                                    }
                                    return 0;
                                }
                            });
                            Log.v(TAG,mList.toString());
                            cxt.startActivity(intent);
                        } else {
                            Intent intent = new Intent(cxt, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            cxt.startActivity(intent);
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    };
}
