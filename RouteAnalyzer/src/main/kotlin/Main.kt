package org.example

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
            WayPoint(parts[0],parts[0],parts[0])

        }
    return result
}

fun main() {
    val rows = CsvTools.ReadCsv("waypoints.csv")
//    rows.forEach { row ->
//        println(row.joinToString(", "))
//    }
//    val first = rows.map({ row -> row.joinToString(", ") })
//                    .map { row -> row.split(";")[0] }
//    first.forEach { s -> println(s) }
//    val time_stamp = get_timestamp(rows)
//    time_stamp.forEach(::println)
    val data = get_json_data(rows)
    println(Json.encodeToString(data))
//    data.forEach(::println)
}