
package com.sjani.usnationalparkguide.Models.Park;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable
{

    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("altText")
    @Expose
    private String altText;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("url")
    @Expose
    private String url;
    public final static Parcelable.Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
    ;

    protected Image(Parcel in) {
        this.credit = ((String) in.readValue((String.class.getClassLoader())));
        this.altText = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.caption = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Image() {
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(credit);
        dest.writeValue(altText);
        dest.writeValue(title);
        dest.writeValue(id);
        dest.writeValue(caption);
        dest.writeValue(url);
    }

    public int describeContents() {
        return  0;
    }

}
