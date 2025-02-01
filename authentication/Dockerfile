# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17-alpine3.19

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/authentication-*.jar authentication.jar

# Copy the RSA keys into the container
COPY keys /app/keys

# Ensure the keys are accessible
RUN chmod 600 /app/keys/*

# Expose the application port
EXPOSE 8060

# Run the application
ENTRYPOINT ["java", "-jar", "/app/authentication.jar"]
