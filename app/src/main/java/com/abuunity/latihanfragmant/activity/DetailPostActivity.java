package com.abuunity.latihanfragmant.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.adapter.StepsDetailAdapter;
import com.abuunity.latihanfragmant.adapter.ToolsDetailAdapter;
import com.abuunity.latihanfragmant.pojo.Posts;
import com.abuunity.latihanfragmant.pojo.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailPostActivity extends AppCompatActivity {

    public ImageView imageProfile;
    public ImageView imagePost;
    public ImageView imageLike;
    public ImageView imageComment;
    public ImageView imageSave;
    public ImageView imageMore;

    public TextView username;
    public TextView likeCount;
    public TextView commentCount;
    public TextView title;
    public SocialTextView description;
    private RecyclerView toolsRecyclerView;
    private RecyclerView stepsRecyclerView;
    private StepsDetailAdapter stepsAdapter;
    private ToolsDetailAdapter toolsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        imageProfile = findViewById(R.id.image_profile);
        imagePost = findViewById(R.id.image_post);
        imageLike = findViewById(R.id.like_post);
        imageComment = findViewById(R.id.comment_post);
        imageSave = findViewById(R.id.save_post);
        imageMore = findViewById(R.id.image_more);

        username = findViewById(R.id.username);
        likeCount = findViewById(R.id.like_count);
        commentCount = findViewById(R.id.comment_count);
        title = findViewById(R.id.title_post);
        description = findViewById(R.id.description_post);
        toolsRecyclerView = findViewById(R.id.rv_tools);
        stepsRecyclerView = findViewById(R.id.rv_steps);

        Posts posts = getIntent().getParcelableExtra("posts");

        Picasso.get().load(posts.getImageurl()).into(imagePost);
        description.setText(posts.getDescription().toString());
        title.setText(posts.getTitle());

        List<String> stepsList = new ArrayList<>(posts.getStepsList());
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepsAdapter = new StepsDetailAdapter(stepsList);
        stepsRecyclerView.setAdapter(stepsAdapter);

        List<String> toolsList = new ArrayList<>(posts.getToolsList());
        toolsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolsAdapter = new ToolsDetailAdapter(toolsList);
        toolsRecyclerView.setAdapter(toolsAdapter);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getImagePost(posts.getPublisher());

        imageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_option, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.share:
                            //
                            break;
                        case R.id.laporkan:
                            //
                            break;
                    }
                    return true;
                });
                popupMenu.show();
            }
        });

    }

    private void getImagePost(String publisher) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users")
                .whereEqualTo("id", publisher)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        username.setText(users.getUsername());
                    }
                }
            }});
    }
}