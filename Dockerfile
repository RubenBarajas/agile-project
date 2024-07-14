# Base Java 21 image
FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build.gradle files
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copy the application source code
COPY src ./src

# Run the Gradle build and tests
RUN ./gradlew clean build

# Copy the built JAR file into the container
COPY build/libs/agile-0.0.1-SNAPSHOT.jar app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/app/app.jar"]
