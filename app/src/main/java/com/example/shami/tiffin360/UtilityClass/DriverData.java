package com.example.shami.tiffin360.UtilityClass;

/**
 * Created by Shami on 9/23/2017.
 */
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shami on 8/18/2017.
 */

public class DriverData implements Parcelable {

    private String Driver_Id;
    private String Driver_Name;
    private String Driver_Lat;
    private String Driver_Long;


    public DriverData(){}

    public DriverData(String Driver_Id, String Driver_Name, String Driver_Lat, String Driver_Long) {
        this.Driver_Id = Driver_Id;
        this .Driver_Name = Driver_Name;
        this.Driver_Lat = Driver_Lat;
        this.Driver_Long = Driver_Long;
    }

    public String getDriver_Id() {
        return Driver_Id;
    }

    public void setDriver_Id(String Driver_Id) {
        this.Driver_Id = Driver_Id;
    }

    public String getDriver_Name() {
        return Driver_Name;
    }

    public void setDriver_Name(String Driver_Name) {
        this.Driver_Name = Driver_Name;
    }

    public String getDriver_Lat() {
        return Driver_Lat;
    }

    public void setDriver_Lat(String Driver_Lat) {
        this.Driver_Lat = Driver_Lat;
    }

    public String getDriver_Long() {
        return Driver_Long;
    }

    public void setDriver_Long(String Driver_Long) {
        this.Driver_Long = Driver_Long;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Driver_Id);
        dest.writeString(this.Driver_Name);
        dest.writeString(this.Driver_Lat);
        dest.writeString(this.Driver_Long);
    }

    protected DriverData(Parcel in) {
        this.Driver_Id = in.readString();
        this.Driver_Name = in.readString();
        this.Driver_Lat = in.readString();
        this.Driver_Long = in.readString();
    }

    public static final Parcelable.Creator<DriverData> CREATOR = new Parcelable.Creator<DriverData>() {
        @Override
        public DriverData createFromParcel(Parcel source) {
            return new DriverData(source);
        }

        @Override
        public DriverData[] newArray(int size) {
            return new DriverData[size];
        }
    };
}
