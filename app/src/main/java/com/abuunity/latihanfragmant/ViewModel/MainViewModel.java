package com.abuunity.latihanfragmant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.abuunity.latihanfragmant.api.CommentsRepository;
import com.abuunity.latihanfragmant.api.HashtagRepository;
import com.abuunity.latihanfragmant.api.PostRepository;
import com.abuunity.latihanfragmant.api.UsersRepository;
import com.abuunity.latihanfragmant.pojo.Comments;
import com.abuunity.latihanfragmant.pojo.Hashtags;
import com.abuunity.latihanfragmant.pojo.Posts;
import com.abuunity.latihanfragmant.pojo.Users;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {

    private final UsersRepository usersRepository;
    private final PostRepository postsRepository;
    private final HashtagRepository hashtagRepository;
    private final CommentsRepository commentsRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        postsRepository = new PostRepository();
        hashtagRepository = new HashtagRepository();
        commentsRepository = new CommentsRepository();
        usersRepository = new UsersRepository();
    }

    public MutableLiveData<ArrayList<Users>> getUsersMutableLive() {
        MutableLiveData<ArrayList<Users>> usersMutableLive;
        return usersMutableLive = usersRepository.getAllUsers();
    }

    public MutableLiveData<ArrayList<Posts>> getPostsMutableLive() {
        MutableLiveData<ArrayList<Posts>> postsMutableLive;
        return postsMutableLive = postsRepository.getAllPosts();
    }

    public MutableLiveData<ArrayList<Hashtags>> getHashTagsMutableLiveData() {
        MutableLiveData<ArrayList<Hashtags>> hashtagsLiveData;
        return hashtagsLiveData = hashtagRepository.getAllHashtag();
    }

    public MutableLiveData<ArrayList<Comments>> getCommentMutableLiveData(String postid) {
        MutableLiveData<ArrayList<Comments>> commentMutableLiveData;
        return commentMutableLiveData = commentsRepository.getAllComment(postid);
    }
}
