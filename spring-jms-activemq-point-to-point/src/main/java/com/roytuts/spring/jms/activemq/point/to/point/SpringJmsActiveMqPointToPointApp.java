package com.roytuts.spring.jms.activemq.point.to.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.roytuts.spring.jms.activemq.point.to.point.producer.MessageProducer;

@SpringBootApplication(scanBasePackages = "com.roytuts.spring.jms.activemq.point.to.point")
public class SpringJmsActiveMqPointToPointApp implements CommandLineRunner {

	@Autowired
	private MessageProducer messageProducer;

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsActiveMqPointToPointApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		messageProducer.sendMessageToDefaultDestination("Send this message to default destination.");
	}

}
