FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -pl category-service -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/category-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
