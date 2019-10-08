FROM navikt/java:12
VOLUME /tmp
COPY target/arxaas-*.jar /app/app.jar
EXPOSE 8080/tcp
ENV SPRING_PROFILES_ACTIVE=prod
