FROM openjdk:11
ARG JAR_FILE=target/cli-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY ${JAR_FILE} app.jar
CMD ["java","-jar","/app.jar"]

