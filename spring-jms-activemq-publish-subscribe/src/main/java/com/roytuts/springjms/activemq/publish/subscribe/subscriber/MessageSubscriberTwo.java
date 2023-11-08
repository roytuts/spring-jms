package com.roytuts.springjms.activemq.publish.subscribe.subscriber;

import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@Component
public class MessageSubscriberTwo implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				String msg = ((TextMessage) message).getText();
				System.out.println("Message consumed by MessageSubscriber2 : " + msg);
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
	}

}
