# AWS Deployment

## Infrastructure
- **EC2 Instance:** t3.micro (Amazon Linux 2023)
- **Public IP:** 54.211.226.252
- **Port:** 8080

## API URL
http://54.211.226.252:8080

## Deployment steps
1. Launch EC2 instance with Amazon Linux 2023
2. Install Docker and Docker Compose
3. Create docker-compose.yaml with bstrong-api and bstrong-db services
4. Run `docker-compose up -d`

## Docker images
- API: marcosmartinez10/bstrong-api:latest
- DB: mariadb:11.3.2