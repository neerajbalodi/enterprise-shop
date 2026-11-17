# Observability — Phase 1: Metrics (Prometheus + Grafana)

The app side is already wired:
- each service has `micrometer-registry-prometheus`, so `/actuator/prometheus` is live;
- the Helm chart stamps `prometheus.io/scrape/path/port` annotations on every service pod.

## 1. Install the stack (one Helm chart)
```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

helm install monitoring prometheus-community/kube-prometheus-stack \
  -n monitoring --create-namespace \
  -f monitoring/kube-prometheus-stack-values.yaml
```

## 2. Get the Grafana URL (LoadBalancer)
```bash
kubectl -n monitoring get svc monitoring-grafana -w   # wait for EXTERNAL-IP
```
Open http://<that-hostname>  ·  login: **admin / admin123**

## 3. Confirm Prometheus is scraping the services
```bash
kubectl -n monitoring port-forward svc/monitoring-kube-prometheus-prometheus 9090:9090
# open http://localhost:9090/targets  → the "spring-boot-pods" job should list every service UP
```

## 4. See the metrics in Grafana
- In Grafana → Dashboards → Import, add dashboard ID **4701** (JVM Micrometer) and **11378** (Spring Boot).
- Or Explore → pick the Prometheus datasource → query:
  `rate(http_server_requests_seconds_count[1m])`

## 5. The live demo — generate load and watch it move
```bash
APP=$(kubectl -n shop-dev get svc frontend -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
# hammer the catalog so the graphs react in real time
for i in $(seq 1 500); do curl -s http://$APP/api/products > /dev/null; done
```
Watch request-rate and latency climb in Grafana, then settle. Place an order to see
order-service and Kafka metrics move too.

## What students see
Request rate, error rate, p95 latency, JVM memory/GC, and Kafka consumer lag — per service,
live — all on one Grafana screen that visibly reacts to traffic.
