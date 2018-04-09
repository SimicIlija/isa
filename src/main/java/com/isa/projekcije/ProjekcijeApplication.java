package com.isa.projekcije;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class ProjekcijeApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProjekcijeApplication.class, args);
	}
}
