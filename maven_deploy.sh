#!/bin/bash
echo $GPG_SECRET_KEYS | base64 --decode | $GPG_EXECUTABLE --import
mvn --settings .maven.xml deploy -Dmaven.test.skip=true -Dmaven.install.skip=true -B -P release