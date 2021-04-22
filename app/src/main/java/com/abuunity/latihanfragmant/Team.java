package com.abuunity.latihanfragmant;

import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {
    private String nama;
    private String logo;
    private String description;

    public Team(String nama, String logo) {
        this.nama = nama;
        this.logo = logo;
    }

    public Team(String nama, String logo, String description) {
        this.nama = nama;
        this.logo = logo;
        this.description = description;
    }


    protected Team(Parcel in) {
        nama = in.readString();
        logo = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(logo);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
