
![ARXaaS logo](ARXaaS_hero_small.png)
[![CircleCI](https://circleci.com/gh/navikt/ARXaaS/tree/master.svg?style=svg)](https://circleci.com/gh/navikt/ARXaaS/tree/master)
[![Maintainability](https://api.codeclimate.com/v1/badges/b33732ce938a22920733/maintainability)](https://codeclimate.com/github/navikt/ARXaaS/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/b33732ce938a22920733/test_coverage)](https://codeclimate.com/github/navikt/ARXaaS/test_coverage)
[![Maven Central](https://img.shields.io/maven-central/v/no.nav/arxaas.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22no.nav%22%20AND%20a:%22arxaas%22)
[![Javadocs](https://www.javadoc.io/badge/no.nav/arxaas.svg)](https://www.javadoc.io/doc/no.nav/arxaas)

# ARXaaS - Anonymization as a Service

ARXaaS is a data anonymization service built ontop of the [ARX](https://arx.deidentifier.org/) library. It exposes HTTP REST endpoints for analyzing re-identification risk, anonymizing datasets and creating generalization hierarchies.

#### Documentation

[API documentation](https://navikt.github.io/ARXaaS/)

#### Running the service locally

##### Run server on localhost as a Docker container
1. Make sure Docker Desktop is running.
2. Pull the Docker image
```bash
docker pull navikt/arxaas
```
3. Run the Docker image
```bash
docker run -p 8080:8080 navikt/arxaas
```

##### Run server on localhost from jar (download ARXaaS executable jar from Maven and execute it)
1. Go to [![Maven Central](https://img.shields.io/maven-central/v/no.nav/arxaas.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22no.nav%22%20AND%20a:%22arxaas%22) and download the latest version of ARXaaS
2. Run the jar file
```bash
java -jar <path to jar>
```

#### Development
1. Clone the ARXaaS project from Github
2. Download ARX Java library with sources from https://arx.deidentifier.org/downloads/
3. Install the ARX library in your local Maven repostitory
```bash
mvn -q install:install-file -Dfile=<relative path to ARX deidentifier library jar file from spring project root directory> -DgroupId=org.deidentifier -DartifactId=libarx -Dversion=3.8.0 -Dpackaging=jar
```
You now have access to your own customizable ARXaaS project. You can make changes to the project, compile it, then run your own custom ARXaaS server.

#### HTTPS configuration
##### Note that ARXaaS uses HTTP by default.
[Documentation](READMEHTTPS.md)
