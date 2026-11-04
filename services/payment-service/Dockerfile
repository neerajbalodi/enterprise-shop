# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY libs ./libs
COPY services/payment-service ./services/payment-service
RUN mvn -q -pl services/payment-service -am clean package -DskipTests

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY --from=build /workspace/services/payment-service/target/*.jar app.jar
USER appuser
EXPOSE 8084
ENTRYPOINT ["java","-jar","app.jar"]
