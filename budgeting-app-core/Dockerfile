# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy everything
COPY . .

# Build the whole multi-module project
RUN mvn -B -DskipTests clean package


# ---- Runtime stage ----
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy ONLY the user-service JAR
COPY --from=build /app/user-service/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
