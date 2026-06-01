# APIMan Setup

## Prerequisites
- Download APIMan Docker Compose from: https://github.com/apiman/apiman/releases/tag/3.1.3.Final
- Docker Desktop running

## Setup steps
1. Extract the ZIP
2. Edit `docker-compose.setup.yml` and replace `$PWD` with the absolute path
3. Create `data/keys` folder manually
4. Run: `docker-compose -f docker-compose.setup.yml up`
5. Run: `docker-compose up`
6. Access: http://apiman.local.gd:8080/apimanui

## Credentials
- Username: admin
- Password: admin123!

## API Configuration
- Organization: bstrong-org
- Plan: bstrong-plan (Rate Limiting: 10 req/min, Transfer Quota: 100MB/day)
- API: bstrong-api (Implementation: http://host.docker.internal:8081)
- Client: bstrong-client

## Postman collection
Use `bStrong_APIMAN_postman_collection.json` with X-API-Key header.