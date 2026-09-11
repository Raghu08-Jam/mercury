FROM eclipse-temurin:25-jre
WORKDIR /app
COPY target/mercury-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]