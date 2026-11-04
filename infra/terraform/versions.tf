terraform {
  required_version = ">= 1.6"
  required_providers {
    aws = { source = "hashicorp/aws", version = "~> 5.60" }
  }
  # Remote state (uncomment and configure for team use):
  # backend "s3" {
  #   bucket         = "enterprise-shop-tfstate"
  #   key            = "global/terraform.tfstate"
  #   region         = "ap-south-1"
  #   dynamodb_table = "enterprise-shop-tflock"
  # }
}

provider "aws" {
  region = var.region
}
