package com.sjani.usnationalparkguide.data.repository

import android.util.Log
import com.sjani.usnationalparkguide.data.dao.*
import com.sjani.usnationalparkguide.data.entity.*
import com.sjani.usnationalparkguide.data.model.*
import com.sjani.usnationalparkguide.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ParkRepository(
    private val parkDao: ParkDao,
    private val favDao: FavDao,
    private val trailDao: TrailDao,
    private val campDao: CampDao,
    private val alertDao: AlertDao,
    private val npsApiService: NPSApiService,
    private val hikingProjectApiService: HikingProjectApiService,
    private val weatherApiService: WeatherApiService
) {
    suspend fun refreshParks(apiKey: String, fields: String, state: String, maxResults: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = npsApiService.getParks(state, apiKey, fields, maxResults)
                parkDao.clearTable()
                parkDao.saveAll(response.data.map { park -> park.toEntity() })
            } catch (e: Exception) {
                Log.e("ParkRepository", "Error fetching parks", e)
            }
        }
    }
    
    fun getAllParks(): Flow<List<ParkEntity>> = parkDao.getAllParks()
    fun getPark(parkCode: String): Flow<ParkEntity?> = parkDao.getPark(parkCode)
    
    suspend fun clearParks() = withContext(Dispatchers.IO) { parkDao.clearTable() }
    
    suspend fun refreshTrails(apiKey: String, latLong: String) {
        withContext(Dispatchers.IO) {
            try {
                val coords = parseLatLong(latLong) ?: return@withContext
                val response = hikingProjectApiService.getTrails(coords.first, coords.second, apiKey)
                trailDao.clearTable()
                trailDao.saveAll(response.trails.map { trail -> trail.toEntity() })
            } catch (e: Exception) {
                Log.e("ParkRepository", "Error fetching trails", e)
            }
        }
    }
    
    fun getAllTrails(): Flow<List<TrailEntity>> = trailDao.getAllTrails()
    fun getTrail(trailId: String): Flow<TrailEntity?> = trailDao.getTrail(trailId)
    
    suspend fun refreshCampgrounds(parkCode: String, fields: String, apiKey: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = npsApiService.getCampgrounds(parkCode, apiKey, fields)
                campDao.clearTable()
                campDao.saveAll(response.data.map { camp -> camp.toEntity() })
            } catch (e: Exception) {
                Log.e("ParkRepository", "Error fetching camps", e)
            }
        }
    }
    
    fun getAllCampgrounds(): Flow<List<CampEntity>> = campDao.getAllCamps()
    fun getCampground(campId: String): Flow<CampEntity?> = campDao.getCamp(campId)
    
    suspend fun refreshAlerts(parkCode: String, apiKey: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = npsApiService.getAlerts(parkCode, apiKey)
                alertDao.clearTable()
                alertDao.saveAll(response.data.map { alert -> alert.toEntity() })
            } catch (e: Exception) {
                Log.e("ParkRepository", "Error fetching alerts", e)
            }
        }
    }
    
    fun getAllAlerts(): Flow<List<AlertEntity>> = alertDao.getAllAlerts()
    
    suspend fun getCurrentWeather(latLong: String, apiKey: String): CurrentWeather? {
        return withContext(Dispatchers.IO) {
            try {
                val coords = parseLatLong(latLong) ?: return@withContext null
                weatherApiService.getCurrentWeather(coords.first, coords.second, apiKey)
            } catch (e: Exception) {
                Log.e("ParkRepository", "Error fetching weather", e)
                null
            }
        }
    }
    
    fun getAllFavorites(): Flow<List<FavParkEntity>> = favDao.getAllFavorites()
    
    fun isFavorite(parkId: String): Flow<Boolean> = favDao.isFavorite(parkId)
    
    suspend fun addFavorite(favParkEntity: FavParkEntity) {
        withContext(Dispatchers.IO) {
            favDao.save(favParkEntity)
        }
    }
    
    suspend fun removeFavorite(parkId: String) {
        withContext(Dispatchers.IO) {
            favDao.deleteById(parkId)
        }
    }
    
    suspend fun clearAllFavorites() = withContext(Dispatchers.IO) { favDao.clearTable() }
    
    /**
     * Parses latLong string from NPS API (format: "lat:38.72261844, long:-109.5863666")
     * Uses regex to extract only numeric values, avoiding issues with colons leaking into coordinates.
     */
    private fun parseLatLong(latLong: String): Pair<String, String>? {
        return try {
            // Regex to match decimal numbers (positive or negative)
            val numberRegex = Regex("-?\\d+\\.\\d+")
            val matches = numberRegex.findAll(latLong).map { it.value }.toList()
            
            if (matches.size >= 2) {
                val lat = matches[0]
                val lon = matches[1]
                
                // Validate that extracted values are valid coordinates
                val latDouble = lat.toDoubleOrNull()
                val lonDouble = lon.toDoubleOrNull()
                
                if (latDouble != null && lonDouble != null &&
                    latDouble in -90.0..90.0 && lonDouble in -180.0..180.0) {
                    Pair(lat, lon)
                } else {
                    Log.w("ParkRepository", "Invalid coordinates extracted: lat=$lat, lon=$lon")
                    null
                }
            } else {
                Log.w("ParkRepository", "Could not parse latLong: $latLong")
                null
            }
        } catch (e: Exception) {
            Log.e("ParkRepository", "Error parsing latLong: $latLong", e)
            null
        }
    }
    
    private fun Park.toEntity(): ParkEntity {
        val addr = addresses.firstOrNull { it.type == "Physical" } ?: addresses.firstOrNull()
        return ParkEntity(
            parkId = id,
            parkName = fullName,
            states = states,
            parkCode = parkCode,
            latLong = latLong,
            description = description,
            designation = designation,
            address = addr?.let { "${it.line1}, ${it.city}, ${it.stateCode} ${it.postalCode}" } ?: "NA",
            phone = contacts?.phoneNumbers?.firstOrNull()?.phoneNumber ?: "NA",
            email = contacts?.emailAddresses?.firstOrNull()?.emailAddress ?: "NA",
            image = images.firstOrNull()?.url ?: ""
        )
    }
    
    private fun Trail.toEntity() = TrailEntity(
        trailId = id.toString(), trailName = name, summary = summary, difficulty = difficulty,
        imageSmall = imgSmall, imageMed = imgMedium, length = length.toString(),
        ascent = ascent.toString(), latitude = latitude.toString(), longitude = longitude.toString(),
        location = location, condition = conditionDetails, moreInfo = url
    )
    
    private fun Campground.toEntity(): CampEntity {
        val addr = addresses.firstOrNull { it.type == "Physical" } ?: addresses.firstOrNull()
        return CampEntity(
            campId = id, campName = name, description = description, parkCode = parkCode,
            address = addr?.let { "${it.line1}, ${it.city}, ${it.stateCode} ${it.postalCode}" } ?: "NA",
            latLong = latLong, cellPhoneReception = amenities.cellPhoneReception,
            showers = amenities.showers.firstOrNull() ?: "None",
            internetConnectivity = amenities.internetConnectivity,
            toilets = amenities.toilets.firstOrNull() ?: "None",
            wheelchairAccess = accessibility.wheelchairAccess,
            reservationsUrl = reservationsUrl, directionsUrl = directionsUrl
        )
    }
    
    private fun Alert.toEntity() = AlertEntity(
        alertId = id, alertName = title, description = description, category = category, parkCode = parkCode
    )
}
