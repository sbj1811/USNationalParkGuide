package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fee implements Parcelable {

    public final static Parcelable.Creator<Fee> CREATOR = new Creator<Fee>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Fee createFromParcel(Parcel in) {
            return new Fee(in);
        }

        public Fee[] newArray(int size) {
            return (new Fee[size]);
        }

    };
    @SerializedName("cost")
    @Expose
    private Double cost;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private String title;

    protected Fee(Parcel in) {
        this.cost = ((Double) in.readValue((Double.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Fee() {
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cost);
        dest.writeValue(description);
        dest.writeValue(title);
    }

    public int describeContents() {
        return 0;
    }

}
