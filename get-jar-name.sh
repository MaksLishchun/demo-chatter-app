#!/bin/sh
# Отримати artifactId і версію з pom.xml
ARTIFACT_ID=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

# Вивести ім'я JAR файлу
echo "${ARTIFACT_ID}-${VERSION}.jar"
