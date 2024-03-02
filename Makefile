MVN_DOCKER_IMAGE=maven:3.9.6-amazoncorretto-21-al2023

build:
	docker run --rm \
    -v $(shell pwd):/opt/maven -w /opt/maven \
    -v /var/run/docker.sock:/var/run/docker.sock \
	${MVN_DOCKER_IMAGE} mvn clean install

compose-up:
	docker compose up

run:
	docker run --rm \
    -v $(shell pwd):/opt/maven -w /opt/maven \
    ${MVN_DOCKER_IMAGE} mvn spring-boot:run

start-dev: build compose-up run

##
# targets that do not produce output files
##
.PHONY: build, run, start-dev