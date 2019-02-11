#!/bin/bash

docker login -u $DOCKER_USER -p $DOCKER_PASS
export IMAGE_NAME=arxaas/aaas
docker build -t $IMAGE_NAME:$TRAVIS_COMMIT .
docker tag $IMAGE_NAME:$TRAVIS_COMMIT $IMAGE_NAME:latest
docker push $IMAGE_NAME
