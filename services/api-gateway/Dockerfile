FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .

RUN mvn clean package \
    -pl services/api-gateway \
    -am \
    -DskipTests


FROM eclipse-temurin:21-jre

LABEL org.opencontainers.image.title="distributed-url-shortener-api-gateway"
LABEL org.opencontainers.image.description="API Gateway"
LABEL org.opencontainers.image.version="1.0.0"
LABEL org.opencontainers.image.vendor="Yash Chauhan"

RUN addgroup --system spring && \
    adduser --system spring --ingroup spring

WORKDIR /app

COPY --from=builder \
    /workspace/services/api-gateway/target/*.jar \
    app.jar

RUN chown -R spring:spring /app

USER spring

EXPOSE 8081

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app/app.jar"]