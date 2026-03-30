FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -f budgeting-app-core/pom.xml -pl budget-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/budgeting-app-core/budget-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
