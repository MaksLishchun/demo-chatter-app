# syntax=docker/dockerfile:1.0.0

FROM maven:3.8.4-openjdk-17 AS build

# # Set the working directory
WORKDIR /app

COPY . .

RUN mvn dependency:go-offline

RUN --mount=type=secret,id=FIREBASE_ACCOUNT_KEY_ENCODED,dst=/etc/secrets/FIREBASE_ACCOUNT_KEY_ENCODED cat /etc/secrets/FIREBASE_ACCOUNT_KEY_ENCODED

RUN echo "$FIREBASE_ACCOUNT_KEY_ENCODED" | base64 --decode > src/main/resources/firebase_service_account_key.json

# Перевірка наявності файлу
RUN ls -l src/main/resources


RUN mvn clean install -Dspring.profiles.active=server

COPY target/*.jar app.jar

# Вказуємо команду запуску
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Dspring.profiles.active=server", "-jar", "/app/app.jar"]


