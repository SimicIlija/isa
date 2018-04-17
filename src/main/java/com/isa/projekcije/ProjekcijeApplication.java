package com.isa.projekcije;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;



@SpringBootApplication()
@EnableAsync
public class ProjekcijeApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProjekcijeApplication.class, args);
	}
}
