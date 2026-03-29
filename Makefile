.PHONY: help up down logs build build-maven build-all rebuild clean status \
        up-services down-services \
        logs-user logs-budget logs-transaction logs-category \
        shell-user shell-budget shell-transaction shell-category shell-mongo \
        test test-user test-budget test-transaction test-category

help:
	@echo "Budgeting App - Docker Commands"
	@echo ""
	@echo "Full stack:"
	@echo "  make up              - Start all services"
	@echo "  make down            - Stop all services"
	@echo "  make logs            - Stream logs for all services"
	@echo "  make status          - Show status of all containers"
	@echo ""
	@echo "Backend services only (no frontend):"
	@echo "  make up-services     - Start MongoDB + all backend microservices"
	@echo "  make down-services   - Stop MongoDB + all backend microservices"
	@echo ""
	@echo "Build:"
	@echo "  make build           - Build all service images"
	@echo "  make rebuild         - Rebuild all service images from scratch"
	@echo ""
	@echo "Logs (individual services):"
	@echo "  make logs-user       - Stream user-service logs"
	@echo "  make logs-budget     - Stream budget-service logs"
	@echo "  make logs-transaction - Stream transaction-service logs"
	@echo "  make logs-category   - Stream category-service logs"
	@echo ""
	@echo "Shell access:"
	@echo "  make shell-user      - Open bash in user-service container"
	@echo "  make shell-budget    - Open bash in budget-service container"
	@echo "  make shell-transaction - Open bash in transaction-service container"
	@echo "  make shell-category  - Open bash in category-service container"
	@echo "  make shell-mongo     - Open mongosh in MongoDB container"
	@echo ""
	@echo "Tests:"
	@echo "  make test            - Run tests in all backend services"
	@echo "  make test-user       - Run user-service tests"
	@echo "  make test-budget     - Run budget-service tests"
	@echo "  make test-transaction - Run transaction-service tests"
	@echo "  make test-category   - Run category-service tests"
	@echo ""
	@echo "Maintenance:"
	@echo "  make clean           - Remove all containers and volumes"
	@echo ""
	@echo "Service ports:"
	@echo "  User Service:        http://localhost:8080"
	@echo "  Budget Service:      http://localhost:8081"
	@echo "  Transaction Service: http://localhost:8082"
	@echo "  Category Service:    http://localhost:8083"
	@echo "  MongoDB Express:     http://localhost:8086 (admin/admin123)"
	@echo "  Frontend:            http://localhost:5173"

# ========== Full Stack ==========

up: build-maven
	docker-compose up -d
	@echo ""
	@echo "✅ All services started!"
	@echo ""
	@echo "Access points:"
	@echo "  Frontend:            http://localhost:5173"
	@echo "  User Service:        http://localhost:8080"
	@echo "  Budget Service:      http://localhost:8081"
	@echo "  Transaction Service: http://localhost:8082"
	@echo "  Category Service:    http://localhost:8083"
	@echo "  MongoDB Express:     http://localhost:8086 (admin/admin123)"

down:
	docker-compose down
	@echo "✅ All services stopped"

logs:
	docker-compose logs -f

# ========== Backend Services Only ==========

up-services: build-maven
	docker-compose up -d mongodb mongo-express user-service budget-service transaction-service category-service
	@echo ""
	@echo "✅ Backend microservices started!"
	@echo ""
	@echo "Access points:"
	@echo "  User Service:        http://localhost:8080"
	@echo "  Budget Service:      http://localhost:8081"
	@echo "  Transaction Service: http://localhost:8082"
	@echo "  Category Service:    http://localhost:8083"
	@echo "  MongoDB Express:     http://localhost:8086 (admin/admin123)"

down-services:
	docker-compose stop mongodb mongo-express user-service budget-service transaction-service category-service
	@echo "✅ Backend microservices stopped"

# ========== Build ==========

build-maven:
	./mvnw -f budgeting-app-core/pom.xml clean package -DskipTests

build:
	docker-compose build user-service budget-service transaction-service category-service
	@echo "✅ All service images built"

rebuild:
	docker-compose down
	docker-compose build --no-cache user-service budget-service transaction-service category-service
	docker-compose up -d mongodb mongo-express user-service budget-service transaction-service category-service
	@echo "✅ All services rebuilt and started"

# ========== Logs (individual services) ==========

logs-user:
	docker-compose logs -f user-service

logs-budget:
	docker-compose logs -f budget-service

logs-transaction:
	docker-compose logs -f transaction-service

logs-category:
	docker-compose logs -f category-service

# ========== Shell Access ==========

shell-user:
	docker-compose exec user-service bash

shell-budget:
	docker-compose exec budget-service bash

shell-transaction:
	docker-compose exec transaction-service bash

shell-category:
	docker-compose exec category-service bash

shell-mongo:
	docker-compose exec mongodb mongosh -u admin -p admin123 --authenticationDatabase admin

# ========== Tests ==========

test:
	docker-compose exec user-service mvn test && \
	docker-compose exec budget-service mvn test && \
	docker-compose exec transaction-service mvn test && \
	docker-compose exec category-service mvn test

test-user:
	docker-compose exec user-service mvn test

test-budget:
	docker-compose exec budget-service mvn test

test-transaction:
	docker-compose exec transaction-service mvn test

test-category:
	docker-compose exec category-service mvn test

# ========== Maintenance ==========

status:
	docker-compose ps

clean:
	docker-compose down -v
	@echo "✅ All containers and volumes removed"
