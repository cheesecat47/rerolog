# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy AS builder
LABEL authors="cheesecat47"

WORKDIR /app

COPY .mvn .mvn/
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

RUN ./mvnw clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-jammy

COPY --from=builder /app/target/*SNAPSHOT.jar /app.jar
