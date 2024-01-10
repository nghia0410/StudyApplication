package uef.com.studyapplication.service;

import static uef.com.studyapplication.acitivity.LoginActivity.user;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import uef.com.studyapplication.adapter.CustomArrayAdapter;
import uef.com.studyapplication.adapter.UserAssignmentAdapter;
import uef.com.studyapplication.dto.NewAssignment;
import uef.com.studyapplication.dto.UserAssignment;

public class AssignmentService {
    private final FirebaseFirestore _db = FirebaseFirestore.getInstance();
    private final FirebaseStorage _storage = FirebaseStorage.getInstance();
    private static final AssignmentService INSTANCE = new AssignmentService();

    ///Tất cả assignment
    public ArrayList<NewAssignment> assignments = new ArrayList<>();
    ///Danh sách assignment của user hiện tại
    public ArrayList<UserAssignment> userAssignments = new ArrayList<>();

    public CustomArrayAdapter allAssignmentAdapter;
    public UserAssignmentAdapter allSubmittedAdapter;

    private AssignmentService() {
    }

    public static AssignmentService getInstance() {
        return INSTANCE;
    }


    public void getAssignments() {
        ArrayList<NewAssignment> assignments = new ArrayList<>();
        _db.collection("assignment").get().addOnCompleteListener(runnable -> {
            if (runnable.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : runnable.getResult()) {
                    NewAssignment assignment = documentSnapshot.toObject(NewAssignment.class);
                    try {
                        if (assignment.getSubmitter() == null || !assignment.getSubmitter().contains(user.getUuid()) || user.getType().compareTo("admin") == 0)
                            assignments.add(assignment);
                    }catch (Exception e){
                        Log.d("GetAssignment","empty");
                    }
                }
                this.assignments = assignments;
                loadAdapter();
            }
        }).addOnFailureListener(runnable -> {

        });
    }

    public void sortAssignments(NewAssignment.AssignmentLevel level) {
        assignments.sort((t0, t1) -> {
            if (t0.getLevel().equals(level))
                return -1;
            return 1;
        });
        loadAdapter();
    }

    private void loadAdapter() {
        this.allAssignmentAdapter.clear();
        this.allAssignmentAdapter.addAll(assignments);
        this.allAssignmentAdapter.notifyDataSetChanged();
    }

    private void loadSubmittedAdapter() {
        this.allSubmittedAdapter.clear();
        this.allSubmittedAdapter.addAll(userAssignments);
        this.allSubmittedAdapter.notifyDataSetChanged();
    }


    ///Filter theo level
    public void getAssignments(NewAssignment.AssignmentLevel level) {
        ArrayList<NewAssignment> assignments = new ArrayList<>();
        _db.collection("assignment")
                .whereEqualTo("level", level.toString())
                .get().addOnCompleteListener(runnable -> {
                    if (runnable.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : runnable.getResult()) {
                            NewAssignment assignment = documentSnapshot.toObject(NewAssignment.class);
                            assignments.add(assignment);

                        }
                        this.assignments = assignments;
                    }
                }).addOnFailureListener(runnable -> {
                    throw new RuntimeException("Cannot get assignments: " + runnable.getLocalizedMessage());
                });
    }

    public void addAssignment(NewAssignment assignment) {
        _db.collection("assignment")
                .add(assignment)
                .addOnSuccessListener(runnable -> {
                    runnable.update("uuid", runnable.getId());
                });
    }


    ///Thêm assignment với attachments
    public void addAssignment(NewAssignment assignment, List<Uri> attachmentPaths) {
        _db.collection("assignment")
                .add(assignment)
                .addOnSuccessListener(runnable -> {
                    runnable.update("uuid", runnable.getId());
                    if (!attachmentPaths.isEmpty()) {
                        StorageReference ref = _storage
                                .getReference()
                                .child("attachments")
                                .child(runnable.getId());
                        for (Uri path :
                                attachmentPaths) {
                            ref.child(path.getPath())
                                    .putFile(path)
                                    .addOnCompleteListener(runnable1 -> {
                                        runnable1.addOnSuccessListener(runnable2 -> {
                                            ref.getDownloadUrl().addOnSuccessListener(runnable3 ->
                                            {
                                                runnable.update("attachments", FieldValue.arrayUnion(runnable3));
                                            });
                                        });
                                    });
                        }
                    }
                });
    }


    public void deleteAssignment(String id) {
        _db.collection("assignment")
                .document(id)
                .delete()
                .addOnSuccessListener(runnable -> {
                    getAssignments();
                })
                .addOnFailureListener(runnable -> {
                    throw new RuntimeException("Cannot get assignments: " + runnable.getLocalizedMessage());
                });
    }

    ///Lấy danh sách các assignment đã được submit bởi user
    public void getUserAssignments(String id) {
        ArrayList<UserAssignment> assignments = new ArrayList<>();
        _db.collection("users")
                .document(id)
                .collection("assignment")
                .whereArrayContains("submitter", id)
                .get().addOnCompleteListener(runnable -> {
                    if (runnable.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : runnable.getResult()) {
                            UserAssignment userAssignment = documentSnapshot.toObject(UserAssignment.class);
                            assignments.add(userAssignment);
                        }
                        this.userAssignments = assignments;
                        loadSubmittedAdapter();
                    }
                }).addOnFailureListener(runnable -> {
                    throw new RuntimeException("Cannot get user assignments: " + runnable.getLocalizedMessage());
                });
    }

    public void addUserAssignment(UserAssignment assignment, String userId) {
        _db.collection("assignment")
                .document(assignment.getUuid())
                .update("submitter", FieldValue.arrayUnion(userId))
                .addOnSuccessListener(runnable1 -> {
                    assignment.getSubmitter().add(userId);
                    _db.collection("users")
                            .document(userId)
                            .collection("assignment")
                            .document(assignment.getUuid())
                            .set(assignment)
                            .addOnSuccessListener(runnable -> {
                                getUserAssignments(userId);
                            });

                });

    }

    public void updateAssignment(NewAssignment assignment) {
        _db.collection("assignment")
                .document(assignment.getUuid())
                .set(assignment).addOnSuccessListener(runnable -> {
                    getAssignments();
                });
    }
}
