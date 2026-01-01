package com.sjani.usnationalparkguide.data.model

import com.google.gson.annotations.SerializedName

// Park Models
data class ParksResponse(
    @SerializedName("data") val data: List<Park> = emptyList()
)

data class Park(
    @SerializedName("id") val id: String = "",
    @SerializedName("fullName") val fullName: String = "",
    @SerializedName("parkCode") val parkCode: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("latLong") val latLong: String = "",
    @SerializedName("contacts") val contacts: Contacts? = null,
    @SerializedName("addresses") val addresses: List<Address> = emptyList(),
    @SerializedName("images") val images: List<Image> = emptyList(),
    @SerializedName("designation") val designation: String = "",
    @SerializedName("states") val states: String = ""
)

data class Contacts(
    @SerializedName("phoneNumbers") val phoneNumbers: List<PhoneNumber> = emptyList(),
    @SerializedName("emailAddresses") val emailAddresses: List<EmailAddress> = emptyList()
)

data class PhoneNumber(@SerializedName("phoneNumber") val phoneNumber: String = "")
data class EmailAddress(@SerializedName("emailAddress") val emailAddress: String = "")

data class Address(
    @SerializedName("line1") val line1: String = "",
    @SerializedName("line2") val line2: String = "",
    @SerializedName("line3") val line3: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("stateCode") val stateCode: String = "",
    @SerializedName("postalCode") val postalCode: String = "",
    @SerializedName("type") val type: String = ""
)

data class Image(@SerializedName("url") val url: String = "")

// Campground Models
data class CampgroundResponse(@SerializedName("data") val data: List<Campground> = emptyList())

data class Campground(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("parkCode") val parkCode: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("latLong") val latLong: String = "",
    @SerializedName("reservationUrl") val reservationsUrl: String = "",
    @SerializedName("directionsUrl") val directionsUrl: String = "",
    @SerializedName("amenities") val amenities: CampAmenities = CampAmenities(),
    @SerializedName("accessibility") val accessibility: CampAccessibility = CampAccessibility(),
    @SerializedName("addresses") val addresses: List<CampAddress> = emptyList()
)

data class CampAmenities(
    @SerializedName("toilets") val toilets: List<String> = emptyList(),
    @SerializedName("internetConnectivity") val internetConnectivity: String = "",
    @SerializedName("showers") val showers: List<String> = emptyList(),
    @SerializedName("cellPhoneReception") val cellPhoneReception: String = ""
)

data class CampAccessibility(@SerializedName("wheelchairAccess") val wheelchairAccess: String = "")

data class CampAddress(
    @SerializedName("line1") val line1: String = "",
    @SerializedName("line2") val line2: String = "",
    @SerializedName("line3") val line3: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("stateCode") val stateCode: String = "",
    @SerializedName("postalCode") val postalCode: String = "",
    @SerializedName("type") val type: String = ""
)

// Alert Models
data class AlertResponse(@SerializedName("data") val data: List<Alert> = emptyList())

data class Alert(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("parkCode") val parkCode: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("category") val category: String = ""
)

// Trail Models
data class TrailResponse(@SerializedName("trails") val trails: List<Trail> = emptyList())

data class Trail(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("summary") val summary: String = "",
    @SerializedName("difficulty") val difficulty: String = "",
    @SerializedName("imgSmall") val imgSmall: String = "",
    @SerializedName("imgMedium") val imgMedium: String = "",
    @SerializedName("length") val length: Double = 0.0,
    @SerializedName("ascent") val ascent: Int = 0,
    @SerializedName("latitude") val latitude: Double = 0.0,
    @SerializedName("longitude") val longitude: Double = 0.0,
    @SerializedName("location") val location: String = "",
    @SerializedName("conditionDetails") val conditionDetails: String = "",
    @SerializedName("url") val url: String = ""
)

// Weather Models
data class CurrentWeather(
    @SerializedName("weather") val weather: List<Weather> = emptyList(),
    @SerializedName("main") val main: WeatherMain? = null,
    @SerializedName("wind") val wind: Wind? = null,
    @SerializedName("sys") val sys: Sys? = null,
    @SerializedName("name") val name: String = ""
)

data class Weather(
    @SerializedName("description") val description: String = ""
)

data class WeatherMain(
    @SerializedName("temp") val temp: Double = 0.0,
    @SerializedName("temp_min") val tempMin: Double = 0.0,
    @SerializedName("temp_max") val tempMax: Double = 0.0,
    @SerializedName("humidity") val humidity: Int = 0
)

data class Wind(@SerializedName("speed") val speed: Double = 0.0)

data class Sys(
    @SerializedName("sunrise") val sunrise: Long = 0,
    @SerializedName("sunset") val sunset: Long = 0
)


