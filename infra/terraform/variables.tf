variable "region"       { type = string  default = "ap-south-1" }
variable "project"      { type = string  default = "enterprise-shop" }
variable "environment"  { type = string  default = "dev" }
variable "cluster_version" { type = string default = "1.30" }
variable "db_password"   { type = string  sensitive = true }

variable "services" {
  type    = list(string)
  default = ["api-gateway","user-service","product-service","order-service","payment-service","notification-service","frontend"]
}
