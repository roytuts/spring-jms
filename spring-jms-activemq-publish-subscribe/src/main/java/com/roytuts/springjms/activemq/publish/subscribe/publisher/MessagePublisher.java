package com.roytuts.springjms.activemq.publish.subscribe.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendMessage(final String message) {
		jmsTemplate.convertAndSend(message);
	}

}
