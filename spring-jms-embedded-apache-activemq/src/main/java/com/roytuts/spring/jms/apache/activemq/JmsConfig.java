package com.roytuts.spring.jms.apache.activemq;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

@Configuration
public class JmsConfig {

	@Value("${jms.broker.url}")
	private String brokerUrl;

	@Value("${jms.queue.name}")
	private String queueName;

	@Autowired
	private MessageConsumer messageConsumer;

	@Bean
	public BrokerService broker() {
		BrokerService broker = new BrokerService();
		try {
			broker.addConnector(brokerUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		broker.setPersistent(false);

		return broker;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
	}

	@Bean
	public Destination destination() {
		return new ActiveMQQueue(queueName);
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Destination destination) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setDefaultDestination(destination);

		return jmsTemplate;
	}

	@Bean
	public MessageListenerContainer defaultMessageListenerContainer(ConnectionFactory connectionFactory,
			Destination destination) {
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
		defaultMessageListenerContainer.setDestination(destination);
		defaultMessageListenerContainer.setMessageListener(messageConsumer);

		return defaultMessageListenerContainer;
	}

}
