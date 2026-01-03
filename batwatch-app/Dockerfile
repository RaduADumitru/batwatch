FROM maven:3.9.12-eclipse-temurin-21-alpine

WORKDIR /app
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run