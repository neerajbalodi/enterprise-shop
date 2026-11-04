output "cluster_name"      { value = module.eks.cluster_name }
output "cluster_endpoint"  { value = module.eks.cluster_endpoint }
output "ecr_repositories"  { value = { for k, r in aws_ecr_repository.service : k => r.repository_url } }
output "rds_endpoint"      { value = aws_db_instance.postgres.address }
output "redis_endpoint"    { value = aws_elasticache_cluster.redis.cache_nodes[0].address }
