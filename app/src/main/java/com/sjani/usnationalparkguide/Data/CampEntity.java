package com.sjani.usnationalparkguide.Data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "camps")
public class CampEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "camp_id")
    private String camp_id;
    @ColumnInfo(name = "camp_name")
    private String camp_name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "parkCode")
    private String parkCode;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "latLong")
    private String latLong;
    @ColumnInfo(name = "cellPhoneReception")
    private String cellPhoneReception;
    @ColumnInfo(name = "showers")
    private String showers;
    @ColumnInfo(name = "internetConnectivity")
    private String internetConnectivity;
    @ColumnInfo(name = "toilets")
    private String toilets;
    @ColumnInfo(name = "wheelchairAccess")
    private String wheelchairAccess;
    @ColumnInfo(name = "reservationsUrl")
    private String reservationsUrl;
    @ColumnInfo(name = "directionsUrl")
    private String directionsUrl;

    @NonNull
    public String getCamp_id() {
        return camp_id;
    }

    public void setCamp_id(@NonNull String camp_id) {
        this.camp_id = camp_id;
    }

    public String getCamp_name() {
        return camp_name;
    }

    public void setCamp_name(String camp_name) {
        this.camp_name = camp_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getCellPhoneReception() {
        return cellPhoneReception;
    }

    public void setCellPhoneReception(String cellPhoneReception) {
        this.cellPhoneReception = cellPhoneReception;
    }

    public String getShowers() {
        return showers;
    }

    public void setShowers(String showers) {
        this.showers = showers;
    }

    public String getInternetConnectivity() {
        return internetConnectivity;
    }

    public void setInternetConnectivity(String internetConnectivity) {
        this.internetConnectivity = internetConnectivity;
    }

    public String getToilets() {
        return toilets;
    }

    public void setToilets(String toilets) {
        this.toilets = toilets;
    }

    public String getWheelchairAccess() {
        return wheelchairAccess;
    }

    public void setWheelchairAccess(String wheelchairAccess) {
        this.wheelchairAccess = wheelchairAccess;
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
}
