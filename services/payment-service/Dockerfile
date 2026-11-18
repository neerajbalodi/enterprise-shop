# Runs the jar built once by the pipeline's Build & Test stage.
# Build context is services/payment-service/target (just the jar) — tiny and fast.
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY *.jar app.jar
# OpenTelemetry Java agent — auto-instruments HTTP, JDBC and Kafka (no code change).
# Pinned version; bump as needed. Added as root so it is world-readable for appuser.
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.10.0/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar
RUN chmod 0644 /otel/opentelemetry-javaagent.jar
ENV JAVA_TOOL_OPTIONS="-javaagent:/otel/opentelemetry-javaagent.jar"
USER appuser
EXPOSE 8084
ENTRYPOINT ["java","-jar","app.jar"]
