FROM openjdk:23

WORKDIR /app

COPY ./target/auth-service-0.0.1-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]