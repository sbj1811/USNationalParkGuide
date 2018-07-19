
package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Campsites implements Parcelable
{

    @SerializedName("other")
    @Expose
    private Double other;
    @SerializedName("group")
    @Expose
    private Double group;
    @SerializedName("horse")
    @Expose
    private Double horse;
    @SerializedName("totalSites")
    @Expose
    private Double totalSites;
    @SerializedName("tentOnly")
    @Expose
    private Double tentOnly;
    @SerializedName("electricalHookups")
    @Expose
    private Double electricalHookups;
    @SerializedName("rvOnly")
    @Expose
    private Double rvOnly;
    @SerializedName("walkBoatTo")
    @Expose
    private Double walkBoatTo;
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

    }
    ;

    protected Campsites(Parcel in) {
        this.other = ((Double) in.readValue((Double.class.getClassLoader())));
        this.group = ((Double) in.readValue((Double.class.getClassLoader())));
        this.horse = ((Double) in.readValue((Double.class.getClassLoader())));
        this.totalSites = ((Double) in.readValue((Double.class.getClassLoader())));
        this.tentOnly = ((Double) in.readValue((Double.class.getClassLoader())));
        this.electricalHookups = ((Double) in.readValue((Double.class.getClassLoader())));
        this.rvOnly = ((Double) in.readValue((Double.class.getClassLoader())));
        this.walkBoatTo = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Campsites() {
    }

    public Double getOther() {
        return other;
    }

    public void setOther(Double other) {
        this.other = other;
    }

    public Double getGroup() {
        return group;
    }

    public void setGroup(Double group) {
        this.group = group;
    }

    public Double getHorse() {
        return horse;
    }

    public void setHorse(Double horse) {
        this.horse = horse;
    }

    public Double getTotalSites() {
        return totalSites;
    }

    public void setTotalSites(Double totalSites) {
        this.totalSites = totalSites;
    }

    public Double getTentOnly() {
        return tentOnly;
    }

    public void setTentOnly(Double tentOnly) {
        this.tentOnly = tentOnly;
    }

    public Double getElectricalHookups() {
        return electricalHookups;
    }

    public void setElectricalHookups(Double electricalHookups) {
        this.electricalHookups = electricalHookups;
    }

    public Double getRvOnly() {
        return rvOnly;
    }

    public void setRvOnly(Double rvOnly) {
        this.rvOnly = rvOnly;
    }

    public Double getWalkBoatTo() {
        return walkBoatTo;
    }

    public void setWalkBoatTo(Double walkBoatTo) {
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
        return  0;
    }

}
