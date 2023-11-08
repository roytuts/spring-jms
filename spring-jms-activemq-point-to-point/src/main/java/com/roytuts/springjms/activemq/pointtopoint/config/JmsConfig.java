package com.roytuts.springjms.activemq.pointtopoint.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import com.roytuts.springjms.activemq.pointtopoint.consumer.MessageConsumer;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;

@Configuration
public class JmsConfig {

	@Value("${broker.url}")
	private String brokerUrl;

	@Value("${queue.name}")
	private String queueName;

	@Autowired
	private MessageConsumer messageConsumer;

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(brokerUrl);
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory(ConnectionFactory connectionFactory) {
		return new CachingConnectionFactory(connectionFactory);
	}

	@Bean
	public Queue queue() {
		return new ActiveMQQueue(queueName);
	}

	@Bean
	public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory, Queue queue) {
		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
		jmsTemplate.setDefaultDestination(queue);

		return jmsTemplate;
	}

	@Bean
	public MessageListenerContainer messageListenerContainer(CachingConnectionFactory cachingConnectionFactory,
			Queue queue) {
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(cachingConnectionFactory);
		defaultMessageListenerContainer.setDestination(queue);
		defaultMessageListenerContainer.setMessageListener(messageConsumer);

		return defaultMessageListenerContainer;
	}

}
