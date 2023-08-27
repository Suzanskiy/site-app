provider "aws" {
  region = "eu-central-1"
}

resource "aws_ecs_cluster" "my_cluster" {
  name = "my-cluster"
}

resource "aws_ecs_task_definition" "my_task" {
  family                = "my-task-family"
  network_mode          = "bridge"
  requires_compatibilities = ["EC2"]
  cpu                   = "256"
  memory                = "512"
  execution_role_arn    = aws_iam_role.ecs_execution_role.arn

  container_definitions = jsonencode([
    {
      name  = "site-application"
      image = "349962084235.dkr.ecr.eu-central-1.amazonaws.com/mine-site:latest"
      cpu   = 128
      memory = 256
    }
  ])
}

resource "aws_iam_role" "ecs_execution_role" {
  name = "ecs_execution_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        },
        Effect = "Allow",
        Sid    = ""
      }
    ]
  })
}

resource "aws_ecs_service" "my_service" {
  name            = "my-service"
  cluster         = aws_ecs_cluster.my_cluster.id
  task_definition = aws_ecs_task_definition.my_task.arn
  desired_count   = 1
  launch_type     = "EC2"
}

# This is just a basic example, and you might also need to configure security groups, VPCs, subnets, etc.
