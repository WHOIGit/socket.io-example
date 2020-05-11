FROM maven:3-openjdk-15 AS builder

COPY src /app/src/
COPY pom.xml /app/
WORKDIR /app
RUN mvn compile assembly:single


FROM openjdk:15

WORKDIR /app
COPY --from=builder \
    /app/target/publisher-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /app/

CMD java -cp publisher-1.0-SNAPSHOT-jar-with-dependencies.jar \
    edu.whoi.publisher.App "$URL" "$TOKEN"
