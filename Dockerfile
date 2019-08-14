FROM openjdk:11.0-jre-slim
MAINTAINER Maciej Główka <maciej.glowka@decerto.pl>
MAINTAINER Piotr Marciniak <piotr.marciniak@decerto.pl>

ENV JAVA_OPTS="$JAVA_OPTS -Duser.language=pl -Duser.region=PL"

COPY ./build/libs/dictionary-engine-0.1-SNAPSHOT.jar /app/dictionary-engine.jar
COPY ./docker/docker_application.yml /root/conf/application.yml

EXPOSE 8082

WORKDIR /app

ENTRYPOINT java $JAVA_OPTS -jar dictionary-engine.jar
