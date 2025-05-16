package com.example.MiraiElectronics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiraiElectronicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiraiElectronicsApplication.class, args);
	}

}