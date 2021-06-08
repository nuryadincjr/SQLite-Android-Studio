package com.abuunity.latihanfragmant.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.api.UsersRepository;
import com.abuunity.latihanfragmant.fragment.HomeFragment;
import com.abuunity.latihanfragmant.pojo.Users;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView closeBtn;
    private TextView savesBtn;
    private CircleImageView imageProfile;
    private ImageButton browsersBtn;
    private TextView name;
    private TextView usernanme;
    private TextView bio;

    private Uri imageUri;
    private FirebaseUser firebaseUser;
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog dialog;
    private Users usersdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        usersdata = getIntent().getParcelableExtra("users");
        storageReference = FirebaseStorage.getInstance().getReference().child("profiles");

        dialog = new ProgressDialog(this);
        dialog.setMessage("Posting");
        dialog.setCancelable(false);

        closeBtn = findViewById(R.id.close);
        savesBtn = findViewById(R.id.save);
        imageProfile = findViewById(R.id.image_profile);
        browsersBtn = findViewById(R.id.btn_browseres);
        name = findViewById(R.id.nama);
        usernanme = findViewById(R.id.username);
        bio = findViewById(R.id.bio);

        name.setText(usersdata.getName());
        usernanme.setText(usersdata.getUsername());
        bio.setText(usersdata.getBio());
        if(!usersdata.getImageUrl().equals("default"))
            Picasso.get().load(usersdata.getImageUrl()).into(imageProfile);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        browsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 25);
            }
        });

        savesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        dialog.show();
        Users users = new Users(usersdata.getId(), bio.getText().toString(), name.getText().toString(),
                null, usersdata.getImageUrl(), usernanme.getText().toString(),null );

        if(imageUri != null) {
            if (!imageUri.equals("default")){
                storageReference.child(usersdata.getId()+"/"+ HomeFragment.getImagPath(usersdata.getImageUrl())).delete();
            }

            StorageReference filePath = storageReference.child(usersdata.getId()+"/"+System.currentTimeMillis()+ "." + getFileExtension(imageUri));
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
                    users.setImageUrl(url);
                    new UsersRepository().editUsers(users);
                    finish();
                }
            });
        } else {
            new UsersRepository().editUsers(users);
            finish();
        }

        dialog.dismiss();
    }

    private String getFileExtension(Uri imageUri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(imageUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 25 || resultCode == RESULT_OK && null != data) {
            imageUri = data.getData();
            imageProfile.setImageURI(imageUri);
        }
    }
}