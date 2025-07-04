FROM openjdk:21-jdk

WORKDIR /app

COPY target/data-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

CMD ["java", "-jar", "app.jar"]