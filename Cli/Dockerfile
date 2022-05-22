# Pull base image
FROM openjdk:8
 
LABEL maintainer="javaCli"
 
RUN mkdir /app
	
ARG JAR_FILE 
 
ENV JAVA_XMS=512 JAVA_XMX=1024
 
COPY /target/LoginHealthInspecCLI-1.0-SNAPSHOT-jar-with-dependencies /app/app.jar
 
WORKDIR /app
ENTRYPOINT java -Xms${JAVA_XMS}m -Xmx${JAVA_XMX}m \
    -Djava.security.egd=file:/dev/./urandom -jar app.jar