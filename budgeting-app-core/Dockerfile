FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy pom.xml first for better layer caching
COPY pom.xml .

# Download dependencies (this layer gets cached)
RUN mvn dependency:resolve

# Copy source code
COPY src ./src

# Expose port
EXPOSE 8080

# Run the application
CMD ["mvn", "spring-boot:run"]