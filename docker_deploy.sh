#!/bin/bash

cat google_cloud_api_key.json | docker login -u _json_key --password-stdin gcr.io

export IMAGE_NAME=arxaas/aaas
docker build -t $IMAGE_NAME:$TRAVIS_COMMIT .
docker tag gcr.io/$IMAGE_NAME:$TRAVIS_COMMIT $IMAGE_NAME:latest
docker push gcr.io/carbon-sunup-229608/$IMAGE_NAME
