variable "region" {
  type    = string
  default = "ap-south-1"
}

variable "project" {
  type    = string
  default = "enterprise-shop"
}

variable "environment" {
  type    = string
  default = "dev"
}

variable "cluster_version" {
  type    = string
  default = "1.30"
}

# Managed data layer (RDS/ElastiCache/MSK). OFF by default: the automated
# pipeline runs Postgres/Redis/Kafka in-cluster. Turn on for the managed-AWS lesson.
variable "manage_data" {
  type    = bool
  default = false
}

variable "db_password" {
  type      = string
  default   = "shop-demo-password"
  sensitive = true
}

variable "services" {
  type    = list(string)
  default = ["api-gateway", "user-service", "product-service", "order-service", "payment-service", "notification-service", "frontend"]
}
