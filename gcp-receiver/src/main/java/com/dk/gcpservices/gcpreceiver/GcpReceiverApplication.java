package com.dk.gcpservices.gcpreceiver;

import com.dk.gcpservices.gcpreceiver.models.SampleMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
public class GcpReceiverApplication {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String SUBSCRIPTION_NAME = "exampleSubscription";

	public static void main(String[] args) {
		SpringApplication.run(GcpReceiverApplication.class, args);
	}

	@Bean
	public MessageChannel pubsubInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("pubsubInputChannel") MessageChannel inputChannel,
			PubSubTemplate pubSubTemplate
	) {
		PubSubInboundChannelAdapter adapter =
				new PubSubInboundChannelAdapter(pubSubTemplate, SUBSCRIPTION_NAME);

		adapter.setOutputChannel(inputChannel);

		return adapter;
	}


	// Receive Message then process (in our case we just use logger to log the data)
	@ServiceActivator(inputChannel = "pubsubInputChannel")
	public void messageReceiver(String payload) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		SampleMessage sampleMessage = objectMapper.readValue(payload, SampleMessage.class);
		logger.info("Payload here: " + sampleMessage.toString());
		logger.info(sampleMessage.getMessage());
		logger.info(sampleMessage.getDescription());
	}


}
