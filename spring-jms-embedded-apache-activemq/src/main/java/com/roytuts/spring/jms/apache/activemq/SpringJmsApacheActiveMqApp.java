package com.roytuts.spring.jms.apache.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJmsApacheActiveMqApp implements CommandLineRunner {

	@Autowired
	private MessageProducer messageProducer;

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsApacheActiveMqApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		messageProducer.sendMessageToDestination("Send this message to destination");
	}

}
