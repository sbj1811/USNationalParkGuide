package com.sjani.usnationalparkguide.Data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trails")
public class TrailEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "trail_id")
    private String trail_id;
    @ColumnInfo(name = "trail_name")
    private String trail_name;
    @ColumnInfo(name = "summary")
    private String summary;
    @ColumnInfo(name = "difficulty")
    private String difficulty;
    @ColumnInfo(name = "image_small")
    private String image_small;
    @ColumnInfo(name = "image_med")
    private String image_med;
    @ColumnInfo(name = "length")
    private String length;
    @ColumnInfo(name = "ascent")
    private String ascent;
    @ColumnInfo(name = "latitude")
    private String latitude;
    @ColumnInfo(name = "longitude")
    private String longitude;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "condition")
    private String condition;
    @ColumnInfo(name = "more_info")
    private String more_info;

    @NonNull
    public String getTrail_id() {
        return trail_id;
    }

    public void setTrail_id(@NonNull String trail_id) {
        this.trail_id = trail_id;
    }

    public String getTrail_name() {
        return trail_name;
    }

    public void setTrail_name(String trail_name) {
        this.trail_name = trail_name;
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

    public String getImage_small() {
        return image_small;
    }

    public void setImage_small(String image_small) {
        this.image_small = image_small;
    }

    public String getImage_med() {
        return image_med;
    }

    public void setImage_med(String image_med) {
        this.image_med = image_med;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getAscent() {
        return ascent;
    }

    public void setAscent(String ascent) {
        this.ascent = ascent;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMore_info() {
        return more_info;
    }

    public void setMore_info(String more_info) {
        this.more_info = more_info;
    }
}
