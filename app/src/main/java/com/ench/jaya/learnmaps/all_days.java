package com.ench.jaya.learnmaps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by enchanter25 on 15/6/17.
 */

public class all_days implements Parcelable{

    String lon;
    String lan;


    protected all_days(Parcel in) {
        lon = in.readString();
        lan = in.readString();
    }

    public static final Creator<all_days> CREATOR = new Creator<all_days>() {
        @Override
        public all_days createFromParcel(Parcel in) {
            return new all_days(in);
        }

        @Override
        public all_days[] newArray(int size) {
            return new all_days[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lon);
        dest.writeString(lan);
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public static Creator<all_days> getCREATOR() {
        return CREATOR;
    }
}
