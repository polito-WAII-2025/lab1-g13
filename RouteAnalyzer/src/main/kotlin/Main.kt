package org.example


fun main() {
//    println("Hello World!")
    val rows = CsvTools.ReadCsv("waypoints.csv")
    rows.forEach { row ->
        println(row.joinToString(", "))
    }
}
