
package com.sjani.usnationalparkguide.Models.Trails;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trail implements Parcelable
{

    @SerializedName("trails")
    @Expose
    private List<TrailDatum> trails = null;
    @SerializedName("success")
    @Expose
    private Long success;
    public final static Parcelable.Creator<Trail> CREATOR = new Creator<Trail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Trail createFromParcel(Parcel in) {
            return new Trail(in);
        }

        public Trail[] newArray(int size) {
            return (new Trail[size]);
        }

    }
    ;

    protected Trail(Parcel in) {
        if (in.readByte() == 0x01) {
            this.trails = new ArrayList<>();
            in.readList(this.trails, (TrailDatum.class.getClassLoader()));
        } else {
            this.trails = null;
        }
        this.success = in.readByte() == 0x00 ? null : ((Long) in.readValue((Long.class.getClassLoader())));
    }

    public Trail() {
    }

    public List<TrailDatum> getTrails() {
        return trails;
    }

    public void setTrails(List<TrailDatum> trails) {
        this.trails = trails;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (trails == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(trails);
        }
        if (success == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(success);
        }
    }

    public int describeContents() {
        return  0;
    }

}
