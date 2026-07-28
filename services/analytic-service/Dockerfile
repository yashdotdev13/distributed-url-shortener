FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .

RUN mvn clean package \
    -pl services/analytic-service \
    -am \
    -DskipTests



FROM eclipse-temurin:21-jre

LABEL org.opencontainers.image.title="distributed-url-shortener-analytic-service"
LABEL org.opencontainers.image.description="Analytics Service"
LABEL org.opencontainers.image.version="1.0.0"

RUN addgroup --system spring && \
    adduser --system spring --ingroup spring

WORKDIR /app

COPY --from=builder \
    /workspace/services/analytic-service/target/*.jar \
    app.jar

RUN chown -R spring:spring /app

USER spring

EXPOSE 8094

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app/app.jar"]