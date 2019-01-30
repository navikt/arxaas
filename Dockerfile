FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","no.oslomet.aaas.AaaSApplication"]
#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} aaas.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/aaas.jar"]