
package com.example.android.usnationalparkguide.Models.Campgrounds;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Campground implements Parcelable
{

    @SerializedName("total")
    @Expose
    private Long total;
    @SerializedName("data")
    @Expose
    private List<CampDatum> data = null;
    @SerializedName("limit")
    @Expose
    private Double limit;
    @SerializedName("start")
    @Expose
    private Double start;
    public final static Parcelable.Creator<Campground> CREATOR = new Creator<Campground>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Campground createFromParcel(Parcel in) {
            return new Campground(in);
        }

        public Campground[] newArray(int size) {
            return (new Campground[size]);
        }

    }
    ;

    protected Campground(Parcel in) {
        this.total = in.readByte() == 0x00 ? null : ((Long) in.readValue((Long.class.getClassLoader())));
        if (in.readByte() == 0x01) {
            this.data = new ArrayList<>();
            in.readList(this.data, (CampDatum.class.getClassLoader()));
        } else {
            this.data = null;
        }       
        this.limit = in.readByte() == 0x00 ? null : ((Double) in.readValue((Double.class.getClassLoader())));
        this.start = in.readByte() == 0x00 ? null : ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Campground() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<CampDatum> getData() {
        return data;
    }

    public void setData(List<CampDatum> data) {
        this.data = data;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(total);
        }
        if (data == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
        if (limit == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(limit);
        }
        if (start == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(start);
        }
    }

    public int describeContents() {
        return  0;
    }

}
