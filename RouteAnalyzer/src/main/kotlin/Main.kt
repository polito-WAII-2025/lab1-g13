package org.example

import com.google.gson.Gson

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

//fun get_mostFrequentedArea(data: List<WayPoint>): Map<String, Any>{
//    var max_time = 0.0
//    var num = 0
//    for((index, point) in data.withIndex()){
//        if(index == data.size) break
//        val next_point = data[index + 1]
//        val spend_time = next_point.time - point.time
//        if(spend_time>max_time){
//            max_time = spend_time
//            num = index + 1
//        }
//    }
//    val result = {
//        "centralWaypoint" to data[num]
//    }
//}

fun main() {
    val config = ymlTools.ReadYml("custom-parameters.yml")
    val rows = csvTools.ReadCsv("waypoints.csv")
    val data = get_json_data(rows)
    val max_result = calculate_max_distance(data,config.earthRadiusKm)
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
