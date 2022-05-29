package com.jds.umsender.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.jds.umsender.vo.RequestMasterVO;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value("${kafka.my.push.topic.group.name}")
	private String groupid;
	
	@Bean
	public ConsumerFactory<String, RequestMasterVO> requestVOConsumerFactory(){
		JsonDeserializer<RequestMasterVO> deserializer = requestVoJsonDeserializer();
		
		return new DefaultKafkaConsumerFactory<>(
				consumerFactoryConfig(deserializer),
				new StringDeserializer(), deserializer);
				
				
	}	//	end ConsumerFactory()
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, RequestMasterVO> requestKafkaListenerConatinerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, RequestMasterVO> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory(requestVOConsumerFactory());
		
		return factory;
	}
	
	
	private Map<String, Object> consumerFactoryConfig(JsonDeserializer<RequestMasterVO> deserializer) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupid);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		
		return props;
	}
	
	private JsonDeserializer<RequestMasterVO> requestVoJsonDeserializer() {
		JsonDeserializer<RequestMasterVO> deserializer = new JsonDeserializer<>(RequestMasterVO.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		return deserializer;		
		
	}
	
	

}
