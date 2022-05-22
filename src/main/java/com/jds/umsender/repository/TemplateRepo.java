package com.jds.umsender.repository;

import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jds.umsender.sql.TemplateSql;
import com.jds.umsender.vo.MessageTemplateVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TemplateRepo {
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public TemplateRepo(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	
	public MessageTemplateVO findByTemplateCode(Map<String, String> params) {
		
		MessageTemplateVO messageTemp  
			= (MessageTemplateVO) namedParameterJdbcTemplate
			.queryForObject(TemplateSql.SELECT_TEMPLATE_INFO,params, 
						new BeanPropertyRowMapper<MessageTemplateVO>(MessageTemplateVO.class));		
		
		//log.info("MessageTemplate : {} ", messageTemp.toString());
		
		return messageTemp;
		
	}
}
