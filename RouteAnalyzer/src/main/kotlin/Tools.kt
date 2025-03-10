package org.example

import com.opencsv.CSVReader
import org.yaml.snakeyaml.Yaml
import java.io.*


object csvTools {

    fun ReadCsv(file_name: String): List<Array<String>> {
        val inputStream = object {}.javaClass.classLoader.getResourceAsStream("$file_name")
            ?: throw FileNotFoundException("$file_name not found in classpath!")

        val csvReader = CSVReader(InputStreamReader(inputStream))

// 读取所有行
        val rows: List<Array<String>> = csvReader.readAll()
        csvReader.close()

        return rows
    }
}

object ymlTools{
    fun ReadYml(file_name: String): Config {
        val yaml = Yaml()

// 使用 ClassLoader 加载资源文件
        val inputStream = object {}.javaClass.classLoader.getResourceAsStream("$file_name")
            ?: throw FileNotFoundException("$file_name not found in classpath!")

// 解析为 Config 数据类
        val config: Config = yaml.loadAs(inputStream, Config::class.java)

        return config

    }
}

object fileTools{
    fun WriteJson(file_name: String, jsonData:String) {
        val file = File("$file_name") // 写入外部文件系统，而不是 JAR 内部
        file.parentFile?.mkdirs() // 确保目录存在

        file.bufferedWriter().use { writer ->
            writer.write(jsonData)
        }

        println("数据已成功写入到：${file.absolutePath}")


    }
}