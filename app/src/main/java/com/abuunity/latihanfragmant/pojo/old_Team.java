package com.abuunity.latihanfragmant.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class old_Team implements Parcelable {
    private String nama;
    private String logo;
    private String description;

    public old_Team(String nama, String logo) {
        this.nama = nama;
        this.logo = logo;
    }

    public old_Team(String nama, String logo, String description) {
        this.nama = nama;
        this.logo = logo;
        this.description = description;
    }


    protected old_Team(Parcel in) {
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

    public static final Creator<old_Team> CREATOR = new Creator<old_Team>() {
        @Override
        public old_Team createFromParcel(Parcel in) {
            return new old_Team(in);
        }

        @Override
        public old_Team[] newArray(int size) {
            return new old_Team[size];
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
