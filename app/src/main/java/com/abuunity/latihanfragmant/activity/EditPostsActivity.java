package com.abuunity.latihanfragmant.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.MainActivity;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.adapter.StepsAdapter;
import com.abuunity.latihanfragmant.adapter.StepsDetailAdapter;
import com.abuunity.latihanfragmant.adapter.ToolsAdapter;
import com.abuunity.latihanfragmant.adapter.ToolsDetailAdapter;
import com.abuunity.latihanfragmant.api.HashtagRepository;
import com.abuunity.latihanfragmant.api.PostRepository;
import com.abuunity.latihanfragmant.fragment.HomeFragment;
import com.abuunity.latihanfragmant.pojo.Posts;
import com.abuunity.latihanfragmant.pojo.Steps;
import com.abuunity.latihanfragmant.pojo.Tools;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hendraanggrian.appcompat.socialview.Hashtag;
import com.hendraanggrian.appcompat.widget.HashtagArrayAdapter;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditPostsActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView close;
    private ImageView imageAdd;
    private TextView post;
    private SocialAutoCompleteTextView title;
    private SocialAutoCompleteTextView description;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog dialog;

    private RecyclerView recyclerViewTools;
    private List<Tools> toolsList;
    private ToolsAdapter toolsAdapter;
    private Tools tools;

    private RecyclerView recyclerViewSteps;
    private List<Steps> stepsList;
    private StepsAdapter stepsAdapter;
    private ImageView imageSteps;
    private Steps steps;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private StorageTask uploadTask;
    private List<String> hasTagsOld;
    private Posts postsdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posts);

        postsdata = getIntent().getParcelableExtra("posts");
        recyclerViewSteps = findViewById(R.id.rv_steps);
        recyclerViewTools = findViewById(R.id.rv_tools);

        stepsList = new ArrayList<>();
        for(int i=0; i<postsdata.getStepsList().size(); i++) {
            stepsList.add(new Steps(postsdata.getStepsList().get(i)));
        }

        toolsList = new ArrayList<>();
        for(int i=0; i<postsdata.getToolsList().size(); i++) {
            toolsList.add(new Tools(postsdata.getToolsList().get(i)));
        }

        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(simpleCallback);
        itemTouchHelper2.attachToRecyclerView(recyclerViewTools);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback2);
        itemTouchHelper.attachToRecyclerView(recyclerViewSteps);

        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(this));
        stepsAdapter = new StepsAdapter(stepsList);
        recyclerViewSteps.setAdapter(stepsAdapter);

        recyclerViewTools.setLayoutManager(new LinearLayoutManager(this));
        toolsAdapter = new ToolsAdapter(toolsList);
        recyclerViewTools.setAdapter(toolsAdapter);

        storageReference = FirebaseStorage.getInstance().getReference().child("posts");
        firebaseFirestore = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Posting");
        dialog.setCancelable(false);

        close = findViewById(R.id.close);
        imageAdd = findViewById(R.id.image_add);
        post = findViewById(R.id.post);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        firebaseAuth = FirebaseAuth.getInstance();
        imageSteps = findViewById(R.id.image_steps);



        title.setText(postsdata.getTitle());
        description.setText(postsdata.getDescription());
        Picasso.get().load(postsdata.getImageurl()).into(imageAdd);
        hasTagsOld = description.getHashtags();


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPostsActivity.this, MainActivity.class));
                finish();
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 25);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        toolsAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                openMenuEditTools(view,position);
            }
        });

        stepsAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                openMenuEditSteps(view,position);
            }
        });
    }

    private String getFileExtension(Uri imageUri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(imageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 25 && resultCode == RESULT_OK && null != data) {
            imageUri = data.getData();
            imageAdd.setImageURI(imageUri);

        } else if (requestCode == 26 && resultCode == RESULT_OK && null != data) {
            imageUri = data.getData();
            imageSteps.setImageURI(imageUri);
        }else {
            imageUri = data.getData();
            imageAdd.setImageURI(imageUri);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayAdapter<Hashtag> hashtagArrayAdapter = new HashtagArrayAdapter<>(getApplicationContext());

        FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    hashtagArrayAdapter.add(new Hashtag(snapshot1.getKey(), (int) snapshot.getChildrenCount()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        description.setHashtagAdapter(hashtagArrayAdapter);
    }

    private void upload() {
        dialog.show();

        List<String> hasTags = description.getHashtags();
        List<String> tools = new ArrayList<>();
        for(int i=0; i < toolsAdapter.getItemCount(); i++){
            ToolsAdapter.ViewHolder viewHolder = (ToolsAdapter.ViewHolder) recyclerViewTools.findViewHolderForAdapterPosition(i);
            EditText editText = viewHolder.inputTools;
            tools.add(editText.getText().toString());
        }

        List<String> steps = new ArrayList<>();
        for(int i=0; i < stepsAdapter.getItemCount(); i++){
            StepsAdapter.ViewHolder viewHolder = (StepsAdapter.ViewHolder) recyclerViewSteps.findViewHolderForAdapterPosition(i);
            EditText editText = viewHolder.inputTools;
            steps.add(editText.getText().toString());
        }

        Posts posts = new Posts(postsdata.getPostid(), title.getText().toString(), description.getText().toString(), postsdata.getImageurl(), postsdata.getPublisher(), tools, steps);

        if(imageUri != null) {
            if (!imageUri.equals("default")){
                storageReference.child(postsdata.getPostid()+"/"+HomeFragment.getImagPath(postsdata.getImageurl())).delete();
            }

            StorageReference filePath = storageReference.child(postsdata.getPostid()+"/"+System.currentTimeMillis()+ "." + getFileExtension(imageUri));
            uploadTask = filePath.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    Uri downdoadUri = (Uri) task.getResult();
                    String url = downdoadUri.toString();

                    posts.setImageurl(url);
                    new PostRepository().editPosts(posts);
                    new HashtagRepository().deleteHashtags(hasTagsOld, hasTags, postsdata.getPostid(), postsdata.getPublisher());

                    dialog.dismiss();
                    startActivity(new Intent(EditPostsActivity.this, MainActivity.class));
                }
            });
        } else {

            new PostRepository().editPosts(posts);
            new HashtagRepository().deleteHashtags(hasTagsOld, hasTags, postsdata.getPostid(), postsdata.getPublisher());

            dialog.dismiss();
            startActivity(new Intent(EditPostsActivity.this, MainActivity.class));
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP
            | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(toolsList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP
            | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(stepsList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    public void openMenuEditTools(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_adder, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.add:
                    toolsList.add(position,new Tools(""));
                    toolsAdapter.notifyItemInserted(position+1);
                    break;
                case R.id.delete:

                    toolsList.remove(position);
                    toolsAdapter.notifyItemRemoved(position);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }


    public void openMenuEditSteps(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_adder, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.add:
                    stepsList.add(position, new Steps(""));
                    stepsAdapter.notifyItemInserted(position+1);

                    break;
                case R.id.delete:
                    stepsList.remove(position);
                    stepsAdapter.notifyItemRemoved(position);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }
}
