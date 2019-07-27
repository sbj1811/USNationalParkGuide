package com.sjani.usnationalparkguide.Data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alerts")
public class AlertEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "alert_id")
    private String alert_id;
    @ColumnInfo(name = "alert_name")
    private String alert_name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "parkCode")
    private String parkCode;

    public AlertEntity() {
    }

    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    public String getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(@NonNull String alert_id) {
        this.alert_id = alert_id;
    }

    public String getAlert_name() {
        return alert_name;
    }

    public void setAlert_name(String alert_name) {
        this.alert_name = alert_name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public AlertEntity(@NonNull String alert_id, String alert_name, String description, String parkCode) {
        this.alert_id = alert_id;
        this.alert_name = alert_name;
        this.description = description;
        this.parkCode = parkCode;
    }
}
