package com.dm.config;

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
