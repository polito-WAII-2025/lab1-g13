package org.example

import com.google.gson.Gson

fun get_json_data(data:List<Array<String>>): List<WayPoint>{
    val result = data.map{item ->
                                val parts = item[0].split(";")
                                WayPoint(parts[0].toDouble(),parts[1].toDouble(),parts[2].toDouble())
    }
    return result
}

fun calculate_max_distance(data: List<WayPoint>, earthRadiusKm: Double): Map<String, Any>{
    val start_point = data[0]
    var num = 0
    var distance = 0.0
    for ((index,point) in data.withIndex()){
        val cur_dis = point.haversine(start_point, earthRadiusKm)
        if (cur_dis > distance){
            distance = cur_dis
            num = index
        }
    }
    val max_point = data[num]
//    println("$num distance: $distance, max_point: $max_point")
    val result = mapOf(
        "waypoint" to max_point,
        "distanceKm" to distance,
    )
    return result
}

fun getWaypointsOutsideGeofence(data: List<WayPoint>, center_point: WayPoint, config: Config): Map<String, Any>{
    val outside_points = data.filter { point -> point.haversine(center_point, config.earthRadiusKm) > config.geofenceRadiusKm }
    val count = outside_points.size
    val result = mapOf(
        "centralWaypoint" to center_point,
        "areaRadiusKm" to config.geofenceRadiusKm,
        "count" to count,
        "waypoints" to outside_points
    )
    return result
}

fun main() {
    val config = ymlTools.ReadYml("custom-parameters.yml")
    val rows = csvTools.ReadCsv("waypoints.csv")
    val data = get_json_data(rows)
    val max_result = calculate_max_distance(data,config.earthRadiusKm)
    val jsonString = Gson().toJson(max_result)
    println(jsonString)

    val center_point = WayPoint(0.0, config.geofenceCenterLatitude, config.geofenceCenterLongitude)
    val outside_result = getWaypointsOutsideGeofence(data, center_point, config)
    val outside_result_json = Gson().toJson(outside_result)
    println(outside_result_json)

}
