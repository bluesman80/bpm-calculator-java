FROM --platform=linux/amd64 eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/bpm.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
