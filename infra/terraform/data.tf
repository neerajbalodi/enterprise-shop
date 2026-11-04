# ---------------- RDS for PostgreSQL ----------------
# Production teaching note: use Multi-AZ and a proper subnet group.
resource "aws_db_subnet_group" "this" {
  name       = "${local.name}-db"
  subnet_ids = module.vpc.private_subnets
  tags       = local.tags
}

resource "aws_db_instance" "postgres" {
  identifier             = "${local.name}-pg"
  engine                 = "postgres"
  engine_version         = "16"
  instance_class         = "db.t3.micro"
  allocated_storage      = 20
  db_subnet_group_name   = aws_db_subnet_group.this.name
  username               = "shop"
  password               = var.db_password
  multi_az               = var.environment == "prod"
  skip_final_snapshot    = true
  publicly_accessible    = false
  tags                   = local.tags
}

# ---------------- ElastiCache for Redis ----------------
resource "aws_elasticache_subnet_group" "this" {
  name       = "${local.name}-redis"
  subnet_ids = module.vpc.private_subnets
}

resource "aws_elasticache_cluster" "redis" {
  cluster_id           = "${local.name}-redis"
  engine               = "redis"
  node_type            = "cache.t3.micro"
  num_cache_nodes      = 1
  subnet_group_name    = aws_elasticache_subnet_group.this.name
  tags                 = local.tags
}

# ---------------- MSK (Kafka) ----------------
# For a cheaper lab, prefer MSK Serverless (aws_msk_serverless_cluster).
resource "aws_msk_serverless_cluster" "kafka" {
  cluster_name = "${local.name}-kafka"
  vpc_config {
    subnet_ids         = module.vpc.private_subnets
    security_group_ids = [module.eks.cluster_primary_security_group_id]
  }
  client_authentication {
    sasl { iam { enabled = true } }
  }
  tags = local.tags
}
