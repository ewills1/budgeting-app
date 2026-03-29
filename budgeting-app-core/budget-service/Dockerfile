FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw -pl budget-service -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/budget-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
