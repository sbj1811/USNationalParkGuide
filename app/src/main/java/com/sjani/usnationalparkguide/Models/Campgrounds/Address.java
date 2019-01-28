package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    public final static Parcelable.Creator<Address> CREATOR = new Creator<Address>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return (new Address[size]);
        }

    };
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;
    @SerializedName("line1")
    @Expose
    private String line1;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("line3")
    @Expose
    private String line3;
    @SerializedName("line2")
    @Expose
    private String line2;

    protected Address(Parcel in) {
        this.postalCode = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.stateCode = ((String) in.readValue((String.class.getClassLoader())));
        this.line1 = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.line3 = ((String) in.readValue((String.class.getClassLoader())));
        this.line2 = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Address() {
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(postalCode);
        dest.writeValue(city);
        dest.writeValue(stateCode);
        dest.writeValue(line1);
        dest.writeValue(type);
        dest.writeValue(line3);
        dest.writeValue(line2);
    }

    public int describeContents() {
        return 0;
    }

}
