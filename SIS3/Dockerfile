FROM ubuntu:latest
LABEL authors="akzhol"

WORKDIR /app

COPY target/SIS3-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","SIS3-0.0.1-SNAPSHOT"]