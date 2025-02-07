FROM openjdk:23

COPY target/*.jar auth-service-0.0.1-SNAPSHOT.jar

EXPOSE 8081

CMD ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]