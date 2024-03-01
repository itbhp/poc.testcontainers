MVN_DOCKER_IMAGE=maven:3.9.6-amazoncorretto-21-al2023

build:
	docker run --rm \
    -v $(shell pwd):/opt/maven -w /opt/maven \
	${MVN_DOCKER_IMAGE} mvn clean install
##
# targets that do not produce output files
##
.PHONY: build