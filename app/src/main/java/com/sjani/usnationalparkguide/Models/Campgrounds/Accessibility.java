package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Accessibility implements Parcelable {

    public final static Parcelable.Creator<Accessibility> CREATOR = new Creator<Accessibility>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Accessibility createFromParcel(Parcel in) {
            return new Accessibility(in);
        }

        public Accessibility[] newArray(int size) {
            return (new Accessibility[size]);
        }

    };
    @SerializedName("wheelchairaccess")
    @Expose
    private String wheelchairAccess;
    @SerializedName("internetinfo")
    @Expose
    private String internetInfo;
    @SerializedName("rvallowed")
    @Expose
    private Double rvAllowed;
    @SerializedName("cellphoneinfo")
    @Expose
    private String cellPhoneInfo;
    @SerializedName("firestovepolicy")
    @Expose
    private String fireStovePolicy;
    @SerializedName("rvmaxlength")
    @Expose
    private Double rvMaxLength;
    @SerializedName("additionalinfo")
    @Expose
    private String additionalInfo;
    @SerializedName("trailermaxlength")
    @Expose
    private Double trailerMaxLength;
    @SerializedName("adainfo")
    @Expose
    private String adaInfo;
    @SerializedName("rvinfo")
    @Expose
    private String rvInfo;
    @SerializedName("accessroads")
    @Expose
    private List<String> accessRoads = null;
    @SerializedName("trailerallowed")
    @Expose
    private Double trailerAllowed;
    @SerializedName("classifications")
    @Expose
    private List<String> classifications = null;

    protected Accessibility(Parcel in) {
        this.wheelchairAccess = ((String) in.readValue((String.class.getClassLoader())));
        this.internetInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.rvAllowed = ((Double) in.readValue((Double.class.getClassLoader())));
        this.cellPhoneInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.fireStovePolicy = ((String) in.readValue((String.class.getClassLoader())));
        this.rvMaxLength = ((Double) in.readValue((Double.class.getClassLoader())));
        this.additionalInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.trailerMaxLength = ((Double) in.readValue((Double.class.getClassLoader())));
        this.adaInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.rvInfo = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.accessRoads, (java.lang.String.class.getClassLoader()));
        this.trailerAllowed = ((Double) in.readValue((Double.class.getClassLoader())));
        in.readList(this.classifications, (java.lang.String.class.getClassLoader()));
    }

    public Accessibility() {
    }

    public String getWheelchairAccess() {
        return wheelchairAccess;
    }

    public void setWheelchairAccess(String wheelchairAccess) {
        this.wheelchairAccess = wheelchairAccess;
    }

    public String getInternetInfo() {
        return internetInfo;
    }

    public void setInternetInfo(String internetInfo) {
        this.internetInfo = internetInfo;
    }

    public Double getRvAllowed() {
        return rvAllowed;
    }

    public void setRvAllowed(Double rvAllowed) {
        this.rvAllowed = rvAllowed;
    }

    public String getCellPhoneInfo() {
        return cellPhoneInfo;
    }

    public void setCellPhoneInfo(String cellPhoneInfo) {
        this.cellPhoneInfo = cellPhoneInfo;
    }

    public String getFireStovePolicy() {
        return fireStovePolicy;
    }

    public void setFireStovePolicy(String fireStovePolicy) {
        this.fireStovePolicy = fireStovePolicy;
    }

    public Double getRvMaxLength() {
        return rvMaxLength;
    }

    public void setRvMaxLength(Double rvMaxLength) {
        this.rvMaxLength = rvMaxLength;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Double getTrailerMaxLength() {
        return trailerMaxLength;
    }

    public void setTrailerMaxLength(Double trailerMaxLength) {
        this.trailerMaxLength = trailerMaxLength;
    }

    public String getAdaInfo() {
        return adaInfo;
    }

    public void setAdaInfo(String adaInfo) {
        this.adaInfo = adaInfo;
    }

    public String getRvInfo() {
        return rvInfo;
    }

    public void setRvInfo(String rvInfo) {
        this.rvInfo = rvInfo;
    }

    public List<String> getAccessRoads() {
        return accessRoads;
    }

    public void setAccessRoads(List<String> accessRoads) {
        this.accessRoads = accessRoads;
    }

    public Double getTrailerAllowed() {
        return trailerAllowed;
    }

    public void setTrailerAllowed(Double trailerAllowed) {
        this.trailerAllowed = trailerAllowed;
    }

    public List<String> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<String> classifications) {
        this.classifications = classifications;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(wheelchairAccess);
        dest.writeValue(internetInfo);
        dest.writeValue(rvAllowed);
        dest.writeValue(cellPhoneInfo);
        dest.writeValue(fireStovePolicy);
        dest.writeValue(rvMaxLength);
        dest.writeValue(additionalInfo);
        dest.writeValue(trailerMaxLength);
        dest.writeValue(adaInfo);
        dest.writeValue(rvInfo);
        dest.writeList(accessRoads);
        dest.writeValue(trailerAllowed);
        dest.writeList(classifications);
    }

    public int describeContents() {
        return 0;
    }

}
