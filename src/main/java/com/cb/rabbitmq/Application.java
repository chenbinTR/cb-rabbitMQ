package com.cb.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("rabbitmq demo start");
		SpringApplication.run(Application.class, args);
	}

}
