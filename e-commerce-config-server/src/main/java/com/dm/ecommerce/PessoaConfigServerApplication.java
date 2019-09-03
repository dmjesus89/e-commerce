package com.dm.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PessoaConfigServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PessoaConfigServerApplication.class, args);
	}

}
