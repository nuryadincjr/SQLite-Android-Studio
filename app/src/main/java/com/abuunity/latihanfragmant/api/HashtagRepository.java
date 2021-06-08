package com.abuunity.latihanfragmant.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.abuunity.latihanfragmant.pojo.Hashtags;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashtagRepository {

    private FirebaseFirestore firebaseFirestore;

    public HashtagRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<ArrayList<Hashtags>> getAllHashtag() {
        ArrayList<Hashtags> hashtagss = new ArrayList<>();;
        final MutableLiveData<ArrayList<Hashtags>> hashtagsMutableLiveData = new MutableLiveData<>();

        firebaseFirestore.collection("hashtags")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Hashtags users = document.toObject(Hashtags.class);
                        users.setTag(document.getId());

                        List<Map<String, Object>> groups = (List<Map<String, Object>>) document.get("taggers");
                        int i = 0;
                        ArrayList<String> names = new ArrayList<>();
                        for (Map<String, Object> group : groups) {
                            String id = (String) group.get("postid");
                            String post = (String) group.get("tag");
                            String uid = (String) group.get("uid");
                            names.add(id);
                            names.add(post);
                            names.add(uid);
                            i++;
                            users.setUid(uid);
                            users.setPostid(post);
                        }
                        users.setCounter(i);
                        hashtagss.add(users);

                    }
                    hashtagsMutableLiveData.postValue(hashtagss);
                }
                else
                    hashtagsMutableLiveData.setValue(null);
            }
        });

        return hashtagsMutableLiveData;
    }


    public void deleteHashtags(List<String> hasTagsOld, List<String> hasTagsNew, String postId, String uid){

        if(!hasTagsOld.isEmpty() || !hasTagsNew.isEmpty()) {
            for(String tag : hasTagsOld) {
                DocumentReference washingtonRef = firebaseFirestore.collection("hashtags").document(tag.toLowerCase());
                Map<String, Object> map = new HashMap<>();
                map.put("postid", postId);
                map.put("uid", uid);
                map.put("tag", tag.toLowerCase());

                washingtonRef.update("taggers", FieldValue.arrayRemove(map));
            }

            if(hasTagsNew != null){
                satveHashTags(hasTagsNew, postId,  uid);
            }
        }
    }

    public void satveHashTags(List<String> hasTags, String postId, String uid){
        if(!hasTags.isEmpty()) {
            for(String tag : hasTags) {
                DocumentReference washingtonRef = firebaseFirestore.collection("hashtags").document(tag.toLowerCase());
                Map<String, Object> map = new HashMap<>();
                map.put("postid", postId);
                map.put("uid", uid);
                map.put("tag", tag.toLowerCase());

                washingtonRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            washingtonRef.update("taggers", FieldValue.arrayUnion(map));

                        } else {
                            Map<String, Object> rootObj = new HashMap<>();
                            ArrayList<Object> arrayExample = new ArrayList<>();
                            Collections.addAll(arrayExample, map);
                            rootObj.put("taggers",arrayExample);
                            washingtonRef.set(rootObj);
                        }
                    }
                });
            }
        }
    }
}
