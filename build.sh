#!/bin/bash

docker kill quarkus-base-template

docker rm quarkus-base-template

docker rmi quarkus-base-template:latest

DOCKER_BUILDKIT=1 docker buildx build --platform linux/amd64 -f  Dockerfile -t quarkus-base-template:latest .

docker compose up quarkus-base-template