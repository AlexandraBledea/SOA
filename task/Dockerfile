# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:21-alpine3.17

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/task-*.jar task.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/task.jar"]
