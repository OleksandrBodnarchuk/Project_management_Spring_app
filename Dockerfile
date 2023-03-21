# Define base image for this image
FROM eclipse-temurin:17
# Define maintainer - who is the author
LABEL maintainer="obodnarchuk@outlook.com"
# Create working directory in the docker container
WORKDIR /app
# Copy jar file from project to docker container
COPY target/yolloapp-0.0.1-SNAPSHOT.jar /app/yolloapp.jar
# Define instruction to run .jar file
ENTRYPOINT [ "java", "-jar", "yolloapp.jar" ]

