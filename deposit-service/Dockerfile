FROM openjdk:17-jdk-alpine
EXPOSE 8079
COPY build/libs/deposit-service-1.0-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "deposit-service-1.0-SNAPSHOT.jar"]
#You are building a Docker image from Windows against a non-Windows Docker host.
#All files and directories added to build context will have '-rwxr-xr-x' permissions. It is recommended to
#double check and reset permissions for sensitive files and directories.
