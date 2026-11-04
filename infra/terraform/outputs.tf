output "region"           { value = var.region }
output "cluster_name"     { value = module.eks.cluster_name }
output "cluster_endpoint" { value = module.eks.cluster_endpoint }
output "ecr_registry"     { value = split("/", values(aws_ecr_repository.service)[0].repository_url)[0] }
output "ecr_repositories" { value = { for k, r in aws_ecr_repository.service : k => r.repository_url } }
output "rds_endpoint"     { value = try(aws_db_instance.postgres[0].address, null) }
output "redis_endpoint"   { value = try(aws_elasticache_cluster.redis[0].cache_nodes[0].address, null) }
