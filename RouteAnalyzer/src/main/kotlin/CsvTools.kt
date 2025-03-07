package org.example

import com.opencsv.CSVReader
import java.io.FileReader


object CsvTools {

    fun ReadCsv(file_name: String): List<Array<String>> {
        val file = FileReader(file_name)
        val csvReader = CSVReader(file)

        // 读取所有行
        val rows: List<Array<String>> = csvReader.readAll()
        csvReader.close()
        return rows
    }
}