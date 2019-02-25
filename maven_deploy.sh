#!/bin/bash

mvn --settings .maven.xml deploy -Dmaven.test.skip=true -Dmaven.install.skip=true -B -P release