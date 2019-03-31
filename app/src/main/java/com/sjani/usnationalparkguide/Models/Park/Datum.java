package com.sjani.usnationalparkguide.Models.Park;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Datum implements Parcelable {

    public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    };
    @SerializedName("contacts")
    @Expose
    private Contacts contacts;
    @SerializedName("states")
    @Expose
    private String states;
    @SerializedName("entranceFees")
    @Expose
    private List<EntranceFee> entranceFees = null;
    @SerializedName("directionsinfo")
    @Expose
    private String directionsInfo;
    @SerializedName("entrancePasses")
    @Expose
    private List<EntrancePass> entrancePasses = null;
    @SerializedName("directionsurl")
    @Expose
    private String directionsUrl;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("weatherinfo")
    @Expose
    private String weatherInfo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("operatinghours")
    @Expose
    private List<OperatingHour> operatingHours = null;
    @SerializedName("latlong")
    @Expose
    private String latLong;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("parkcode")
    @Expose
    private String parkCode;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fullname")
    @Expose
    private String fullName;

    protected Datum(Parcel in) {
        this.contacts = ((Contacts) in.readValue((Contacts.class.getClassLoader())));
        this.states = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.entranceFees, (com.sjani.usnationalparkguide.Models.Park.EntranceFee.class.getClassLoader()));
        this.directionsInfo = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.entrancePasses, (com.sjani.usnationalparkguide.Models.Park.EntrancePass.class.getClassLoader()));
        this.directionsUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.weatherInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.operatingHours, (com.sjani.usnationalparkguide.Models.Park.OperatingHour.class.getClassLoader()));
        this.latLong = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.images, (com.sjani.usnationalparkguide.Models.Park.Image.class.getClassLoader()));
        this.designation = ((String) in.readValue((String.class.getClassLoader())));
        this.parkCode = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.addresses, (com.sjani.usnationalparkguide.Models.Park.Address.class.getClassLoader()));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Datum() {
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public List<EntranceFee> getEntranceFees() {
        return entranceFees;
    }

    public void setEntranceFees(List<EntranceFee> entranceFees) {
        this.entranceFees = entranceFees;
    }

    public String getDirectionsInfo() {
        return directionsInfo;
    }

    public void setDirectionsInfo(String directionsInfo) {
        this.directionsInfo = directionsInfo;
    }

    public List<EntrancePass> getEntrancePasses() {
        return entrancePasses;
    }

    public void setEntrancePasses(List<EntrancePass> entrancePasses) {
        this.entrancePasses = entrancePasses;
    }

    public String getDirectionsUrl() {
        return directionsUrl;
    }

    public void setDirectionsUrl(String directionsUrl) {
        this.directionsUrl = directionsUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "contacts=" + contacts +
                ", states='" + states + '\'' +
                ", entranceFees=" + entranceFees +
                ", directionsInfo='" + directionsInfo + '\'' +
                ", entrancePasses=" + entrancePasses +
                ", directionsUrl='" + directionsUrl + '\'' +
                ", url='" + url + '\'' +
                ", weatherInfo='" + weatherInfo + '\'' +
                ", name='" + name + '\'' +
                ", operatingHours=" + operatingHours +
                ", latLong='" + latLong + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", designation='" + designation + '\'' +
                ", parkCode='" + parkCode + '\'' +
                ", addresses=" + addresses +
                ", id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OperatingHour> getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(List<OperatingHour> operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(contacts);
        dest.writeValue(states);
        dest.writeList(entranceFees);
        dest.writeValue(directionsInfo);
        dest.writeList(entrancePasses);
        dest.writeValue(directionsUrl);
        dest.writeValue(url);
        dest.writeValue(weatherInfo);
        dest.writeValue(name);
        dest.writeList(operatingHours);
        dest.writeValue(latLong);
        dest.writeValue(description);
        dest.writeList(images);
        dest.writeValue(designation);
        dest.writeValue(parkCode);
        dest.writeList(addresses);
        dest.writeValue(id);
        dest.writeValue(fullName);
    }

    public int describeContents() {
        return 0;
    }

}
