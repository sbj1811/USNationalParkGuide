package com.sjani.usnationalparkguide.Models.Trails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailDatum implements Parcelable {

    public final static Parcelable.Creator<TrailDatum> CREATOR = new Creator<TrailDatum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TrailDatum createFromParcel(Parcel in) {
            return new TrailDatum(in);
        }

        public TrailDatum[] newArray(int size) {
            return (new TrailDatum[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("stars")
    @Expose
    private Double stars;
    @SerializedName("starVotes")
    @Expose
    private Long starVotes;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("imgSqSmall")
    @Expose
    private String imgSqSmall;
    @SerializedName("imgSmall")
    @Expose
    private String imgSmall;
    @SerializedName("imgSmallMed")
    @Expose
    private String imgSmallMed;
    @SerializedName("imgMedium")
    @Expose
    private String imgMedium;
    @SerializedName("length")
    @Expose
    private Double length;
    @SerializedName("ascent")
    @Expose
    private Long ascent;
    @SerializedName("descent")
    @Expose
    private Long descent;
    @SerializedName("high")
    @Expose
    private Long high;
    @SerializedName("low")
    @Expose
    private Long low;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("conditionStatus")
    @Expose
    private String conditionStatus;
    @SerializedName("conditionDetails")
    @Expose
    private String conditionDetails;
    @SerializedName("conditionDate")
    @Expose
    private String conditionDate;

    protected TrailDatum(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.summary = ((String) in.readValue((String.class.getClassLoader())));
        this.difficulty = ((String) in.readValue((String.class.getClassLoader())));
        this.stars = ((Double) in.readValue((Long.class.getClassLoader())));
        this.starVotes = ((Long) in.readValue((Long.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.imgSqSmall = ((String) in.readValue((String.class.getClassLoader())));
        this.imgSmall = ((String) in.readValue((String.class.getClassLoader())));
        this.imgSmallMed = ((String) in.readValue((String.class.getClassLoader())));
        this.imgMedium = ((String) in.readValue((String.class.getClassLoader())));
        this.length = ((Double) in.readValue((Double.class.getClassLoader())));
        this.ascent = ((Long) in.readValue((Long.class.getClassLoader())));
        this.descent = ((Long) in.readValue((Long.class.getClassLoader())));
        this.high = ((Long) in.readValue((Long.class.getClassLoader())));
        this.low = ((Long) in.readValue((Long.class.getClassLoader())));
        this.longitude = ((Double) in.readValue((Double.class.getClassLoader())));
        this.latitude = ((Double) in.readValue((Double.class.getClassLoader())));
        this.conditionStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.conditionDetails = ((String) in.readValue((String.class.getClassLoader())));
        this.conditionDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TrailDatum() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Long getStarVotes() {
        return starVotes;
    }

    public void setStarVotes(Long starVotes) {
        this.starVotes = starVotes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgSqSmall() {
        return imgSqSmall;
    }

    public void setImgSqSmall(String imgSqSmall) {
        this.imgSqSmall = imgSqSmall;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getImgSmallMed() {
        return imgSmallMed;
    }

    public void setImgSmallMed(String imgSmallMed) {
        this.imgSmallMed = imgSmallMed;
    }

    public String getImgMedium() {
        return imgMedium;
    }

    public void setImgMedium(String imgMedium) {
        this.imgMedium = imgMedium;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Long getAscent() {
        return ascent;
    }

    public void setAscent(Long ascent) {
        this.ascent = ascent;
    }

    public Long getDescent() {
        return descent;
    }

    public void setDescent(Long descent) {
        this.descent = descent;
    }

    public Long getHigh() {
        return high;
    }

    public void setHigh(Long high) {
        this.high = high;
    }

    public Long getLow() {
        return low;
    }

    public void setLow(Long low) {
        this.low = low;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }

    public String getConditionDetails() {
        return conditionDetails;
    }

    public void setConditionDetails(String conditionDetails) {
        this.conditionDetails = conditionDetails;
    }

    public String getConditionDate() {
        return conditionDate;
    }

    public void setConditionDate(String conditionDate) {
        this.conditionDate = conditionDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(type);
        dest.writeValue(summary);
        dest.writeValue(difficulty);
        dest.writeValue(stars);
        dest.writeValue(starVotes);
        dest.writeValue(location);
        dest.writeValue(url);
        dest.writeValue(imgSqSmall);
        dest.writeValue(imgSmall);
        dest.writeValue(imgSmallMed);
        dest.writeValue(imgMedium);
        dest.writeValue(length);
        dest.writeValue(ascent);
        dest.writeValue(descent);
        dest.writeValue(high);
        dest.writeValue(low);
        dest.writeValue(longitude);
        dest.writeValue(latitude);
        dest.writeValue(conditionStatus);
        dest.writeValue(conditionDetails);
        dest.writeValue(conditionDate);
    }

    public int describeContents() {
        return 0;
    }

}
