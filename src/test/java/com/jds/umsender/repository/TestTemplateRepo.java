package com.jds.umsender.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jds.umsender.vo.MessageTemplateVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TestTemplateRepo {
	
	@Autowired
	TemplateRepo repo;

	private Map<String, String> setParameter() {
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("templateCd", "T0001");
		params.put("mediaCd", "MI001");
		
		return params;
	}
	
	@Test
	void test() {
		MessageTemplateVO vo = repo.findByTemplateCode(setParameter());
		
		log.info("registId : {} ",vo.getRegistId());
		
	}

}
