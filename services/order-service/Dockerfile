# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY libs ./libs
COPY services/order-service ./services/order-service
RUN mvn -q -pl services/order-service -am clean package -DskipTests

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY --from=build /workspace/services/order-service/target/*.jar app.jar
USER appuser
EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]
