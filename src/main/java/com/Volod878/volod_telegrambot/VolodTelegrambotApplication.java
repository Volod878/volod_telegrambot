package com.Volod878.volod_telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VolodTelegrambotApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolodTelegrambotApplication.class, args);
	}

}
