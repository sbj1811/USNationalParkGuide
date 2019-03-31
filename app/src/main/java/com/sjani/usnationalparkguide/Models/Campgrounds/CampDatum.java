package com.sjani.usnationalparkguide.Models.Campgrounds;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CampDatum implements Parcelable {

    public final static Parcelable.Creator<CampDatum> CREATOR = new Creator<CampDatum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CampDatum createFromParcel(Parcel in) {
            return new CampDatum(in);
        }

        public CampDatum[] newArray(int size) {
            return (new CampDatum[size]);
        }

    };
    @SerializedName("regulationsurl")
    @Expose
    private String regulationsUrl;
    @SerializedName("weatheroverview")
    @Expose
    private String weatherOverview;
    @SerializedName("campsites")
    @Expose
    private Campsites campsites;
    @SerializedName("accessibility")
    @Expose
    private Accessibility accessibility;
    @SerializedName("directionsoverview")
    @Expose
    private String directionsOverview;
    @SerializedName("reservationsurl")
    @Expose
    private String reservationsUrl;
    @SerializedName("directionsurl")
    @Expose
    private String directionsUrl;
    @SerializedName("fees")
    @Expose
    private List<Fee> fees = null;
    @SerializedName("reservationssitesfirstcome")
    @Expose
    private String reservationsSitesFirstCome;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("regulationsoverview")
    @Expose
    private String regulationsOverview;
    @SerializedName("operatinghours")
    @Expose
    private List<OperatingHour> operatingHours = null;
    @SerializedName("latlong")
    @Expose
    private String latLong;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("reservationsSitesReservable")
    @Expose
    private String reservationsSitesReservable;
    @SerializedName("parkcode")
    @Expose
    private String parkCode;
    @SerializedName("amenities")
    @Expose
    private Amenities amenities;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("reservationsdescription")
    @Expose
    private String reservationsDescription;

    protected CampDatum(Parcel in) {
        this.regulationsUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.weatherOverview = ((String) in.readValue((String.class.getClassLoader())));
        this.campsites = ((Campsites) in.readValue((Campsites.class.getClassLoader())));
        this.accessibility = ((Accessibility) in.readValue((Accessibility.class.getClassLoader())));
        this.directionsOverview = ((String) in.readValue((String.class.getClassLoader())));
        this.reservationsUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.directionsUrl = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.fees, (com.sjani.usnationalparkguide.Models.Campgrounds.Fee.class.getClassLoader()));
        this.reservationsSitesFirstCome = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.regulationsOverview = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.operatingHours, (com.sjani.usnationalparkguide.Models.Campgrounds.OperatingHour.class.getClassLoader()));
        this.latLong = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.reservationsSitesReservable = ((String) in.readValue((String.class.getClassLoader())));
        this.parkCode = ((String) in.readValue((String.class.getClassLoader())));
        this.amenities = ((Amenities) in.readValue((Amenities.class.getClassLoader())));
        in.readList(this.addresses, (com.sjani.usnationalparkguide.Models.Campgrounds.Address.class.getClassLoader()));
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.reservationsDescription = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CampDatum() {
    }

    public String getRegulationsUrl() {
        return regulationsUrl;
    }

    public void setRegulationsUrl(String regulationsUrl) {
        this.regulationsUrl = regulationsUrl;
    }

    public String getWeatherOverview() {
        return weatherOverview;
    }

    public void setWeatherOverview(String weatherOverview) {
        this.weatherOverview = weatherOverview;
    }

    public Campsites getCampsites() {
        return campsites;
    }

    public void setCampsites(Campsites campsites) {
        this.campsites = campsites;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Accessibility accessibility) {
        this.accessibility = accessibility;
    }

    public String getDirectionsOverview() {
        return directionsOverview;
    }

    public void setDirectionsOverview(String directionsOverview) {
        this.directionsOverview = directionsOverview;
    }

    public String getReservationsUrl() {
        return reservationsUrl;
    }

    public void setReservationsUrl(String reservationsUrl) {
        this.reservationsUrl = reservationsUrl;
    }

    public String getDirectionsUrl() {
        return directionsUrl;
    }

    public void setDirectionsUrl(String directionsUrl) {
        this.directionsUrl = directionsUrl;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public String getReservationsSitesFirstCome() {
        return reservationsSitesFirstCome;
    }

    public void setReservationsSitesFirstCome(String reservationsSitesFirstCome) {
        this.reservationsSitesFirstCome = reservationsSitesFirstCome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegulationsOverview() {
        return regulationsOverview;
    }

    public void setRegulationsOverview(String regulationsOverview) {
        this.regulationsOverview = regulationsOverview;
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

    public String getReservationsSitesReservable() {
        return reservationsSitesReservable;
    }

    public void setReservationsSitesReservable(String reservationsSitesReservable) {
        this.reservationsSitesReservable = reservationsSitesReservable;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationsDescription() {
        return reservationsDescription;
    }

    public void setReservationsDescription(String reservationsDescription) {
        this.reservationsDescription = reservationsDescription;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(regulationsUrl);
        dest.writeValue(weatherOverview);
        dest.writeValue(campsites);
        dest.writeValue(accessibility);
        dest.writeValue(directionsOverview);
        dest.writeValue(reservationsUrl);
        dest.writeValue(directionsUrl);
        dest.writeList(fees);
        dest.writeValue(reservationsSitesFirstCome);
        dest.writeValue(name);
        dest.writeValue(regulationsOverview);
        dest.writeList(operatingHours);
        dest.writeValue(latLong);
        dest.writeValue(description);
        dest.writeValue(reservationsSitesReservable);
        dest.writeValue(parkCode);
        dest.writeValue(amenities);
        dest.writeList(addresses);
        dest.writeValue(id);
        dest.writeValue(reservationsDescription);
    }

    public int describeContents() {
        return 0;
    }

}
