FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/mensageria-docker-0.0.1-SNAPSHOT.jar /app/mensageria-docker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mensageria-docker.jar"]