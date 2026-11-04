# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY libs ./libs
COPY services/user-service ./services/user-service
RUN mvn -q -pl services/user-service -am clean package -DskipTests

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY --from=build /workspace/services/user-service/target/*.jar app.jar
USER appuser
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
