package org.example

import com.opencsv.CSVReader
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.FileReader


object csvTools {

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

object ymlTools{
    fun ReadYml(file_name: String): Config {
        val yaml = Yaml()
        val baseDir = System.getProperty("user.dir") // 获取项目根目录
        val file_path = File("$baseDir/Resource/$file_name")
        val inputStream = FileInputStream(file_path)

        // 解析为 Config 数据类
        val config: Config = yaml.loadAs(inputStream, Config::class.java)
        return config
    }
}