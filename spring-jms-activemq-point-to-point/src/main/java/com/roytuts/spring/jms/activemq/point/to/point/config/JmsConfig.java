package com.roytuts.spring.jms.activemq.point.to.point.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import com.roytuts.spring.jms.activemq.point.to.point.consumer.MessageConsumer;

@Configuration
@PropertySource(value = "classpath:application.properties") // optional
public class JmsConfig {

	@Autowired
	private Environment env;

	@Autowired
	private MessageConsumer messageConsumer;

	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(env.getProperty("JMS.BROKER.URL"));
		return connectionFactory;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory());
		return cachingConnectionFactory;
	}

	@Bean
	public Queue queue() {
		Queue queue = new ActiveMQQueue(env.getProperty("JMS.QUEUE.NAME"));
		return queue;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());

		jmsTemplate.setDefaultDestination(queue());

		return jmsTemplate;
	}

	@Bean
	public MessageListenerContainer messageListenerContainer() {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();

		messageListenerContainer.setDestination(queue());
		messageListenerContainer.setMessageListener(messageConsumer);
		messageListenerContainer.setConnectionFactory(connectionFactory());

		return messageListenerContainer;
	}

}
