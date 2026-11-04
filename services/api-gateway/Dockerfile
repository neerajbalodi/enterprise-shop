# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY libs ./libs
COPY services/api-gateway ./services/api-gateway
RUN mvn -q -pl services/api-gateway -am clean package -DskipTests

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY --from=build /workspace/services/api-gateway/target/*.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
