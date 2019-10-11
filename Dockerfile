FROM navikt/java:13
VOLUME /tmp
COPY target/arxaas-*.jar /app/app.jar
EXPOSE 8080/tcp
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx8g -XshowSettings:vm"
