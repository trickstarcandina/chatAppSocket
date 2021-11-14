FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} chat-server.jar
ENTRYPOINT ["java", "-jar", "/chat-server.jar"]
EXPOSE 8081