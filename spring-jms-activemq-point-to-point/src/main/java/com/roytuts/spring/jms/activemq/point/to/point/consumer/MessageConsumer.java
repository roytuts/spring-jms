package com.roytuts.spring.jms.activemq.point.to.point.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

@Component
public class MessageConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				String msg = ((TextMessage) message).getText();
				System.out.println("Message has been consumed : " + msg);
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
	}

}
