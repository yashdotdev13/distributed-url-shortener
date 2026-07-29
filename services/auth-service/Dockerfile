FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .

RUN mvn clean package \
    -pl services/auth-service \
    -am \
    -DskipTests

FROM eclipse-temurin:21-jre

LABEL maintainer="Yash Chauhan"

WORKDIR /app

COPY --from=builder /workspace/services/auth-service/target/app.jar app.jar

EXPOSE 8091

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","app.jar"]