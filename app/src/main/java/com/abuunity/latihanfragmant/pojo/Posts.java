package com.abuunity.latihanfragmant.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Posts implements Parcelable {

    private String postid;
    private String title;
    private String description;
    private String imageurl;
    private String publisher;
    private List<String> toolsList;
    private List<String> stepsList;

    public Posts() {
    }

    public Posts(String postid, String title, String description, String imageurl, String publisher, List<String> toolsList, List<String> stepsList) {
        this.postid = postid;
        this.title = title;
        this.description = description;
        this.imageurl = imageurl;
        this.publisher = publisher;
        this.toolsList = toolsList;
        this.stepsList = stepsList;
    }

    protected Posts(Parcel in) {
        postid = in.readString();
        title = in.readString();
        description = in.readString();
        imageurl = in.readString();
        publisher = in.readString();
        toolsList = in.createStringArrayList();
        stepsList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postid);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageurl);
        dest.writeString(publisher);
        dest.writeStringList(toolsList);
        dest.writeStringList(stepsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Posts> CREATOR = new Creator<Posts>() {
        @Override
        public Posts createFromParcel(Parcel in) {
            return new Posts(in);
        }

        @Override
        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getToolsList() {
        return toolsList;
    }

    public void setToolsList(List<String> toolsList) {
        this.toolsList = toolsList;
    }

    public List<String> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<String> stepsList) {
        this.stepsList = stepsList;
    }


}
