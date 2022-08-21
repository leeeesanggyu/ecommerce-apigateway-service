# ecommerce-api-gateway-service

### docker run 명령어
docker run -d -p 8000:8000 --network ecommerce-network -e "spring.cloud.config.url=http://config-service:8888" -e "spring.rabbitmq.host=rabbitmq" -e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka" --name api-gateway-servicve salgu1998/api-gateway-service:1.0