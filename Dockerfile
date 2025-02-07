FROM openjdk:23

COPY target/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]