package com.sjani.usnationalparkguide.Data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "parks")
public class ParkEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "park_id")
    private String park_id;
    @ColumnInfo(name = "park_name")
    private String park_name;
    @ColumnInfo(name = "states")
    private String states;
    @ColumnInfo(name = "parkCode")
    private String parkCode;
    @ColumnInfo(name = "latLong")
    private String latLong;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "designation")
    private String designation;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "isFav")
    private boolean isFav;


    @Override
    public String toString() {
        return "ParkEntity{" +
                "park_id='" + park_id + '\'' +
                ", park_name='" + park_name + '\'' +
                ", states='" + states + '\'' +
                ", parkCode='" + parkCode + '\'' +
                ", latLong='" + latLong + '\'' +
                ", description='" + description + '\'' +
                ", designation='" + designation + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", isFav=" + isFav +
                '}';
    }

    public String getPark_id() {
        return park_id;
    }

    public void setPark_id(String park_id) {
        this.park_id = park_id;
    }

    public String getPark_name() {
        return park_name;
    }

    public void setPark_name(String park_name) {
        this.park_name = park_name;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
