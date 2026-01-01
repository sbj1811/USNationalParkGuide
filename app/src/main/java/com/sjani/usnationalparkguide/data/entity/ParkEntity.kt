package com.sjani.usnationalparkguide.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parks")
data class ParkEntity(
    @PrimaryKey
    @ColumnInfo(name = "park_id")
    val parkId: String,
    
    @ColumnInfo(name = "park_name")
    val parkName: String = "",
    
    @ColumnInfo(name = "states")
    val states: String = "",
    
    @ColumnInfo(name = "parkCode")
    val parkCode: String = "",
    
    @ColumnInfo(name = "latLong")
    val latLong: String = "",
    
    @ColumnInfo(name = "description")
    val description: String = "",
    
    @ColumnInfo(name = "designation")
    val designation: String = "",
    
    @ColumnInfo(name = "address")
    val address: String = "",
    
    @ColumnInfo(name = "phone")
    val phone: String = "",
    
    @ColumnInfo(name = "email")
    val email: String = "",
    
    @ColumnInfo(name = "image")
    val image: String = "",
    
    @ColumnInfo(name = "isFav")
    val isFav: Boolean = false
)

@Entity(tableName = "fav_parks")
data class FavParkEntity(
    @PrimaryKey
    @ColumnInfo(name = "park_id")
    val parkId: String,
    
    @ColumnInfo(name = "park_name")
    val parkName: String = "",
    
    @ColumnInfo(name = "states")
    val states: String = "",
    
    @ColumnInfo(name = "parkCode")
    val parkCode: String = "",
    
    @ColumnInfo(name = "latLong")
    val latLong: String = "",
    
    @ColumnInfo(name = "description")
    val description: String = "",
    
    @ColumnInfo(name = "designation")
    val designation: String = "",
    
    @ColumnInfo(name = "address")
    val address: String = "",
    
    @ColumnInfo(name = "phone")
    val phone: String = "",
    
    @ColumnInfo(name = "email")
    val email: String = "",
    
    @ColumnInfo(name = "image")
    val image: String = ""
) {
    companion object {
        fun fromParkEntity(park: ParkEntity): FavParkEntity {
            return FavParkEntity(
                parkId = park.parkId,
                parkName = park.parkName,
                states = park.states,
                parkCode = park.parkCode,
                latLong = park.latLong,
                description = park.description,
                designation = park.designation,
                address = park.address,
                phone = park.phone,
                email = park.email,
                image = park.image
            )
        }
    }
}

@Entity(tableName = "trails")
data class TrailEntity(
    @PrimaryKey
    @ColumnInfo(name = "trail_id")
    val trailId: String,
    
    @ColumnInfo(name = "trail_name")
    val trailName: String = "",
    
    @ColumnInfo(name = "summary")
    val summary: String = "",
    
    @ColumnInfo(name = "difficulty")
    val difficulty: String = "",
    
    @ColumnInfo(name = "image_small")
    val imageSmall: String = "",
    
    @ColumnInfo(name = "image_med")
    val imageMed: String = "",
    
    @ColumnInfo(name = "length")
    val length: String = "",
    
    @ColumnInfo(name = "ascent")
    val ascent: String = "",
    
    @ColumnInfo(name = "latitude")
    val latitude: String = "",
    
    @ColumnInfo(name = "longitude")
    val longitude: String = "",
    
    @ColumnInfo(name = "location")
    val location: String = "",
    
    @ColumnInfo(name = "condition")
    val condition: String = "",
    
    @ColumnInfo(name = "more_info")
    val moreInfo: String = ""
)

@Entity(tableName = "camps")
data class CampEntity(
    @PrimaryKey
    @ColumnInfo(name = "camp_id")
    val campId: String,
    
    @ColumnInfo(name = "camp_name")
    val campName: String = "",
    
    @ColumnInfo(name = "description")
    val description: String = "",
    
    @ColumnInfo(name = "parkCode")
    val parkCode: String = "",
    
    @ColumnInfo(name = "address")
    val address: String = "",
    
    @ColumnInfo(name = "latLong")
    val latLong: String = "",
    
    @ColumnInfo(name = "cellPhoneReception")
    val cellPhoneReception: String = "",
    
    @ColumnInfo(name = "showers")
    val showers: String = "",
    
    @ColumnInfo(name = "internetConnectivity")
    val internetConnectivity: String = "",
    
    @ColumnInfo(name = "toilets")
    val toilets: String = "",
    
    @ColumnInfo(name = "wheelchairAccess")
    val wheelchairAccess: String = "",
    
    @ColumnInfo(name = "reservationsUrl")
    val reservationsUrl: String = "",
    
    @ColumnInfo(name = "directionsUrl")
    val directionsUrl: String = ""
)

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey
    @ColumnInfo(name = "alert_id")
    val alertId: String,
    
    @ColumnInfo(name = "alert_name")
    val alertName: String = "",
    
    @ColumnInfo(name = "description")
    val description: String = "",
    
    @ColumnInfo(name = "category")
    val category: String = "",
    
    @ColumnInfo(name = "parkCode")
    val parkCode: String = ""
)
