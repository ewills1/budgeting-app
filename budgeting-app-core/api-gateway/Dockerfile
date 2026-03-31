FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY budgeting-app-core/pom.xml budgeting-app-core/
COPY budgeting-app-core/api-gateway/ budgeting-app-core/api-gateway/

RUN ./mvnw -pl budgeting-app-core/api-gateway -am package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/budgeting-app-core/api-gateway/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]