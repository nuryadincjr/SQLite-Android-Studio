package com.abuunity.latihanfragmant.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.activity.EditPostsActivity;
import com.abuunity.latihanfragmant.activity.EditProfileActivity;
import com.abuunity.latihanfragmant.adapter.PostAdapter;
import com.abuunity.latihanfragmant.api.CommentsRepository;
import com.abuunity.latihanfragmant.api.HashtagRepository;
import com.abuunity.latihanfragmant.api.PostRepository;
import com.abuunity.latihanfragmant.api.UsersRepository;
import com.abuunity.latihanfragmant.pojo.Posts;
import com.abuunity.latihanfragmant.pojo.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView imageProfil;
    private ImageView options;
    private ImageButton postsApp;
    private ImageButton saveApp;

    private TextView followers;
    private TextView following;
    private TextView posts;
    private TextView name;
    private TextView bio;
    private TextView username;
    private TextView usernamename;
    private TextView email;

    private RecyclerView recyclerViewPosts;
    private RecyclerView recyclerViewSave;

    private FirebaseUser firebaseUser;
    private String profilid;
    private Button editProfile;

    //tes
    private PostAdapter postAdapter;
    private PostAdapter savePostAdapter;
    private List<Posts> postsList;
    private List<Posts> savesList;

    private ImageView imageSave;
    private String imagedata;
    private Users usersdata;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        profilid = firebaseUser.getUid();

        String data = getContext().getSharedPreferences("PROFILE", Context.MODE_PRIVATE).getString("profileId", "none");
        if(data.equals("none")) {
            profilid = firebaseUser.getUid();
        } else{
            profilid = data;
        }

        imageSave = view.findViewById(R.id.save_post);

        imageProfil = view.findViewById(R.id.image_profile);
        options = view.findViewById(R.id.options_bar);
        postsApp = view.findViewById(R.id.post_bar);
        saveApp = view.findViewById(R.id.save_bar);

        followers = view.findViewById(R.id.followers_counts);
        following = view.findViewById(R.id.following_counts);
        posts = view.findViewById(R.id.post_counts);
        name = view.findViewById(R.id.full_name);
        bio = view.findViewById(R.id.bio);
        username = view.findViewById(R.id.username);
        usernamename = view.findViewById(R.id.usernamename);
        email = view.findViewById(R.id.email);

        recyclerViewPosts = view.findViewById(R.id.rv_my_posts);
        recyclerViewSave = view.findViewById(R.id.rv_my_save);
        editProfile = view.findViewById(R.id.edit_profile);

        postsList = new ArrayList<>();
//        postAdapter = new PostAdapter(getContext(), postsList);
//        recyclerViewPosts.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerViewPosts.setAdapter(postAdapter);
//
//        savesList = new ArrayList<>();
////        savePostAdapter = new PostAdapter(getContext(), savesList);
//        recyclerViewSave.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerViewSave.setAdapter(savePostAdapter);

//        readPosts();
//        readSaves();
        userInfo();
//        countsfollowersing();
        countPosts();

        if(profilid.equals(firebaseUser.getUid())) {
            editProfile.setText("Edit profile");
        } else {
//            checkFollowingStatus();
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnText = editProfile.getText().toString();

                if(btnText.equals("Edit profile")) {
                    Intent intent = new Intent(getContext(), EditProfileActivity.class);
                    intent.putExtra("users", usersdata);
                    startActivity(intent);

                } else {
//                    if(btnText.equals("follow")) {
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                                .child("following").child(profilid).setValue(true);
//
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(profilid)
//                                .child("followers").child(firebaseUser.getUid()).setValue(true);
//                    } else {
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                                .child("following").child(profilid).removeValue();
//
//                        FirebaseDatabase.getInstance().getReference().child("Follow").child(profilid)
//                                .child("followers").child(firebaseUser.getUid()).removeValue();
//                    }
                }
            }
        });

//        recyclerViewPosts.setVisibility(View.VISIBLE);
//        recyclerViewSave.setVisibility(View.GONE);
//
//        postsApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readPosts();
//                saveApp.setImageResource(R.drawable.ic_bookmark);
//                postsApp.setImageResource(R.drawable.ic_app);
//                recyclerViewPosts.setVisibility(View.VISIBLE);
//                recyclerViewSave.setVisibility(View.GONE);
//            }
//        });
//
//        saveApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readSaves();
//                saveApp.setImageResource(R.drawable.ic_bookmark_fill);
//                postsApp.setImageResource(R.drawable.ic_app_registration);
//                recyclerViewPosts.setVisibility(View.GONE);
//                recyclerViewSave.setVisibility(View.VISIBLE);
//            }
//        });

        return view;
    }

//    private void checkFollowingStatus() {
//        FirebaseDatabase.getInstance().getReference().child("Follow")
//                .child(firebaseUser.getUid()).child("following").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.child(profilid).exists()) {
//                    editProfile.setText("following");
//                }else{
//                    editProfile.setText("follow");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void countPosts() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("posts")
                .whereEqualTo("publisher", profilid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if(task.isSuccessful()) {
                      postsList = new ArrayList<>();
                      posts.setText(String.valueOf(task.getResult().size()));
                      for (QueryDocumentSnapshot document : task.getResult()) {
                          Posts postss = document.toObject(Posts.class);

                          postsList.add(new Posts(postss.getPostid(), postss.getTitle(), postss.getDescription(),
                                  postss.getImageurl(), postss.getPublisher(), postss.getToolsList(),postss.getStepsList()));

                      }

                      postAdapter = new PostAdapter(getContext(), postsList);
                      recyclerViewPosts.setLayoutManager(new GridLayoutManager(getContext(), 2));
                      recyclerViewPosts.setAdapter(postAdapter);

                      postAdapter.setItemClickListener(new ItemClickListener() {
                          @Override
                          public void onClick(View view, int position) {
                              if(firebaseUser.getUid().equals(postsList.get(position).getPublisher())) {
                                  openMenuEdit(view, position);
                              }
                          }
                      });
                  }
              }

        });

//        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int counter = 0;
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Posts posts1 = dataSnapshot.getValue(Posts.class);
//
//                    if(posts1.getPublisher().equals(profilid))
//                        counter++;
//
//                }
//                posts.setText(String.valueOf(counter));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

//    private void countsfollowersing() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(profilid);
//
//        reference.child("followers").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                followers.setText(String.valueOf(snapshot.getChildrenCount()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        reference.child("following").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                following.setText(String.valueOf(snapshot.getChildrenCount()));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void userInfo() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users")
                .whereEqualTo("id", profilid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Users users = document.toObject(Users.class);
                        name.setText(users.getName());
                        username.setText(users.getUsername());
                        usernamename.setText(users.getUsername());
                        email.setText(users.getEmail());
                        bio.setText(users.getBio());
                        if(users.getImageUrl().equals("default")) {
                            imageProfil.setImageResource(R.drawable.ic_person);
                        } else {
                            Picasso.get().load(users.getImageUrl()).into(imageProfil);
                        }
                        usersdata = new Users(profilid, users.getBio(), users.getName(), users.getEmail(), users.getImageUrl(), users.getUsername(), users.getPassword());
                    }

                }
            }
        });




//        FirebaseDatabase.getInstance().getReference().child("Users").child(profilid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Users users = snapshot.getValue(Users.class);
//
//                if(users.getImageUrl().equals("default")) {
//                    imageProfil.setImageResource(R.drawable.ic_person);
//                } else {
//                    Picasso.get().load(users.getImageUrl()).into(imageProfil);
//                }
//
//                username.setText(users.getUsername());
//                name.setText(users.getName());
//                bio.setText(users.getBio());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

//    private void readPosts() {
//        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                postsList.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Posts posts = dataSnapshot.getValue(Posts.class);
//
//                    if(posts.getPublisher().equals(profilid)) {
//                        postsList.add(posts);
//                    }
//                }
//                Collections.reverse(postsList);
//                postAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void readSaves() {
//        final List<String> savesListPost = new ArrayList<>();
//        FirebaseDatabase.getInstance().getReference().child("Saves")
//                .child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    savesListPost.add(dataSnapshot.getKey());
//                }
//
//                FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                        savesList.clear();
//                        for(DataSnapshot dataSnapshot1 : snapshot1.getChildren()) {
//                            Posts posts = dataSnapshot1.getValue(Posts.class);
//
//                            for(String id : savesListPost) {
//                                if(posts.getPostid().equals(id)) {
//                                    savesList.add(posts);
//                                }
//                            }
//                        }
//                        Collections.reverse(postsList);
//                        savePostAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void openMenuEdit(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_editor, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    Posts posts = postsList.get(position);
                    Posts postss = new Posts(posts.getPostid(), posts.getTitle(), posts.getDescription(),
                            posts.getImageurl(), posts.getPublisher(), posts.getToolsList(),posts.getStepsList());
                    Intent intent = new Intent(getContext(), EditPostsActivity.class);
                    intent.putExtra("posts", postss);
                    startActivity(intent);
                    break;
                case R.id.delete:
                    dialogShow(position);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        countPosts();
        userInfo();
    }

    private void dialogShow(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Yakin ingin menghapus?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProgressDialog dialog1 = new ProgressDialog(getContext());
                        dialog1.setMessage("Deleting");
                        dialog1.setCancelable(false);
                        dialog1.show();

                        String ids = postsList.get(position).getPostid();
                        String publishers = postsList.get(position).getPublisher();
                        String tag = postsList.get(position).getDescription();
                        String filePath = postsList.get(position).getImageurl();

                        new PostRepository().deletePosts(ids);
                        new CommentsRepository().deleteAllComments(ids);

                        Pattern pattern = Pattern.compile("#([A-Za-z0-9_-]+)");
                        Matcher matcher = pattern.matcher(tag);
                        List<String> listTag = new ArrayList<>();
                        while (matcher.find()) {
                            listTag.add(matcher.group(1));
                        }

                        new HashtagRepository().deleteHashtags(listTag, null, ids, publishers);

                        if (getImagPath(filePath)==null || !getImagPath(filePath).equals("default")){
                            storageReference.child("posts/"+ids+"/"+getImagPath(filePath)).delete();
                        }

                        countPosts();
                        dialog1.dismiss();
                    }
                });
        builder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static String getImagPath(String imageUrl) {
        Pattern patternImg = Pattern.compile("%2F([A-Za-z0-9.-]+)");
        Matcher mat = patternImg.matcher(imageUrl);
        List<String> listImg = new ArrayList<>();
        while (mat.find()) {
            listImg.add(mat.group(1));
        }
        if(imageUrl.equals("default") || imageUrl ==null) {
            return "default";
        }else
            return listImg.get(1);
    }
}