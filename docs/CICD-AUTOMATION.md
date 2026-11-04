# Automated CI/CD — one trigger provisions infra and deploys the app

## What the pipeline does (per trigger, fully automated)
1. **Build & test** every service (in a Maven container — no Maven needed on the agent).
2. **Provision infra** with Terraform: VPC, **EKS** cluster, **ECR** repos. State is
   stored in S3 + DynamoDB (bootstrapped automatically on first run).
3. **Build & push** all service images + the frontend to ECR, tagged with the Git SHA.
4. **Deploy the data layer** (Postgres, Redis, Kafka) in-cluster, plus the `shop-secrets` Secret.
5. **Deploy the app** via one Helm release (all 6 services + frontend), images pinned to the SHA.
6. **Smoke test** the gateway's health endpoint.

A `destroy` run uninstalls the release and runs `terraform destroy`.

## One-time setup (cannot be automated away)
These establish *access*; everything after is automated.

1. **Launch an EC2 instance** (t3.large, Amazon Linux 2023, 30GB, open port 8080).
2. SSH in and run the bootstrap:
   ```bash
   sudo bash infra/ec2-setup.sh
   ```
   It installs Docker, Jenkins, AWS CLI, kubectl, Helm and Terraform, and prints the
   initial admin password.
3. In Jenkins: install suggested plugins **plus "Pipeline: AWS Steps"** (provides AWS Credentials).
4. Add an AWS credential with ID **`aws-creds`** (an IAM user/role allowed to manage
   EKS, EC2/VPC, ECR, IAM, S3, DynamoDB). For a lab, AdministratorAccess is simplest.
5. Create a **Pipeline** job → *Pipeline script from SCM* → your Git repo →
   Script Path `ci/Jenkinsfile`.

## Run it
*Build with Parameters* → `ACTION=deploy`, `ENVIRONMENT=dev` → Build.
First run takes ~15–20 min (EKS creation dominates). Re-runs are much faster.

## Access the app
The frontend Ingress needs the AWS Load Balancer Controller (not installed by default).
Until then, reach it with a port-forward:
```bash
aws eks update-kubeconfig --name enterprise-shop-dev --region ap-south-1
kubectl -n shop-dev port-forward svc/frontend 8080:80     # UI
kubectl -n shop-dev port-forward svc/api-gateway 8081:80  # API
```

## Tear down (stop the meter)
*Build with Parameters* → `ACTION=destroy`, same `ENVIRONMENT`.

## Cost note
An EKS cluster + NAT gateway bills by the hour. Destroy when you're not teaching.
Set `MANAGE_DATA=true` only for the advanced lesson — managed RDS/MSK/ElastiCache add cost
and need app-side auth changes (MSK uses IAM).

## Trigger on push (optional)
Add a GitHub webhook to the repo → `http://<ec2-ip>:8080/github-webhook/` and enable
"GitHub hook trigger" on the job, so every push to main runs the pipeline.
