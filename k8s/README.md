# Raw Kubernetes manifests (teaching Phase 3)

These hand-written manifests are the "before Helm" version. Students feel the
repetition of near-identical YAML per service here; Phase 4 replaces this whole
folder with the Helm charts in `../helm`.

```bash
kubectl apply -f namespace.yaml
kubectl apply -f user-service.yaml
kubectl get pods -n shop
```
