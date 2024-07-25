# Use an official Maven image to build the app
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the app
COPY src ./src
RUN mvn package

# Use a smaller image to run the app
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/$(./get-jar-name.sh) ./app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]
