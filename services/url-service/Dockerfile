FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .

RUN mvn clean package \
    -pl services/url-service \
    -am \
    -DskipTests

FROM eclipse-temurin:21-jre

LABEL maintainer="Yash Chauhan"
LABEL org.opencontainers.image.title="distributed-url-shortener-url-service"
LABEL org.opencontainers.image.description="URL Service"
LABEL org.opencontainers.image.version="1.0.0"

RUN addgroup --system spring && \
    adduser --system spring --ingroup spring

WORKDIR /app

COPY --from=builder \
    /workspace/services/url-service/target/app.jar \
    app.jar

RUN chown -R spring:spring /app

USER spring

EXPOSE 8092

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","app.jar"]