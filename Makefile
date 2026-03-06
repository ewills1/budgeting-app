.PHONY: help up down logs build rebuild clean status shell-backend shell-mongo up-backend down-backend logs-backend-only

help:
	@echo "Budgeting App - Backend Docker Commands"
	@echo ""
	@echo "Backend + MongoDB:"
	@echo "  make up-backend      - Start MongoDB + Backend only"
	@echo "  make down-backend    - Stop MongoDB + Backend only"
	@echo "  make logs-backend    - View backend logs only"
	@echo "  make status          - Show status of all containers"
	@echo "  make shell-backend   - Open bash in backend container"
	@echo "  make shell-mongo     - Open mongosh in MongoDB container"
	@echo "  make test            - Run backend tests"
	@echo ""
	@echo "Full stack (when frontend added):"
	@echo "  make up              - Start all services (MongoDB + Backend + Frontend)"
	@echo "  make down            - Stop all services"
	@echo "  make logs            - View all logs"
	@echo ""
	@echo "Maintenance:"
	@echo "  make build           - Build backend container"
	@echo "  make rebuild         - Rebuild backend from scratch"
	@echo "  make clean           - Remove all containers and volumes"

# ========== Backend + MongoDB Only ==========

up-backend:
	docker-compose up -d mongodb mongo-express backend
	@echo ""
	@echo "✅ Backend + MongoDB started!"
	@echo ""
	@echo "Access points:"
	@echo "  Backend API:      http://localhost:8080/api"
	@echo "  MongoDB Express:  http://localhost:8081 (admin/admin123)"
	@echo "  MongoDB Direct:   localhost:27017 (admin/admin123)"
	@echo ""
	@echo "View logs with: make logs-backend"

down-backend:
	docker-compose stop mongodb mongo-express backend
	@echo "✅ Backend + MongoDB stopped"

logs-backend:
	docker-compose logs -f backend

# ========== Full Stack (Future) ==========

up:
	docker-compose up -d
	@echo ""
	@echo "✅ All services started!"
	@echo ""
	@echo "Access points:"
	@echo "  Frontend:         http://localhost:5173"
	@echo "  Backend API:      http://localhost:8080/api"
	@echo "  MongoDB Express:  http://localhost:8081 (admin/admin123)"

down:
	docker-compose down
	@echo "✅ All services stopped"

logs:
	docker-compose logs -f

# ========== Shared Commands ==========

status:
	docker-compose ps

build:
	docker-compose build backend

rebuild:
	docker-compose down mongodb mongo-express backend
	docker-compose build --no-cache backend
	docker-compose up -d mongodb mongo-express backend
	@echo "✅ Backend rebuilt and started"

clean:
	docker-compose down -v
	@echo "✅ All containers and volumes removed"

shell-backend:
	docker-compose exec backend bash

shell-mongo:
	docker-compose exec mongodb mongosh -u admin -p admin123 --authenticationDatabase admin

test:
	docker-compose exec backend mvn test