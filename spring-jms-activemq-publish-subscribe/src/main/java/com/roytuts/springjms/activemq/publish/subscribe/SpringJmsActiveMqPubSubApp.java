package com.roytuts.springjms.activemq.publish.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.roytuts.springjms.activemq.publish.subscribe.publisher.MessagePublisher;

@SpringBootApplication
public class SpringJmsActiveMqPubSubApp implements CommandLineRunner {

	@Autowired
	private MessagePublisher messagePublisher;

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsActiveMqPubSubApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		messagePublisher.sendMessage("This is a message that will be posted into Topic.");
	}

}
