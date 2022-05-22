package com.jds.umsender.repository;

import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jds.umsender.sql.MediaInfoSql;
import com.jds.umsender.vo.MediaInfoVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MediaInfoRepo {
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public MediaInfoRepo(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	
	public MediaInfoVO findByMediaCode(Map<String, String> params) {
		
		MediaInfoVO mediaInfo = (MediaInfoVO)namedParameterJdbcTemplate
				.queryForObject(MediaInfoSql.SELECT_MEDIA_INFO, params, 
						new BeanPropertyRowMapper<MediaInfoVO>(MediaInfoVO.class));
		
		//log.info("MediaInfo : {}",mediaInfo.toString() );
		
		return mediaInfo;
	}
}
