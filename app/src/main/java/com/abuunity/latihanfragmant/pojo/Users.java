package com.abuunity.latihanfragmant.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    private String id;
    private String bio;
    private String name;
    private String email;
    private String imageUrl;
    private String username;
    private String password;

    public Users() {
    }

    public Users(String id, String bio, String name, String email, String imageUrl, String username, String password) {
        this.id = id;
        this.bio = bio;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.username = username;
        this.password = password;
    }

    protected Users(Parcel in) {
        id = in.readString();
        bio = in.readString();
        name = in.readString();
        email = in.readString();
        imageUrl = in.readString();
        username = in.readString();
        password = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(bio);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(imageUrl);
        dest.writeString(username);
        dest.writeString(password);
    }
}
