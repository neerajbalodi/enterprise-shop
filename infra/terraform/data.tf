# These managed services are created only when var.manage_data = true.
# The default automated pipeline uses in-cluster Postgres/Redis/Kafka instead.

resource "aws_db_subnet_group" "this" {
  count      = var.manage_data ? 1 : 0
  name       = "${local.name}-db"
  subnet_ids = module.vpc.private_subnets
  tags       = local.tags
}

resource "aws_db_instance" "postgres" {
  count                  = var.manage_data ? 1 : 0
  identifier             = "${local.name}-pg"
  engine                 = "postgres"
  engine_version         = "16"
  instance_class         = "db.t3.micro"
  allocated_storage      = 20
  db_subnet_group_name   = aws_db_subnet_group.this[0].name
  username               = "shop"
  password               = var.db_password
  multi_az               = var.environment == "prod"
  skip_final_snapshot    = true
  publicly_accessible    = false
  tags                   = local.tags
}

resource "aws_elasticache_subnet_group" "this" {
  count      = var.manage_data ? 1 : 0
  name       = "${local.name}-redis"
  subnet_ids = module.vpc.private_subnets
}

resource "aws_elasticache_cluster" "redis" {
  count             = var.manage_data ? 1 : 0
  cluster_id        = "${local.name}-redis"
  engine            = "redis"
  node_type         = "cache.t3.micro"
  num_cache_nodes   = 1
  subnet_group_name = aws_elasticache_subnet_group.this[0].name
  tags              = local.tags
}

resource "aws_msk_serverless_cluster" "kafka" {
  count        = var.manage_data ? 1 : 0
  cluster_name = "${local.name}-kafka"
  vpc_config {
    subnet_ids         = module.vpc.private_subnets
    security_group_ids = [module.eks.cluster_primary_security_group_id]
  }
  client_authentication { sasl { iam { enabled = true } } }
  tags = local.tags
}
