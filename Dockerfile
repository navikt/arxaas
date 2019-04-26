FROM openjdk:11-jdk
VOLUME /tmp
COPY target/arxaas-*.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]
