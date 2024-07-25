# # Use an official Maven image with JDK 17 to build the app
# FROM maven:3.8.4-openjdk-17 AS build

# # Set the working directory
# WORKDIR /app

# # Copy the pom.xml and install dependencies
# COPY pom.xml .
# RUN mvn dependency:go-offline

# # Copy the rest of the source code and build the app
# COPY src ./src
# RUN mvn package

# # Use a smaller image to run the app
# FROM openjdk:17-oracle

# # Set the working directory
# WORKDIR /app

# # Copy the jar file from the build stage
# COPY --from=build /app/target/$(./get-jar-name.sh) ./app.jar

# # Expose the port the app runs on
# EXPOSE 8080

# # Run the app
# CMD ["java", "-jar", "app.jar"]

# Використовуємо базовий образ OpenJDK 17
FROM openjdk:17-oracle

# Вказуємо робочу директорію
WORKDIR /app

# Копіюємо файл jar до контейнера
COPY target/*.jar app.jar

# Вказуємо команду запуску
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app/app.jar"]


