package com.abuunity.latihanfragmant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.pojo.Notifications;
import com.abuunity.latihanfragmant.pojo.Posts;
import com.abuunity.latihanfragmant.pojo.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private Context context;
    private List<Notifications> notificationsList;

    public NotificationsAdapter(Context context, List<Notifications> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent,false);

        return  new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notifications notifications = notificationsList.get(position);

        getUser(holder.imageProfile, holder.username, notifications.getUserId());

        if(notifications.isPost()) {
            holder.imagePost.setVisibility(View.VISIBLE);
            getPost(holder.imagePost, notifications.getPostId());
        }else {
            holder.imagePost.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notifications.isPost()) {
                    context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                            .putString("postId", notifications.getPostId()).apply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imageProfile;
        public ImageView imagePost;
        public TextView username;
        public TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.image_profile);
            imagePost = itemView.findViewById(R.id.image_post);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment_post);
        }
    }

    private void getUser(CircleImageView imageProfile, TextView usernameTextView, String userId) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);

                if(users.getImageUrl().equals("default")) {
                    imageProfile.setImageResource(R.drawable.ic_person);
                }else {
                    Picasso.get().load(users.getImageUrl()).placeholder(R.drawable.ic_person).into(imageProfile);
                }

                usernameTextView.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getPost(ImageView imagePost, String postId) {
        FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Posts posts = snapshot.getValue(Posts.class);
                Picasso.get().load(posts.getImageurl()).placeholder(R.drawable.ic_photo).into(imagePost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
