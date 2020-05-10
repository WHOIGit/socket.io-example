FROM maven:3-openjdk-15 AS builder

COPY redis-pub /app
WORKDIR /app
RUN mvn compile assembly:single


FROM openjdk:15

WORKDIR /app
COPY --from=builder \
    /app/target/redis-pub-1.0-SNAPSHOT-jar-with-dependencies.jar \
    /app/

CMD java -cp redis-pub-1.0-SNAPSHOT-jar-with-dependencies.jar edu.whoi.redispub.App $REDIS
