#Alpine por que é mais leve
FROM openjdk:8-jdk-alpine
MAINTAINER Diego Oliveira <dmjesus89@gmail.com>

# configuracao de variveis.
ENV PORT = 9091

# Adicinar jar file para rodar com entrypoint 
ADD target/e-commerce-eureka-server*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# expose a porta 
EXPOSE 9091