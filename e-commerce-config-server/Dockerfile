#Alpine por que é mais leve
FROM openjdk:8-jdk-alpine
MAINTAINER Diego Oliveira <dmjesus89@gmail.com>

# configuracao de variveis.
ENV PORT = 9090

# Adicinar jar file para rodar com entrypoint 
ADD target/e-commerce-config-server*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# expose a porta 
EXPOSE 9090