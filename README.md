[![Build Status](https://travis-ci.com/oslomet-arx-as-a-service/ARXaaS.svg?branch=master)](https://travis-ci.com/oslomet-arx-as-a-service/ARXaaS)
[![Maintainability](https://api.codeclimate.com/v1/badges/a0aefdc9490c1ec63a5b/maintainability)](https://codeclimate.com/github/oslomet-arx-as-a-service/ARXaaS/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/a0aefdc9490c1ec63a5b/test_coverage)](https://codeclimate.com/github/oslomet-arx-as-a-service/ARXaaS/test_coverage)
[![Maven Central](https://img.shields.io/maven-central/v/no.oslomet/arxaas.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22no.oslomet%22%20AND%20a:%22arxaas%22)
[![Javadocs](http://javadoc.io/badge/no.oslomet/arxaas.svg)](http://javadoc.io/doc/no.oslomet/arxaas)

# ARXaaS - Anonymization as a Service

ARX Web API Service

[API documentation](https://oslomet-arx-as-a-service.github.io/ARXaaS)

#### Running the service locally

##### Run server on localhost as a Docker container
1. Make sure Docker Desktop is running.
2. Pull the Docker image
```bash
docker pull arxaas/aaas
```
3. Run the Docker image
```bash
docker run arxaas/aaas
```

##### Run server on localhost from jar (download ARXaaS executable jar from Maven and execute it)
1. Go to https://search.maven.org/search?q=g:no.oslomet and download the latest version of ARXaaS
2. Run the jar file
```bash
java -jar <path to jar>
```

#### Customize your ARXaaS server
1. Clone the ARXaaS project from github
2. Download ARX Java library with sources from https://arx.deidentifier.org/downloads/
3. Install the ARX library in your local Maven repostitory
```bash
mvn -q install:install-file -Dfile=<relative path to ARX deidentifier library jar file from spring project root directory> -DgroupId=org.deidentifier -DartifactId=libarx -Dversion=3.7.1 -Dpackaging=jar
```
You now have access to your own customizable ARXaaS project. You can make changes to the project, compile it, then run your own custom ARXaaS server.

#### HTTPS configuration
##### Note that ARXaaS uses HTTP by default.
[Documentation](READMEHTTPS.md)
