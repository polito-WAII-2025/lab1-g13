package org.example

import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun get_timestamp(data:List<Array<String>>): List<Double>{
    val first_string = data.map({ row -> row.joinToString(", ") })
        .map { row -> row.split(";")[0] }
    val first_double = first_string.mapNotNull { it.toDoubleOrNull() }
    return first_double
}

fun get_data(data:List<Array<String>>): List<Map<String, Any>>{
    val result = data.map({ row -> row.joinToString(", ") })
        .map {item ->
                val parts = item.split(";")
                mapOf(
                    "time" to parts[0],
                    "altitude" to parts[1],
                    "latitude" to parts[2],
                )
        }
    return result
}

fun get_json_data(data:List<Array<String>>): List<WayPoint>{
    val result = data.map({ row -> row.joinToString(";") })
        .map {item ->
            val parts = item.split(";")
            WayPoint(parts[0].toDouble(),parts[0].toDouble(),parts[0].toDouble())

        }
    return result
}

fun calculate_max_distance(data: List<WayPoint>): Map<String, Any>{
    val start_point = data[0]
    var num = 0
    var distance = 0.0
    for ((index,point) in data.withIndex()){
        val cur_dis = point.distanceTo(start_point)
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

fun main() {
    val rows = CsvTools.ReadCsv("waypoints.csv")
    val data = get_json_data(rows)
    val max_result = calculate_max_distance(data)
    val jsonString = Gson().toJson(max_result)
    println(jsonString)
//    val test = Json.encodeToString(max_result)
//    println(test)
//    rows.forEach { row ->
//        println(row.joinToString(", "))
//    }
//    val first = rows.map({ row -> row.joinToString(", ") })
//                    .map { row -> row.split(";")[0] }
//    first.forEach { s -> println(s) }
//    val time_stamp = get_timestamp(rows)
//    time_stamp.forEach(::println)
//    val data = get_json_data(rows)
//    println(Json.encodeToString(data))
//    data.forEach(::println)
}
