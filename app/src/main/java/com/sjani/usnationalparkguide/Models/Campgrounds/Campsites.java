package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Campsites implements Parcelable {

    public final static Parcelable.Creator<Campsites> CREATOR = new Creator<Campsites>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Campsites createFromParcel(Parcel in) {
            return new Campsites(in);
        }

        public Campsites[] newArray(int size) {
            return (new Campsites[size]);
        }

    };
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("horse")
    @Expose
    private String horse;
    @SerializedName("totalsites")
    @Expose
    private String totalSites;
    @SerializedName("tentonly")
    @Expose
    private String tentOnly;
    @SerializedName("electricalhookups")
    @Expose
    private String electricalHookups;
    @SerializedName("rvonly")
    @Expose
    private String rvOnly;
    @SerializedName("walkboatto")
    @Expose
    private String walkBoatTo;

    protected Campsites(Parcel in) {
        this.other = ((String) in.readValue((String.class.getClassLoader())));
        this.group = ((String) in.readValue((String.class.getClassLoader())));
        this.horse = ((String) in.readValue((String.class.getClassLoader())));
        this.totalSites = ((String) in.readValue((String.class.getClassLoader())));
        this.tentOnly = ((String) in.readValue((String.class.getClassLoader())));
        this.electricalHookups = ((String) in.readValue((String.class.getClassLoader())));
        this.rvOnly = ((String) in.readValue((String.class.getClassLoader())));
        this.walkBoatTo = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Campsites() {
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHorse() {
        return horse;
    }

    public void setHorse(String horse) {
        this.horse = horse;
    }

    public String getTotalSites() {
        return totalSites;
    }

    public void setTotalSites(String totalSites) {
        this.totalSites = totalSites;
    }

    public String getTentOnly() {
        return tentOnly;
    }

    public void setTentOnly(String tentOnly) {
        this.tentOnly = tentOnly;
    }

    public String getElectricalHookups() {
        return electricalHookups;
    }

    public void setElectricalHookups(String electricalHookups) {
        this.electricalHookups = electricalHookups;
    }

    public String getRvOnly() {
        return rvOnly;
    }

    public void setRvOnly(String rvOnly) {
        this.rvOnly = rvOnly;
    }

    public String getWalkBoatTo() {
        return walkBoatTo;
    }

    public void setWalkBoatTo(String walkBoatTo) {
        this.walkBoatTo = walkBoatTo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(other);
        dest.writeValue(group);
        dest.writeValue(horse);
        dest.writeValue(totalSites);
        dest.writeValue(tentOnly);
        dest.writeValue(electricalHookups);
        dest.writeValue(rvOnly);
        dest.writeValue(walkBoatTo);
    }

    public int describeContents() {
        return 0;
    }

}
