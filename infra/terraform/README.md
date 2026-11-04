# Terraform (AWS)

Provisions the managed infrastructure: VPC, EKS, ECR, RDS (PostgreSQL),
ElastiCache (Redis), MSK Serverless (Kafka).

```bash
cd infra/terraform
terraform init
terraform plan  -var="environment=dev" -var="db_password=<pick-one>"
terraform apply -var="environment=dev" -var="db_password=<pick-one>"
```

This is a teaching skeleton — review instance sizes, CIDRs and security groups
before any real use. Destroy nightly to control lab cost:

```bash
terraform destroy -var="environment=dev" -var="db_password=<pick-one>"
```
