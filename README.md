[![Build Status](https://travis-ci.com/oslomet-arx-as-a-service/ARXaaS.svg?branch=master)](https://travis-ci.com/oslomet-arx-as-a-service/ARXaaS)
[![Maintainability](https://api.codeclimate.com/v1/badges/a0aefdc9490c1ec63a5b/maintainability)](https://codeclimate.com/github/oslomet-arx-as-a-service/ARXaaS/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/a0aefdc9490c1ec63a5b/test_coverage)](https://codeclimate.com/github/oslomet-arx-as-a-service/ARXaaS/test_coverage)
[![Maven Central](https://img.shields.io/maven-central/v/no.oslomet/arxaas.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22no.oslomet%22%20AND%20a:%22arxaas%22)
[![Javadocs](http://javadoc.io/badge/no.oslomet/arxaas.svg)](http://javadoc.io/doc/no.oslomet/arxaas)

# ARXaaS - Anonymization-as-a-Service

ARX Web API Service

[API documentation](https://oslomet-arx-as-a-service.github.io/ARXaaS)

#### Running the service locally
##### As a Docker image
1. make sure run Docker Desktop is running.
2. pull the Docker image
```bash
docker pull arxaas/aaas
```
3. run the docker image
```bash
docker run arxaas/aaas
```

##### Execute jar from Maven
1. Go to https://search.maven.org/search?q=g:no.oslomet and download the latest version of ARXaaS
2. Run the jar file
```bash
java -jar JAR_FILE_LOCATION
```

#### Development

1. Clone the project
2. Download ARX Java library with sources from https://arx.deidentifier.org/downloads/
3. Install the ARX library in your local Maven repostitory
```bash
mvn -q install:install-file -Dfile=src/main/resources/libarx-3.7.1.jar -DgroupId=org.deidentifier -DartifactId=libarx -Dversion=3.7.1 -Dpackaging=jar
```

You are ready to go!

#### HTTPS configuration

[Documentation](READMEHTTPS.md)
