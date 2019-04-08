[![Build Status](https://travis-ci.com/oslomet-arx-as-a-service/AaaS.svg?branch=master)](https://travis-ci.com/oslomet-arx-as-a-service/AaaS)
[![Maintainability](https://api.codeclimate.com/v1/badges/0a0bacd7d83928eee14c/maintainability)](https://codeclimate.com/github/oslomet-arx-as-a-service/AaaS/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/0a0bacd7d83928eee14c/test_coverage)](https://codeclimate.com/github/oslomet-arx-as-a-service/AaaS/test_coverage)
[![Maven Central](https://img.shields.io/maven-central/v/no.oslomet/aaas.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22no.oslomet%22%20AND%20a:%22aaas%22)
[![Javadocs](http://javadoc.io/badge/no.oslomet/aaas.svg)](http://javadoc.io/doc/no.oslomet/aaas)

# AaaS - Anonymization-as-a-Service

ARX Web API Service

[API documentation](https://oslomet-arx-as-a-service.github.io/AaaS/)

#### Development

1. clone the project
2. Download ARX java library with sources from https://arx.deidentifier.org/downloads/
3. Install the ARX library in your local maven repostitory
```bash
mvn -q install:install-file -Dfile=src/main/resources/libarx-3.7.1.jar -DgroupId=org.deidentifier -DartifactId=libarx -Dversion=3.7.1 -Dpackaging=jar
```

You are ready to go!

#### HTTPS configuration

[Documentation](READMEHTTPS.md)
