package org.example

import kotlinx.serialization.Serializable
import kotlin.math.pow
import kotlin.math.sqrt

@Serializable
data class WayPoint(val time: Double, val altitude:Double, val latitude:Double){
    fun distanceTo(other: WayPoint): Double{
        return  sqrt((altitude - other.altitude).pow(2) + (latitude - other.latitude).pow(2))
    }
}