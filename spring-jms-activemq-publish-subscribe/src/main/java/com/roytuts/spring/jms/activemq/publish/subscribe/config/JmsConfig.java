package com.roytuts.spring.jms.activemq.publish.subscribe.config;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import com.roytuts.spring.jms.activemq.publish.subscribe.subscriber.MessageSubscriberOne;
import com.roytuts.spring.jms.activemq.publish.subscribe.subscriber.MessageSubscriberTwo;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class JmsConfig {

	@Autowired
	private Environment env;

	@Autowired
	private MessageSubscriberOne messageSubscriberOne;

	@Autowired
	private MessageSubscriberTwo messageSubscriberTwo;

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
	public Topic topic() {
		Topic topic = new ActiveMQTopic(env.getProperty("JMS.TOPIC.NAME"));
		return topic;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());

		jmsTemplate.setDefaultDestination(topic());
		jmsTemplate.setPubSubDomain(true);

		return jmsTemplate;
	}

	@Bean(name = "messageListenerContainerOne")
	public MessageListenerContainer messageListenerContainerOne() {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();

		// messageListenerContainer.setPubSubDomain(true);
		messageListenerContainer.setDestination(topic());
		messageListenerContainer.setMessageListener(messageSubscriberOne);
		messageListenerContainer.setConnectionFactory(connectionFactory());

		return messageListenerContainer;
	}

	@Bean(name = "messageListenerContainerTwo")
	public MessageListenerContainer messageListenerContainerTwo() {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();

		// messageListenerContainer.setPubSubDomain(true);
		messageListenerContainer.setDestination(topic());
		messageListenerContainer.setMessageListener(messageSubscriberTwo);
		messageListenerContainer.setConnectionFactory(connectionFactory());

		return messageListenerContainer;
	}

}
