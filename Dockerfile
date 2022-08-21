FROM openjdk:17-ea-slim
VOLUME /tmp
COPY target/api-gateway-service-1.0.jar api-gateway-service.jar
ENTRYPOINT ["java", "-jar", "api-gateway-service.jar"]