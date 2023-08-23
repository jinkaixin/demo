FROM maven:3.6.3-jdk-8 as builder
# 配置maven私服
COPY ../docker/maven/settings.xml ~/.m2/
# 复制源代码和POM文件
COPY ./ /app/
WORKDIR /app
# 使用Maven进行构建
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:8-jre-slim
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 12345