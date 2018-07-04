
package com.example.android.usnationalparkguide.Models.Park;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contacts implements Parcelable
{

    @SerializedName("phoneNumbers")
    @Expose
    private List<PhoneNumber> phoneNumbers = null;
    @SerializedName("emailAddresses")
    @Expose
    private List<EmailAddress> emailAddresses = null;
    public final static Parcelable.Creator<Contacts> CREATOR = new Creator<Contacts>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        public Contacts[] newArray(int size) {
            return (new Contacts[size]);
        }

    }
    ;

    protected Contacts(Parcel in) {
        in.readList(this.phoneNumbers, (com.example.android.usnationalparkguide.Models.Park.PhoneNumber.class.getClassLoader()));
        in.readList(this.emailAddresses, (com.example.android.usnationalparkguide.Models.Park.EmailAddress.class.getClassLoader()));
    }

    public Contacts() {
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(phoneNumbers);
        dest.writeList(emailAddresses);
    }

    public int describeContents() {
        return  0;
    }

}
