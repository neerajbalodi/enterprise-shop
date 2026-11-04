# One ECR repository per service/image.
resource "aws_ecr_repository" "service" {
  for_each             = toset(var.services)
  name                 = "${var.project}/${each.key}"
  image_tag_mutability = "IMMUTABLE"
  image_scanning_configuration { scan_on_push = true }
  tags = local.tags
}
