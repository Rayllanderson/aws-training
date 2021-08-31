FROM openjdk:11.0.11-jdk-oracle
ARG JAR_FILE=build/libs/*-all.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENV APP_NAME aws-training
ENTRYPOINT ["java", "-jar", "/app.jar"]