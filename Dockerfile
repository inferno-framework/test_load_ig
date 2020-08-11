FROM openjdk:8 AS build
WORKDIR /home
# Grab Gradle first so it can be cached
COPY gradle gradle
COPY gradlew .
RUN ./gradlew --version

COPY settings.gradle .
COPY gradle.properties .
COPY build.gradle.kts .
COPY src src

RUN ./gradlew build
RUN tar -xvf build/distributions/TestLoadIg-*.tar

FROM openjdk:8
WORKDIR /home
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

COPY --from=build /home/TestLoadIg-* .
RUN bin/TestLoadIg prepare

CMD ["./bin/TestLoadIg", "run"]
