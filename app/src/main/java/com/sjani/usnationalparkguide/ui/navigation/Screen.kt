package com.sjani.usnationalparkguide.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object MainList : Screen("main_list")
    object Favorites : Screen("favorites")
    object ParkDetail : Screen("park_detail/{parkCode}/{latLong}") {
        fun createRoute(parkCode: String, latLong: String): String {
            val encoded = latLong.replace(",", "_").replace(":", "-")
            return "park_detail/$parkCode/$encoded"
        }
        fun decodeLatLong(encoded: String) = encoded.replace("_", ",").replace("-", ":")
    }
    object TrailDetail : Screen("trail_detail/{trailId}") {
        fun createRoute(trailId: String) = "trail_detail/$trailId"
    }
    object CampDetail : Screen("camp_detail/{campId}") {
        fun createRoute(campId: String) = "camp_detail/$campId"
    }
    object Settings : Screen("settings")
}
