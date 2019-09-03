package com.dm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PessoaAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PessoaAuthServerApplication.class, args);
	}
}