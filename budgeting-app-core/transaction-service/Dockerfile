FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw -pl transaction-service -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/transaction-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
