# 使用官方的 OpenJDK 镜像作为基础镜像
FROM openjdk:23-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件到容器中
COPY build/libs/RouteAnalyzer-1.0-SNAPSHOT-all.jar /app/app.jar

# 暴露应用程序运行的端口（如果需要）
EXPOSE 9090

# 设置启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]