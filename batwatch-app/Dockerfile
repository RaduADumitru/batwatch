FROM maven:3.9.12-eclipse-temurin-21-alpine

WORKDIR /app
COPY . .
RUN mvn clean install

#CMD mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
CMD mvn spring-boot:run