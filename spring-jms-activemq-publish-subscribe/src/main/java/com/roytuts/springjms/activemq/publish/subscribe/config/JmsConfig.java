package com.roytuts.springjms.activemq.publish.subscribe.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import com.roytuts.springjms.activemq.publish.subscribe.subscriber.MessageSubscriberOne;
import com.roytuts.springjms.activemq.publish.subscribe.subscriber.MessageSubscriberTwo;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Topic;

@Configuration
public class JmsConfig {

	@Value("${broker.url}")
	private String brokerUrl;

	@Value("${topic.name}")
	private String topicName;

	@Autowired
	private MessageSubscriberOne messageSubscriberOne;

	@Autowired
	private MessageSubscriberTwo messageSubscriberTwo;

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(brokerUrl);
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory(ConnectionFactory connectionFactory) {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		return cachingConnectionFactory;
	}

	@Bean
	public Topic topic() {
		Topic topic = new ActiveMQTopic(topicName);
		return topic;
	}

	@Bean
	public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory, Topic topic) {
		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);

		jmsTemplate.setDefaultDestination(topic);
		jmsTemplate.setPubSubDomain(true);

		return jmsTemplate;
	}

	@Bean(name = "messageListenerContainerOne")
	public MessageListenerContainer messageListenerContainerOne(CachingConnectionFactory cachingConnectionFactory,
			Topic topic) {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();

		// messageListenerContainer.setPubSubDomain(true);
		messageListenerContainer.setDestination(topic);
		messageListenerContainer.setMessageListener(messageSubscriberOne);
		messageListenerContainer.setConnectionFactory(cachingConnectionFactory);

		return messageListenerContainer;
	}

	@Bean(name = "messageListenerContainerTwo")
	public MessageListenerContainer messageListenerContainerTwo(CachingConnectionFactory cachingConnectionFactory,
			Topic topic) {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();

		// messageListenerContainer.setPubSubDomain(true);
		messageListenerContainer.setDestination(topic);
		messageListenerContainer.setMessageListener(messageSubscriberTwo);
		messageListenerContainer.setConnectionFactory(cachingConnectionFactory);

		return messageListenerContainer;
	}

}
