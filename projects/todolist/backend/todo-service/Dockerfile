FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# 复制 pom.xml 和下载依赖（利用 Docker 缓存）
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 复制源代码并构建
COPY src ./src
RUN mvn clean package -DskipTests

# 运行阶段
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 复制构建好的 JAR
COPY --from=build /app/target/todo-service-1.0.0.jar app.jar

# 暴露端口
EXPOSE 8002

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
