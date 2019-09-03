package com.dm.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PessoaEurekaServerApplication  {
	
	public static void main(String[] args) {
		SpringApplication.run(PessoaEurekaServerApplication.class, args);
	}

}
