### Pessoa API ###

### Configurações: ### 
Banco Utilizado: H2 em memória.
Java: JDK8

##  Console Banco de Dados  ## 
http://localhost:8080/h2-console 
user:sa 
password:sa
url: jdbc:h2:mem:db;DB_CLOSE_DELAY=-1


###  Servidores: ### 
A aplicação roda localmente já com um container tomcat embutido, porém é possível subir em um servidor web 
Para gerar um pacote deve ser executado o comando maven ( mvn clean instal) na pasta raiz do projeto ou pela IDE eclipse maven build (clean install)

### Rodar a aplicação em DEV:  ### 
Executar a classe AvaliacaoApplication.java, como Java application pela IDE (Eclipse) - Run As Java Application.

### Rodar a aplicação em HOMO ou PROD: ### 
Para realizar deploy em outros ambientes deve ser criar outros arquivos de configuração na pasta resource, application-homo.yml ou application-prod.yml e configurar todas as informações necessário ( DataSource, Porta e etc)

### Swagger: ### 
http://localhost:8080/swagger-ui.html 
É possivel consultar a documentação e realizar as chamadas da api pelo swagger 

### Testes: ### 	
Os testes da camada service foram mockados e usei a configuração do spring para levantar o container do spring.
Também pode ser criado um banco de teste ou banco em mémoria.



### Exemplos de Request: ### .
POST: 
http://localhost:8080/api/pessoas/

### Payload: ###
{
  "cpf": "04124842528",
  "dataNascimento": "2016-01-01T10:24",
  "nome": "Carlos Eduardo"
}


GET: 
http://localhost:8080/api/pessoas/1

PUT: 
http://localhost:8080/api/pessoas/

### Payload: ###
{
  "codigo": 1,
  "cpf": "09834535319",
  "dataNascimento": "2016-01-01T10:24",
  "nome": "Jose luiz"
}

DELETE: 
http://localhost:8080/api/pessoas/1



