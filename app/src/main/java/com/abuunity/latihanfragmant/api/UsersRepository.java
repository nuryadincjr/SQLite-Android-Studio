package com.abuunity.latihanfragmant.api;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.pojo.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersRepository {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    public UsersRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public MutableLiveData<ArrayList<Users>> getAllUsers() {
        ArrayList<Users> userss = new ArrayList<>();;
        final MutableLiveData<ArrayList<Users>> usersMutableLiveData = new MutableLiveData<>();

        firebaseFirestore.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Users users = document.toObject(Users.class);
                        users.setId(document.getId());
                        userss.add(users);;
                    }
                    usersMutableLiveData.postValue(userss);
                }
                else
                    usersMutableLiveData.setValue(null);
            }
        });

        return usersMutableLiveData;
    }

    public void saveUsers(Users users) {
        firebaseFirestore.collection("users").document(users.getId()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void getUser(String usersId) {
        firebaseFirestore.collection("users").document(usersId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }
        });
    }

    public void editUsers(Users users) {

        Map<String, Object> data = new HashMap<>();
        data.put("id",users.getId());
        data.put("bio",users.getBio());
        data.put("imageUrl",users.getImageUrl());
        data.put("nama",users.getName());
        data.put("username", users.getUsername());

        firebaseFirestore.collection("users")
                .document(users.getId()).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }

    public void deleteUsers(String id) {
        firebaseFirestore.collection("users").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    private void getUserImage(String uid, ImageView imageProfile) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users")
                .whereEqualTo("id", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Users users = document.toObject(Users.class);
                        if(users.getImageUrl().equals("default")) {
                            imageProfile.setImageResource(R.drawable.ic_person);
                        }else {
                            Picasso.get().load(users.getImageUrl()).into(imageProfile);

                        }
                    }
                }
            }});
    }

}
