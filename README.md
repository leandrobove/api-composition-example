# API Composition Example

How to build and run applications using Docker:
```bash
docker compose --profile local up -d
```

Fetch employee details from microservices:
```bash
curl http://localhost:8080/employees/c4f5a97c-c9ee-4bf1-bce5-e0128f0a2b5b
```

Stop and remove all containers:
```bash
docker compose --profile local down
```