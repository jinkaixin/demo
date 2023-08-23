FROM maven:3.6.3-jdk-8 as builder
# 配置maven私服，注意路径基于构建上下文，而不是Dockerfile所在路径，聚合项目下由docker进行编译的话，构建上下文为聚合项目根路径
COPY ./docker/maven/settings.xml /usr/share/maven/conf/settings.xml
# 复制源代码和POM文件，xxx是模块名
COPY ./demo-knife4j/ /app/
WORKDIR /app
# 使用Maven进行构建
RUN mvn clean package -DskipTests

# 运行阶段
FROM openjdk:8-jre-slim
COPY --from=builder /app/target/*.jar /app/app.jar