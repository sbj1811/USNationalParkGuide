
package com.sjani.usnationalparkguide.Models.Park;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parks implements Parcelable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("limit")
    @Expose
    private Double limit;
    @SerializedName("start")
    @Expose
    private Double start;
    public final static Parcelable.Creator<Parks> CREATOR = new Creator<Parks>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Parks createFromParcel(Parcel in) {
            return new Parks(in);
        }

        public Parks[] newArray(int size) {
            return (new Parks[size]);
        }

    }
    ;

    protected Parks(Parcel in) {
        this.total = in.readByte() == 0x00 ? null : ((Integer) in.readValue((Integer.class.getClassLoader())));
        if (in.readByte() == 0x01) {
            this.data = new ArrayList<>();
            in.readList(this.data, (com.sjani.usnationalparkguide.Models.Park.Datum.class.getClassLoader()));
        } else {
            this.data = null;
        }
        this.limit = in.readByte() == 0x00 ? null : ((Double) in.readValue((Double.class.getClassLoader())));
        this.start = in.readByte() == 0x00 ? null : ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Parks() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
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
            dest.writeInt(total);
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
