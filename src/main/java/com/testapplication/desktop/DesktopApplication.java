package com.testapplication.desktop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication
public class DesktopApplication {

	public static void main(String[] args) {


		SpringApplication.run(DesktopApplication.class, args);

	}

}
