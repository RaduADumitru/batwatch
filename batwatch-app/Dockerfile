FROM maven:3.9.12-eclipse-temurin-21-alpine

WORKDIR /app

# Install dependencies: cached, should mainly only run if pom.xml is changed
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B dependency:resolve-plugins dependency:resolve

# Copy source code and run
COPY src src

CMD mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"