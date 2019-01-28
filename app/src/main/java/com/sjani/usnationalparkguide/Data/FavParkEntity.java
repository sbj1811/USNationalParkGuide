package com.sjani.usnationalparkguide.Data;

import androidx.room.Entity;

@Entity(tableName = "favorites")
public class FavParkEntity extends ParkEntity {

    public FavParkEntity() {
    }

    public FavParkEntity(ParkEntity parkEntity) {
        this.setPark_id(parkEntity.getPark_id());
        this.setPark_name(parkEntity.getPark_name());
        this.setStates(parkEntity.getStates());
        this.setParkCode(parkEntity.getParkCode());
        this.setLatLong(parkEntity.getLatLong());
        this.setDescription(parkEntity.getDescription());
        this.setDesignation(parkEntity.getDesignation());
        this.setAddress(parkEntity.getAddress());
        this.setPhone(parkEntity.getPhone());
        this.setEmail(parkEntity.getEmail());
        this.setImage(parkEntity.getImage());
    }

}
