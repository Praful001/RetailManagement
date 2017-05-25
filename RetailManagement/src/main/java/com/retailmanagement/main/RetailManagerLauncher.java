package com.retailmanagement.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com.retailmanagement.*" })
@SpringBootApplication
public class RetailManagerLauncher {

	public static void main(String[] args) {
		SpringApplication.run(RetailManagerLauncher.class, args);
	}

}