package org.example

import kotlinx.serialization.Serializable
import kotlin.math.*


@Serializable
data class WayPoint(val time: Double, val latitude:Double, val longitude:Double){

    fun haversine(other: WayPoint, earthRadiusKm: Double): Double{
        val dLat = Math.toRadians(other.latitude - latitude)
        val dLon = Math.toRadians(other.longitude - longitude)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(latitude)) * cos(Math.toRadians(other.latitude)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadiusKm * c
    }
}