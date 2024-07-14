# Stage 1: Build the application with Gradle
FROM amazoncorretto:21 AS builder

WORKDIR /app

# Copy Gradle files
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build the application
RUN ./gradlew clean build

# Stage 2: Create a minimal JRE image and copy the JAR file
FROM amazoncorretto:21

WORKDIR /app

# Copy JAR file from the builder stage
COPY --from=builder /app/build/libs/agile-0.0.1-SNAPSHOT.jar app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "app.jar"]
