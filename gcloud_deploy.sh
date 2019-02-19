#!/bin/bash

cat google_cloud_api_key.json | docker login -u _json_key --password-stdin gcr.io

export IMAGE_NAME=arxaas/aaas
docker build -t $IMAGE_NAME:$TRAVIS_COMMIT .
docker tag $IMAGE_NAME:$TRAVIS_COMMIT $GOOGLE_DOCKER_REPO/$IMAGE_NAME:latest
docker push $GOOGLE_DOCKER_REPO/$IMAGE_NAME:latest
