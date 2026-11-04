# Enterprise Shop — DevOps Showcase

A Java microservices platform built to exercise a full enterprise DevOps stack:
**Spring Boot · PostgreSQL · Redis · Kafka · Docker · Jenkins · Kubernetes · Helm · AWS · Terraform**.

## Services
| Service | Port | Responsibility |
|---|---|---|
| api-gateway | 8080 | Single entry point, routing, rate limiting |
| user-service | 8081 | Registration, login, JWT issuing |
| product-service | 8082 | Catalog, inventory, stock reservation |
| order-service | 8083 | Order lifecycle, orchestration |
| payment-service | 8084 | Payment processing (simulated) |
| notification-service | 8085 | Email/SMS fan-out (simulated) |

## Run locally
```bash
# 1. Start infra + all services
docker compose up --build

# 2. Or run infra only, then run services from your IDE
docker compose up postgres redis kafka
```

Gateway is at http://localhost:8080. Swagger UI per service at `/swagger-ui.html`.

## Build
```bash
mvn -q clean package          # all modules
mvn -q -pl services/user-service -am package   # one service + its deps
```

## Layout
```
services/       six Spring Boot microservices
libs/           shared event DTOs + web/error handling
frontend/       Angular SPA (served via nginx)
helm/           per-service charts + umbrella release
k8s/            raw manifests (teaching Phase 3, before Helm)
infra/terraform AWS provisioning (EKS, RDS, MSK, ElastiCache)
ci/Jenkinsfile  CI/CD pipeline
```

See `docs/` and the architecture blueprint for the full plan.

## Status
user-service and order-service are implemented with real endpoints and event flow.
product, payment and notification services are working skeletons — scaffolded with a
Spring Boot app, sample entity/controller, config and Dockerfile — ready to flesh out.
