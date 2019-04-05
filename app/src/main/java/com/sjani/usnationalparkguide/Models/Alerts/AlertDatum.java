package com.sjani.usnationalparkguide.Models.Alerts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlertDatum implements Parcelable {

    public final static Parcelable.Creator<AlertDatum> CREATOR = new Creator<AlertDatum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public AlertDatum createFromParcel(Parcel in) {
            return new AlertDatum(in);
        }

        public AlertDatum[] newArray(int size) {
            return (new AlertDatum[size]);
        }

    };
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("parkCode")
    @Expose
    private String parkCode;

    protected AlertDatum(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.parkCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public AlertDatum() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(id);
        dest.writeValue(description);
        dest.writeValue(category);
        dest.writeValue(url);
        dest.writeValue(parkCode);
    }

    public int describeContents() {
        return 0;
    }

}
