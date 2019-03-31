package com.sjani.usnationalparkguide.Models.Park;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailAddress implements Parcelable {

    public final static Parcelable.Creator<EmailAddress> CREATOR = new Creator<EmailAddress>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EmailAddress createFromParcel(Parcel in) {
            return new EmailAddress(in);
        }

        public EmailAddress[] newArray(int size) {
            return (new EmailAddress[size]);
        }

    };
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("emailaddress")
    @Expose
    private String emailAddress;

    protected EmailAddress(Parcel in) {
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.emailAddress = ((String) in.readValue((String.class.getClassLoader())));
    }

    public EmailAddress() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(description);
        dest.writeValue(emailAddress);
    }

    public int describeContents() {
        return 0;
    }

}
