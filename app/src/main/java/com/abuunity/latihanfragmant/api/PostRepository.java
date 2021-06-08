package com.abuunity.latihanfragmant.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.abuunity.latihanfragmant.pojo.Posts;
import com.abuunity.latihanfragmant.pojo.Steps;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRepository {

    private FirebaseFirestore firebaseFirestore;

    public PostRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<ArrayList<Posts>> getAllPosts() {
        ArrayList<Posts> posts = new ArrayList<>();;
        final MutableLiveData<ArrayList<Posts>> postMutableLiveData = new MutableLiveData<>();

        firebaseFirestore.collection("posts")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Posts post = document.toObject(Posts.class);
                        post.setPostid(document.getId());
                        posts.add(post);

                    }
                    postMutableLiveData.postValue(posts);
                }
                else
                    postMutableLiveData.setValue(null);

            }
        });

        return postMutableLiveData;
    }

    public void editPosts(Posts post) {
        Map<String, Object> data = new HashMap<>();
        data.put("id",post.getPostid());
        data.put("title",post.getTitle());
        data.put("description",post.getDescription());
        data.put("imageurl",post.getImageurl());
        data.put("publisher",post.getPublisher());
        data.put("stepsList", post.getStepsList());
        data.put("toolsList", post.getToolsList());

        firebaseFirestore.collection("posts")
                .document(post.getPostid()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void savePosts(Posts post) {
        firebaseFirestore.collection("posts").document(post.getPostid()).set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void deletePosts(String id) {
        firebaseFirestore.collection("posts").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
}
