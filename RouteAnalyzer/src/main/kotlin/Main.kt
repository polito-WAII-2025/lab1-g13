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
}
