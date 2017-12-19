package com.example.a90678.mm_2017_05_28_15_503.main.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 90678 on 2017/6/12.
 */

public class TabTitleAndType implements Parcelable {
    int type;
    String title;
    String type2;

    public TabTitleAndType() {
    }

    protected TabTitleAndType(Parcel in) {
        type = in.readInt();
        title = in.readString();
        type2 = in.readString();
    }

    public static final Creator<TabTitleAndType> CREATOR = new Creator<TabTitleAndType>() {
        @Override
        public TabTitleAndType createFromParcel(Parcel in) {
            return new TabTitleAndType(in);
        }

        @Override
        public TabTitleAndType[] newArray(int size) {
            return new TabTitleAndType[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(type2);
    }
}