package com.sjani.usnationalparkguide.Models.Alerts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Alert implements Parcelable {

    public final static Parcelable.Creator<Alert> CREATOR = new Creator<Alert>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Alert createFromParcel(Parcel in) {
            return new Alert(in);
        }

        public Alert[] newArray(int size) {
            return (new Alert[size]);
        }

    };
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private List<AlertDatum> data = null;
    @SerializedName("limit")
    @Expose
    private Double limit;
    @SerializedName("start")
    @Expose
    private Double start;

    protected Alert(Parcel in) {
        this.total = in.readByte() == 0x00 ? null : ((Integer) in.readValue((Integer.class.getClassLoader())));
        if (in.readByte() == 0x01) {
            this.data = new ArrayList<>();
            in.readList(this.data, (AlertDatum.class.getClassLoader()));
        } else {
            this.data = null;
        }
        this.limit = in.readByte() == 0x00 ? null : ((Double) in.readValue((Double.class.getClassLoader())));
        this.start = in.readByte() == 0x00 ? null : ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Alert() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<AlertDatum> getData() {
        return data;
    }

    public void setData(List<AlertDatum> data) {
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
        if (total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
        if (total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(limit);
        }
        if (total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeValue(start);
        }
    }

    public int describeContents() {
        return 0;
    }

}
