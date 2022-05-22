package com.jds.umsender.repository;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jds.umsender.vo.MediaInfoVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class TestMediaInfoReop {

	
	@Autowired
	MediaInfoRepo repo;
	
	@Test
	void testFindByMediaCode() {
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("mediaCd","MI001");
		
		MediaInfoVO mediaInfo = repo.findByMediaCode(param);
		
		
		log.info(mediaInfo.toString());
	}
	
}
