# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Add a label
LABEL maintainer="prathmeshbondar@gmail.com"

# Set the working directory
WORKDIR /app

# Copy the jar file to the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar



# Expose the port your app runs on (e.g. 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"] 