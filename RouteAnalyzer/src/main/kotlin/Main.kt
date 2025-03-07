package org.example


fun main() {
//    println("Hello World!")
    val rows = CsvTools.ReadCsv("D:\\polito\\web2\\Project\\Resource\\waypoints.csv")
    rows.forEach { row ->
        println(row.joinToString(", "))
    }
}