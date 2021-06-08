package com.abuunity.latihanfragmant.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.ViewModel.MainViewModel;
import com.abuunity.latihanfragmant.activity.DetailPostActivity;
import com.abuunity.latihanfragmant.activity.EditPostsActivity;
import com.abuunity.latihanfragmant.adapter.PostAdapter;
import com.abuunity.latihanfragmant.api.CommentsRepository;
import com.abuunity.latihanfragmant.api.HashtagRepository;
import com.abuunity.latihanfragmant.api.PostRepository;
import com.abuunity.latihanfragmant.pojo.Posts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private ArrayList<Posts> postsArrayList;
    private SwipeRefreshLayout refreshLayout;
    private MainViewModel mainViewModel;

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        recyclerViewPosts = view.findViewById(R.id.rv_posts);
        storageReference = FirebaseStorage.getInstance().getReference().child("posts");
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        postsArrayList = new ArrayList<>();

        refreshLayout = view.findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorPrimary,
                R.color.colorPrimarySoft,
                R.color.colorAccent
        );

        getPosts();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void openMenuEdit(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_editor, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    Posts posts = postsArrayList.get(position);
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
        getPosts();
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

                        String ids = postsArrayList.get(position).getPostid();
                        String publishers = postsArrayList.get(position).getPublisher();
                        String tag = postsArrayList.get(position).getDescription();
                        String filePath = postsArrayList.get(position).getImageurl();

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
                            storageReference.child(ids+"/"+getImagPath(filePath)).delete();
                        }

                        getPosts();
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

    private void getPosts() {
        refreshLayout.setRefreshing(true);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getPostsMutableLive().observe(getViewLifecycleOwner(), new Observer<ArrayList<Posts>>() {
            @Override
            public void onChanged(ArrayList<Posts> posts) {
                refreshLayout.setRefreshing(false);
                postsArrayList.clear();
                postsArrayList.addAll(posts);
                loadsPosts();
            }
        });
    }

    private void loadsPosts() {

        postAdapter= new PostAdapter(getContext(), postsArrayList);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPosts.setAdapter(postAdapter);
        recyclerViewPosts.setItemAnimator(new DefaultItemAnimator());

        postAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(firebaseUser.getUid().equals(postsArrayList.get(position).getPublisher())) {
                    openMenuEdit(view, position);
                }
            }
        });
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