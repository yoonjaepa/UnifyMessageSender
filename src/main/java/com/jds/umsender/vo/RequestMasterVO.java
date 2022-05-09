package com.jds.umsender.vo;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data public  class RequestMasterVO {
	
	
	private String				requestMasterUid;
	private LocalDateTime		requestRecieveDttm;
	private String 				requestChannelId;
	private Boolean 			isTemplate;
	private Boolean 			isMass;
	private String				templateId;
	private String  			mediaTypeCd;
	private String  			endUserIp;
	private int	  				messageCnt;
	private String				reservedStr1;
	private String				reservedStr2;
	private RequestDetailVO[] 	requestDetail;
	
	public RequestMasterVO() {
		
		//	요청시각 생성 
		setRequestRecieveDttm(LocalDateTime.now());
	}
	
	
	
}