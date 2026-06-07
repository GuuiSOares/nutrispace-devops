FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build

COPY NutriSpace-GS-main/pom.xml .
COPY NutriSpace-GS-main/.mvn ./.mvn
COPY NutriSpace-GS-main/mvnw .
RUN chmod +x mvnw && mvn dependency:go-offline -DskipTests

COPY NutriSpace-GS-main/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
RUN addgroup --system nutriapp && adduser --system --ingroup nutriapp nutriuser
WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar
COPY docker/application-docker.properties docker/schema-docker.sql docker/data-docker.sql /app/config/
RUN chown -R nutriuser:nutriapp app.jar /app/config

USER nutriuser
EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]
