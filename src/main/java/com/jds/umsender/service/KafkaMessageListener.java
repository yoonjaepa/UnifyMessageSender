package com.jds.umsender.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.jds.umsender.vo.RequestMasterVO;

@Service
public class KafkaMessageListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@KafkaListener(topics="${kafka.my.push.topic.name}"
			, groupId="${kafka.my.push.topic.group.name}" 
			, containerFactory="requestKafkaListenerConatinerFactory")
	public void listenWithHeaders(@Payload RequestMasterVO request, 
			@Headers MessageHeaders messageHeaders) {
		
		
		
		logger.info("Received Message : '{}' / Headers : '{}' \n\n" 
				, request.toString(), messageHeaders);
		
		
	}
}
