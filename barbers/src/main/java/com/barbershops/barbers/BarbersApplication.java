package com.barbershops.barbers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BarbersApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbersApplication.class, args);
	}

}
