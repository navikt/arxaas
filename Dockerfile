FROM navikt/java
VOLUME /tmp
COPY target/arxaas-*.jar /app.jar
EXPOSE 8080/tcp
ENV SPRING_PROFILES_ACTIVE=prod
