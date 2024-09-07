FROM maven:3-openjdk-11 AS build

ENV HOME=/usr/app

RUN mkdir -p $HOME

WORKDIR $HOME

COPY . $HOME

RUN --mount=type=cache,target=/root/.m2 mvn clean -U -B package -Dmaven.test.skip=true

## Stage Two: Deployment

FROM openjdk

RUN mkdir /app

WORKDIR /app

COPY --from=build /usr/app/target/quarkus-base-template-0.0.1-SNAPSHOT-runner.jar /app

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "quarkus-base-template-0.0.1-SNAPSHOT-runner.jar"]

