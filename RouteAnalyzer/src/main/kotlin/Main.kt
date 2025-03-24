package org.example

import kotlinx.serialization.json.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import javax.naming.Context

fun maxDistanceFromStart(str:String):String{
    val data = "x"
    return  data;
}
fun mostFrequentedArea(){}
fun waypointsOutsideGeofence(){}
import com.google.gson.Gson

fun get_json_data(data:List<Array<String>>): List<WayPoint>{
    val result = data.map{item ->
                                val parts = item[0].split(";")
                                WayPoint(parts[0].toDouble(),parts[1].toDouble(),parts[2].toDouble())
    }
    return result
}

fun main() {
//    println("Hello World!")
    val rows = CsvTools.ReadCsv("waypoints.csv")

    rows.forEach { row ->
        println(row.joinToString(", "))
    }
    val json = buildJsonObject {
        putJsonObject("maxDistanceFromStart") {
            putJsonObject("waypoint"){
                    put("timestamp","number")
                    put("latitude","number")
                    put("longitude","number")
            }
            put("distanceKm","xx")
        }
        putJsonObject("mostFrequentedArea") {
            putJsonObject("centralWaypoint"){
                put("timestamp","number")
                put("latitude","number")
                put("longitude","number")
            }
            put("areaRadiusKm","xx")
            put("entriesCount","xx")
        }
        putJsonObject("waypointsOutsideGeofence") {
            putJsonObject("centralWaypoint"){
                put("timestamp","number")
                put("latitude","number")
                put("longitude","number")
            }
            put("areaRadiusKm","xx")
            put("count","xx")
            putJsonArray("waypoints"){
                for(i in 0 .. 5)add(buildJsonObject {
                    putJsonObject("centralWaypoint"){
                        put("timestamp",i)
                        put("latitude",i)
                        put("longitude",i)
                    }})
            }

        }
    }
    val filename = "outputs.json";

    val writer = FileWriter(filename);
    writer.write(json.toString());
    writer.close()
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

fun getMostFrequentedArea(data: List<WayPoint>, config: Config):Map<String, Any>{
    var count = 0
    var center_index = 0
    for((index, point) in data.withIndex()){
        val num = data.filter { item -> item.haversine(point, config.earthRadiusKm) < config.geofenceRadiusKm }.size
        if(num > count){
            count = num
            center_index = index
        }
    }
    val center_point = data[center_index]
    val result = mapOf(
        "centralWaypoint" to center_point,
        "areaRadiusKm" to config.geofenceRadiusKm,
        "entriesCount" to count,
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

fun ExcuteAllData(){
    val config = ymlTools.ReadYml("custom-parameters.yml")
    val rows = csvTools.ReadCsv("waypoints.csv")
    val data = get_json_data(rows)
    val max_result = calculate_max_distance(data,config.earthRadiusKm)

    val frequence_area = getMostFrequentedArea(data,config)

    val center_point = WayPoint(0.0, config.geofenceCenterLatitude, config.geofenceCenterLongitude)
    val outside_result = getWaypointsOutsideGeofence(data, center_point, config)

    val output = mapOf(
        "maxDistanceFromStart" to max_result,
        "mostFrequentedArea" to frequence_area,
        "waypointsOutsideGeofence" to outside_result
    )
    val output_json = Gson().toJson(output)
    println(output_json)

    fileTools.WriteJson("output.json", output_json)
}

fun main() {
    ExcuteAllData()
}
