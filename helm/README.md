# Helm

One generic chart (`charts/service`) renders every microservice; the umbrella
(`umbrella/`) deploys them as a single release.

```bash
cd helm/umbrella
helm dependency update
helm upgrade --install shop . -f values.yaml -f values-dev.yaml  -n dev   --create-namespace
helm upgrade --install shop . -f values.yaml -f values-prod.yaml -n prod  --create-namespace
helm history shop -n prod
helm rollback shop 1 -n prod
```

Replace `REPLACE_ME` image repositories with your ECR registry, and create the
`*-secrets` Secrets (DB creds, JWT key) via External Secrets or the CSI driver.
