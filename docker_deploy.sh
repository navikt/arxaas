#!/bin/bash

docker login -u $DOCKER_USER -p $DOCKER_PASS
export IMAGE_NAME=arxaas/aaas
docker build -t $IMAGE_NAME:$TRAVIS_COMMIT .
docker tag $IMAGE_NAME:$TRAVIS_COMMIT $IMAGE_NAME:latest
docker push $IMAGE_NAME

docker login -u _json_key -p $gcloud_service_user_json gcr.io
export IMAGE_NAME=arxaas/aaas
docker build -t $IMAGE_NAME:$TRAVIS_COMMIT .
docker tag $IMAGE_NAME:$TRAVIS_COMMIT $IMAGE_NAME:latest
docker push $IMAGE_NAME