package com.jds.umsender.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
	
	//private final static Logger logger = LoggerFactory.getLogger("Common.JsonUtil");
	
	public static String object2Json(Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.registerModule(new JavaTimeModule());
		
		String message ="";
		
		try {
			message = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error(e.toString());
		}
		
		return message;
		
	}	//	end object2Json()
	
	public static Object Json2Object(String json, Class<?> className) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.registerModule(new JavaTimeModule());
		
		Object obj = null;
		
		try {
			obj = mapper.readValue(json, className);
			
			} catch (JsonMappingException e) {
				log.error(e.toString());
		} catch (JsonProcessingException e) {
			log.error(e.toString());
		}
		
		return obj;
		
	}
	

}
