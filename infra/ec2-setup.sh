#!/usr/bin/env bash
# One-time setup for a Jenkins controller on Amazon Linux 2023 (EC2).
# Installs: Docker, Git, Java 21, Jenkins, AWS CLI, kubectl, Helm, Terraform.
# Run as root (or with sudo). Recommended instance: t3.large, 30GB disk.
set -euo pipefail

dnf update -y
dnf install -y git docker fontconfig java-21-amazon-corretto unzip which

# Docker
systemctl enable --now docker

# Jenkins
wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
dnf install -y jenkins
usermod -aG docker jenkins
systemctl enable --now jenkins

# AWS CLI v2
curl -s "https://awscli.amazonaws.com/awscli-exe-linux-$(uname -m).zip" -o /tmp/awscliv2.zip
unzip -q -o /tmp/awscliv2.zip -d /tmp && /tmp/aws/install --update

# kubectl
curl -sLO "https://dl.k8s.io/release/$(curl -sL https://dl.k8s.io/release/stable.txt)/bin/linux/$(uname -m | sed 's/x86_64/amd64/;s/aarch64/arm64/')/kubectl"
install -m 0755 kubectl /usr/local/bin/kubectl && rm -f kubectl

# Helm
curl -s https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash

# Terraform
dnf install -y dnf-plugins-core
dnf config-manager --add-repo https://rpm.releases.hashicorp.com/AmazonLinux/hashicorp.repo
dnf install -y terraform

systemctl restart jenkins
echo "---------------------------------------------"
echo "Done. Jenkins is on http://<EC2_PUBLIC_IP>:8080"
echo "Initial admin password:"
cat /var/lib/jenkins/secrets/initialAdminPassword || true
echo "---------------------------------------------"
echo "Next: install suggested plugins + 'Pipeline: AWS Steps' (AWS Credentials),"
echo "add an 'aws-creds' AWS credential, then create a Pipeline job from ci/Jenkinsfile."
