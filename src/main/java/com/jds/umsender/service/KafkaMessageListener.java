package com.jds.umsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.jds.umsender.component.IfTemplateMessageSender;
import com.jds.umsender.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaMessageListener {

	@Autowired
	IfTemplateMessageSender thundermailMessageSender;
	
	@KafkaListener(topics="${kafka.my.push.topic.name}"
			, groupId="${kafka.my.push.topic.group.name}" 
			, containerFactory="requestKafkaListenerConatinerFactory")
	public void listenWithHeaders(@Payload RequestMasterVO request, 
			@Headers MessageHeaders messageHeaders) {	
		
		log.info("Received Message : '{}' / Headers : '{}' \n\n" 
				, request.toString(), messageHeaders);
		
		
		thundermailMessageSender.sendMessages(request);
		
	}
	
	
	
}
