package com.example.shami.tiffin360.Data_Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shami on 9/11/2017.
 */

public class Days_Data implements Parcelable {

    private final String mDay;
    private final String mPrice;
    private final String mCode;
    private int mCount;
    private boolean mFlag;


    public Days_Data(String mDay, String mPrice, String mCode, int mCount, boolean mFlag) {
        this.mDay = mDay;
        this.mPrice = mPrice;
        this.mCode = mCode;
        this.mCount = mCount;
        this.mFlag = mFlag;
    }

    public String getmDay() {
        return mDay;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmCode() {
        return mCode;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public boolean ismFlag() {
        return mFlag;
    }

    public void setmFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDay);
        dest.writeString(this.mPrice);
        dest.writeString(this.mCode);
        dest.writeInt(this.mCount);
        dest.writeByte(this.mFlag ? (byte) 1 : (byte) 0);
    }

    protected Days_Data(Parcel in) {
        this.mDay = in.readString();
        this.mPrice = in.readString();
        this.mCode = in.readString();
        this.mCount = in.readInt();
        this.mFlag = in.readByte() != 0;
    }

    public static final Creator<Days_Data> CREATOR = new Creator<Days_Data>() {
        @Override
        public Days_Data createFromParcel(Parcel source) {
            return new Days_Data(source);
        }

        @Override
        public Days_Data[] newArray(int size) {
            return new Days_Data[size];
        }
    };
}
