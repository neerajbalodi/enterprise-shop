# Runs the jar built once by the pipeline's Build & Test stage.
# Build context is services/order-service/target (just the jar) — tiny and fast.
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY *.jar app.jar
USER appuser
EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]
