package com.moneymoney.eurekaserver.eurekhaservicehello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@EnableEurekaServer
@SpringBootApplication
public class EurekhaServiceHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekhaServiceHelloApplication.class, args);
	}

}

