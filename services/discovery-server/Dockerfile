########################################################
# Build Stage
########################################################

FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .

RUN mvn clean package \
    -pl services/discovery-server \
    -am \
    -DskipTests

########################################################
# Runtime Stage
########################################################

FROM eclipse-temurin:21-jre

LABEL maintainer="Yash Chauhan"

WORKDIR /app

COPY --from=builder \
/workspace/services/discovery-server/target/*.jar \
app.jar

EXPOSE 8761

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app/app.jar"]