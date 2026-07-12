FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY target/ecom-app-0.0.1-SNAPSHOT.jar ecom-app.jar
CMD ["java", "-jar", "ecom-app.jar"]
