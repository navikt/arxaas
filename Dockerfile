FROM openjdk:11-jdk
VOLUME /tmp
COPY target/aaas-*.jar /app.jar
EXPOSE 8080/tcp
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]