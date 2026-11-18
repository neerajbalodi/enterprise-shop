# Observability — Metrics, Logs, Traces

The Jenkins pipeline's **Observability Stack** stage installs and exposes everything
automatically on every `deploy`. No manual helm/kubectl commands are needed.

## What the pipeline installs (namespace: monitoring)
- **kube-prometheus-stack** — Prometheus (metrics) + Grafana (exposed via LoadBalancer).
- **loki-stack** — Loki (log store) + Promtail (ships every pod's logs).
- **tempo** — Tempo (trace store), receives OTLP traces on gRPC 4317.
Grafana is pre-wired with all three datasources (Prometheus, Loki, Tempo).

## How each pillar is produced
- **Metrics:** each service exposes `/actuator/prometheus` (micrometer-registry-prometheus);
  Prometheus scrapes pods via the `prometheus.io/*` annotations on them.
- **Logs:** each service logs JSON (logback-spring.xml + logstash-logback-encoder);
  Promtail collects them into Loki.
- **Traces:** each service runs the OpenTelemetry Java agent (baked into the image via a
  `-javaagent` flag) and exports OTLP traces to Tempo. No app code change.

## After a deploy — get the Grafana URL
The pipeline prints it at the end of the Observability stage. Or:
```bash
echo "http://$(kubectl -n monitoring get svc monitoring-grafana -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')"
```
Login: **admin / admin123**

## See the three pillars in Grafana
- **Metrics:** Dashboards → Import → ID 11378 (Spring Boot) / 4701 (JVM).
- **Logs:** Explore → pick **Loki** → query `{service="order-service"}`.
- **Traces:** Explore → pick **Tempo** → search recent traces, open one to see the
  gateway → order → payment → notification waterfall.
- **Correlation:** a Loki log line's `trace_id` is a clickable link into the Tempo trace.

## Make it move (load test)
```bash
APP=$(kubectl -n shop-dev get svc frontend -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
for i in $(seq 1 500); do curl -s http://$APP/api/products > /dev/null; done
# place an order to generate a full cross-service trace:
curl -s -X POST http://$APP/api/orders -H 'Content-Type: application/json' -H 'Idempotency-Key: t1' \
  -d '{"userId":"<id>","lines":[{"productId":"p-1001","quantity":1,"unitPrice":89.99}]}'
```

## Teardown
The pipeline's `ACTION=destroy` now deletes all LoadBalancer services and the observability
stack before `terraform destroy`, so the VPC deletes cleanly (no manual LB cleanup needed).
