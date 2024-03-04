package com.todoapplication.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TodoapplicationgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoapplicationgatewayApplication.class, args);
	}

}
