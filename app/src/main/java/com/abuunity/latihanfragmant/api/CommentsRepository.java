package com.abuunity.latihanfragmant.api;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.abuunity.latihanfragmant.pojo.Comments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommentsRepository {

    private FirebaseFirestore firebaseFirestore;

    public CommentsRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<ArrayList<Comments>> getAllComment(String postid) {
        ArrayList<Comments> comments = new ArrayList<>();;
        final MutableLiveData<ArrayList<Comments>> commMutableLiveData = new MutableLiveData<>();

        CollectionReference applicationsRef = firebaseFirestore.collection("comments");
        DocumentReference applicationIdRef = applicationsRef.document(postid);
        applicationIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        List<Map<String, Object>> users = (List<Map<String, Object>>) document.get("commenter");
                        for (Map<String, Object> group : users) {
                            Comments comment = document.toObject(Comments.class);

                            String id = (String) group.get("id");
                            String content = (String) group.get("content");
                            String publisher = (String) group.get("publisher");

                            comment.setId(id);
                            comment.setComment(content);
                            comment.setPublisher(publisher);
                            comments.add(comment);
                        }
                        commMutableLiveData.postValue(comments);
                    }
                }else
                    commMutableLiveData.setValue(null);
            }
        });
        return commMutableLiveData;
    }

    public void deleteAllComments(String postid){
        firebaseFirestore.collection("comments").document(postid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void deleteComment(String postId, String id, String content, String publisher){
        DocumentReference washingtonRef = firebaseFirestore.collection("comments").document(postId);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("content", content);
        map.put("publisher", publisher);

        washingtonRef.update("commenter", FieldValue.arrayRemove(map));
    }

    public void satveComments(EditText addComment, FirebaseUser firebaseUser, String postid){
        DocumentReference washingtonRef = firebaseFirestore.collection("comments").document(postid);
        String uniqueID = UUID.randomUUID().toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", uniqueID);
        map.put("content", addComment.getText().toString());
        map.put("publisher", firebaseUser.getUid());

        washingtonRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    washingtonRef.update("commenter", FieldValue.arrayUnion(map));
                    addComment.setText("");
                } else {
                    Map<String, Object> rootObj = new HashMap<>();
                    ArrayList<Object> arrayExample = new ArrayList<>();
                    Collections.addAll(arrayExample, map);
                    rootObj.put("commenter",arrayExample);
                    washingtonRef.set(rootObj, SetOptions.merge());
                    addComment.setText("");
                }
            }
        });
    }
}
