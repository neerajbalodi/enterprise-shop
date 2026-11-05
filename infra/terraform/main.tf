locals {
  name = "${var.project}-${var.environment}"
  tags = { Project = var.project, Environment = var.environment, ManagedBy = "terraform" }
}

# ---------------- Networking ----------------
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "~> 5.0"

  name = "${local.name}-vpc"
  cidr = "10.0.0.0/16"

  azs             = ["${var.region}a", "${var.region}b"]
  private_subnets = ["10.0.1.0/24", "10.0.2.0/24"]
  public_subnets  = ["10.0.101.0/24", "10.0.102.0/24"]

  enable_nat_gateway = true
  single_nat_gateway = true
  tags = local.tags
}

# ---------------- EKS ----------------
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 20.0"

  cluster_name    = local.name
  cluster_version = var.cluster_version
  vpc_id          = module.vpc.vpc_id
  subnet_ids      = module.vpc.private_subnets

  enable_irsa                    = true
  enable_cluster_creator_admin_permissions = true
  cluster_endpoint_public_access = true

  eks_managed_node_groups = {
    default = {
      ami_type       = "AL2023_x86_64_STANDARD"
      instance_types = ["t3.large"]
      min_size       = 2
      max_size       = 4
      desired_size   = 2
      capacity_type  = "SPOT"   # cheaper for a lab
    }
  }
  tags = local.tags
}
