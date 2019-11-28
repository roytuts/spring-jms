package com.roytuts.spring.jms.activemq.publish.subscribe.subscriber;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

@Component
public class MessageSubscriberOne implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				String msg = ((TextMessage) message).getText();
				System.out.println("Message consumed by MessageSubscriber1 : " + msg);
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
	}

}
