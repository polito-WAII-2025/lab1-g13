package org.example

import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader


object CsvTools {

    fun ReadCsv(file_name: String): List<Array<String>> {
        val baseDir = System.getProperty("user.dir") // 获取项目根目录
        val file_path = File("$baseDir/Resource/$file_name")
        val file = FileReader(file_path)
        val csvReader = CSVReader(file)

        // 读取所有行
        val rows: List<Array<String>> = csvReader.readAll()
        csvReader.close()
        return rows
    }
}