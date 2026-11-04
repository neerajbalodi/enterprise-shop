# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY libs ./libs
COPY services/product-service ./services/product-service
RUN mvn -q -pl services/product-service -am clean package -DskipTests

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY --from=build /workspace/services/product-service/target/*.jar app.jar
USER appuser
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]
