FROM maven:3-openjdk-11-slim as MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build

RUN mvn package

FROM adoptopenjdk/openjdk11-openj9

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/ApiCrud-*.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "app.jar" ]