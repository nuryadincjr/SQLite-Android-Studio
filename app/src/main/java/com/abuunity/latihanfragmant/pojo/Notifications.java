package com.abuunity.latihanfragmant.pojo;

public class Notifications {

    private String userId;
    private String texts;
    private String postId;
    private boolean isPost;

    public Notifications() {
    }

    public Notifications(String userId, String texts, String postId, boolean isPost) {
        this.userId = userId;
        this.texts = texts;
        this.postId = postId;
        this.isPost = isPost;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTexts() {
        return texts;
    }

    public void setTexts(String texts) {
        this.texts = texts;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }
}
