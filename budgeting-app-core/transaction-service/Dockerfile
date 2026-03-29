FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -f budgeting-app-core/pom.xml -pl transaction-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/transaction-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
