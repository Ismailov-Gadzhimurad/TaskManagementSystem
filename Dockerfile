FROM openjdk:23

WORKDIR /app

COPY target/desktop-0.0.1-SNAPSHOT.jar /app/desktop.jar

LABEL authors="user"

ENTRYPOINT ["java", "-jar", "desktop.jar"]