package com.abuunity.latihanfragmant.pojo;

public class Hashtags {

    private String postid;
    private String uid;
    private String tag;
    private int counter;

    public Hashtags() {
    }

    public Hashtags(String postid, String uid, String tag) {
        this.postid = postid;
        this.uid = uid;
        this.tag = tag;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}