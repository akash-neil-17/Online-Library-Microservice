package com.micro.configServerv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerv2Application {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerv2Application.class, args);
	}

}
