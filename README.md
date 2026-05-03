Distributed URL Shortener (Spring Boot Microservices)

A scalable URL Shortener system built using Spring Boot microservices architecture, featuring Kafka-based event-driven analytics and Redis caching for high-performance redirects.


⚙️ Tech Stack
Backend: Java, Spring Boot
Architecture: Microservices
Database: MySQL
Cache: Redis
Messaging: Apache Kafka
Service Discovery: Eureka
API Gateway: Spring Cloud Gateway
Build Tool: Maven
Containerization: Docker (infra)

📦 Services
1. url-service
Generates short URLs
Handles redirects
Publishes click events to Kafka
Uses Redis for caching
2. analytics-service
Consumes Kafka events
Stores click analytics asynchronously
3. api-gateway
Routes external requests to services
4. discovery-server
Service registry (Eureka)

🔄 Request Flow
Create Short URL
POST /api/urls
{
  "originalUrl": "https://example.com"
}

Redirect
GET /{shortCode}

Flow:

1. Check Redis cache
2. If miss → fetch from DB
3. Redirect (302 FOUND)
4. Send event to Kafka
5. analytics-service consumes and stores data
⚡ Features
🔥 High-speed redirects using Redis cache
📊 Asynchronous analytics with Kafka
🧱 Microservices-based architecture
🔁 Event-driven design
🚀 Scalable and production-ready design
🧪 Testing APIs

Use Postman

Create URL
POST http://localhost:8081/api/urls
Redirect
GET http://localhost:8081/{shortCode}

🐳 Running the Project
Start infrastructure
docker compose up -d

Services started:

MySQL
Redis
Kafka
Zookeeper
Run services (IntelliJ)

Run in order:

1. discovery-server (8761)
2. api-gateway (8080)
3. url-service (8081)
4. analytics-service (8082)

🧾 Logging Flow
url-service
Redis cache HIT / MISS
Kafka event sent
analytics-service
Received Kafka payload
Saved analytics to DB

🧠 Key Learnings
Designed event-driven microservices architecture
Implemented asynchronous processing using Kafka
Improved performance using Redis caching
Built scalable system with service discovery and API gateway

📌 Future Improvements
Rate limiting (Redis)
URL expiration cleanup (scheduler)
Distributed ID generation
Dashboard for analytics
Full Dockerization (services + infra)
