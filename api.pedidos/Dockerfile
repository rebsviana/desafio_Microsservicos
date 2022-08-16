# define base docker image
FROM openjdk:11
LABEL maintainer="ciandt.com"
ADD target/api.pedidos-0.0.1-SNAPSHOT.jar api.pedidos.jar
ENTRYPOINT ["java", "-jar", "api.pedidos.jar"]