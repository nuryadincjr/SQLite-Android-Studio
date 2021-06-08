package com.abuunity.latihanfragmant.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.ViewModel.MainViewModel;
import com.abuunity.latihanfragmant.adapter.CommentAdapter;
import com.abuunity.latihanfragmant.api.CommentsRepository;
import com.abuunity.latihanfragmant.pojo.Comments;
import com.abuunity.latihanfragmant.pojo.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerViewComment;
    private CommentAdapter commentAdapter;
    private ArrayList<Comments> commentsList;
    private MainViewModel mainViewModel;

    private EditText addComment;
    private ImageView postComment;
    private CircleImageView imageProfile;
    private String postid;
    private String publisher;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");
        publisher = intent.getStringExtra("publisher");

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        addComment = findViewById(R.id.comment_add);
        imageProfile = findViewById(R.id.image_profile);
        postComment = findViewById(R.id.comment_post);
        recyclerViewComment = findViewById(R.id.rv_posts);

        commentsList = new ArrayList<>();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(addComment.getText().toString())) {
                    Toast.makeText(CommentActivity.this, "No comment added", Toast.LENGTH_SHORT).show();
                } else {
                    new CommentsRepository().satveComments(addComment, firebaseUser, postid);
                    addListener(postid);
                }
            }
        });

        getUserImage();
        getComment();
    }

    private void getUserImage() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users")
                .whereEqualTo("id", firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    private void getComment() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getCommentMutableLiveData(postid).observe(this, new Observer<ArrayList<Comments>>() {
            @Override
            public void onChanged(ArrayList<Comments> comments) {
                commentsList.clear();
                commentsList.addAll(comments);
                loadsComment();
            }
        });
    }

    private void loadsComment() {
        commentAdapter= new CommentAdapter(this, commentsList, postid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.scrollToPosition(commentAdapter.getItemCount()-1);
        recyclerViewComment.setLayoutManager(layoutManager);
        recyclerViewComment.setAdapter(commentAdapter);
        recyclerViewComment.setItemAnimator(new DefaultItemAnimator());

        commentAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(firebaseUser.getUid().equals(commentsList.get(position).getPublisher())) {
                    openMenuEdit(view, position);
                }
            }
        });

    }

    public void openMenuEdit(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_remover, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            dialogShow(position);
            return true;
        });
        popupMenu.show();
    }

    private void dialogShow(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin ingin menghapus komentar?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = commentsList.get(position).getId();
                        String content = commentsList.get(position).getComment();
                        String publisher = commentsList.get(position).getPublisher();
                        new CommentsRepository().deleteComment(postid, id, content, publisher);
                        addListener(postid);
                    }
                });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addListener(String postid){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("comments").document(postid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(CommentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null && value.exists()) {
                    getComment();
                } else {
                    Toast.makeText(CommentActivity.this, "Give your first comment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}