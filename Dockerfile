# Using the Java 17 base image
FROM openjdk:17

# Installing the working directory inside the container
WORKDIR /app

# Copy jar files with modules inside the container
ARG JAR_FILE=/out/artifacts/api_gateway_java_spring_jar/api-gateway-java-spring.jar
COPY ${JAR_FILE} /app/
ARG JAR_FILE2=/out/artifacts/mft_backend_client_java_spring_jar/mft-backend-client-java-spring.jar
COPY ${JAR_FILE2} /app/
ARG JAR_FILE3=/out/artifacts/server_java_spring_jar/server-java-spring.jar
COPY ${JAR_FILE3} /app/

# Set ports for modules
EXPOSE 8761
EXPOSE 8765
EXPOSE 9000

# Start modules
CMD java -jar api-gateway-java-spring.jar --server.port=8765
CMD java -jar mft-backend-client-java-spring.jar --server.port=9000
CMD java -jar server-java-spring.jar --server.port=8761