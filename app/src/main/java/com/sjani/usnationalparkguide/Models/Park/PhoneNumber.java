package com.sjani.usnationalparkguide.Models.Park;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneNumber implements Parcelable {

    public final static Parcelable.Creator<PhoneNumber> CREATOR = new Creator<PhoneNumber>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PhoneNumber createFromParcel(Parcel in) {
            return new PhoneNumber(in);
        }

        public PhoneNumber[] newArray(int size) {
            return (new PhoneNumber[size]);
        }

    };
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("type")
    @Expose
    private String type;

    protected PhoneNumber(Parcel in) {
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.extension = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PhoneNumber() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(phoneNumber);
        dest.writeValue(description);
        dest.writeValue(extension);
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }

}
