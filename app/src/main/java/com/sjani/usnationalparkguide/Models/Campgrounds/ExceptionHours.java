package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExceptionHours implements Parcelable {

    public final static Parcelable.Creator<ExceptionHours> CREATOR = new Creator<ExceptionHours>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ExceptionHours createFromParcel(Parcel in) {
            return new ExceptionHours(in);
        }

        public ExceptionHours[] newArray(int size) {
            return (new ExceptionHours[size]);
        }

    };
    @SerializedName("wednesday")
    @Expose
    private String wednesday;
    @SerializedName("monday")
    @Expose
    private String monday;
    @SerializedName("thursday")
    @Expose
    private String thursday;
    @SerializedName("sunday")
    @Expose
    private String sunday;
    @SerializedName("tuesday")
    @Expose
    private String tuesday;
    @SerializedName("friday")
    @Expose
    private String friday;
    @SerializedName("saturday")
    @Expose
    private String saturday;

    protected ExceptionHours(Parcel in) {
        this.wednesday = ((String) in.readValue((String.class.getClassLoader())));
        this.monday = ((String) in.readValue((String.class.getClassLoader())));
        this.thursday = ((String) in.readValue((String.class.getClassLoader())));
        this.sunday = ((String) in.readValue((String.class.getClassLoader())));
        this.tuesday = ((String) in.readValue((String.class.getClassLoader())));
        this.friday = ((String) in.readValue((String.class.getClassLoader())));
        this.saturday = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ExceptionHours() {
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(wednesday);
        dest.writeValue(monday);
        dest.writeValue(thursday);
        dest.writeValue(sunday);
        dest.writeValue(tuesday);
        dest.writeValue(friday);
        dest.writeValue(saturday);
    }

    public int describeContents() {
        return 0;
    }

}
