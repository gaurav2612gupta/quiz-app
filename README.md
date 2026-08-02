# 🧩 Quiz App — Microservices Platform (Eureka + Spring Cloud Gateway)

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Microservices-brightgreen.svg)](https://spring.io/projects/spring-cloud)

A distributed microservices-based Quiz platform built with Spring Cloud, featuring Service Registry (Netflix Eureka), API Gateway routing, Question Service, and Quiz Orchestration Service.

---

## 🏗️ Microservices Architecture
- **`server-registry`:** Netflix Eureka Naming Server for service registration & discovery.
- **`api-gateway`:** Spring Cloud API Gateway serving as the unified entry point.
- **`question-service`:** Independent service managing question CRUD, topics, and difficulty levels.
- **`quiz-service`:** Quiz creation service that communicates via Feign Client with `question-service` to generate interactive tests.

---

## 🛠️ Tech Stack
- **Language:** Java 17
- **Frameworks:** Spring Boot, Spring Cloud, Netflix Eureka, Spring Cloud Gateway, OpenFeign
- **Database:** Spring Data JPA, PostgreSQL / H2
- **Build Tool:** Maven (Multi-module)

---

## 🚀 Getting Started

### Launch Order:
1. Start `server-registry` (Eureka Server on port `8761`)
2. Start `api-gateway` (Gateway Router on port `8060`)
3. Start `question-service` and `quiz-service`

```bash
# Clone the repository
git clone https://github.com/gaurav2612gupta/quiz-app.git
cd quiz-app

# Run Eureka Registry Server
cd server-registry && ./mvnw spring-boot:run
```

Visit the Eureka Dashboard at `http://localhost:8761`.
