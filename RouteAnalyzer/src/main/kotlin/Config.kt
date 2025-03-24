package org.example

data class Config(
    var earthRadiusKm: Double = 0.0,
    var geofenceCenterLatitude: Double = 0.0,
    var geofenceCenterLongitude: Double = 0.0,
    var geofenceRadiusKm: Double = 0.0,
    var mostFrequentedAreaRadiusKm: Double = 0.0
) 