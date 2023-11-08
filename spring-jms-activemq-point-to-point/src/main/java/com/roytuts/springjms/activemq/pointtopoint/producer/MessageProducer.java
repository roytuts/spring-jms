package com.roytuts.springjms.activemq.pointtopoint.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendMessageToDefaultDestination(final String message) {
		jmsTemplate.convertAndSend(message);
	}

}
