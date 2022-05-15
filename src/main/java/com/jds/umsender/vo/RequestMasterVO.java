package com.jds.umsender.vo;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public  class RequestMasterVO extends BaseAuditVO {
	
	//private final Logger logger	= LoggerFactory.getLogger(this.getClass());
	
	private String				requestMasterUid;
	private String				channelUid;
	private String 				bizCustUid;
	private LocalDateTime		requestRecieveDttm;
	private Boolean 			isTemplate;
	private Boolean 			isMass;
	private String				requestChannelIp;
	private int		  			messageCount;
	private String  			requestDoc;
	private String				reservedStr1;
	private String				reservedStr2;
	private String				requestStatusCd;
	private LocalDateTime		requestStatusChangeDttm;
	private String				errorCode;
	private String				errorDetail;
	private RequestDetailVO[] 	requestDetail;
	
	//	audit columns
	/*
	 * private LocalDateTime registDttm; private String registId; private
	 * LocalDateTime modifyDttm; private String modifyId;
	 */

	public RequestMasterVO() {		
		super();
		//	요청시각 생성 
		setRequestRecieveDttm(LocalDateTime.now());
		setRequestStatusChangeDttm(LocalDateTime.now());
		setRequestMasterUid(UUID.randomUUID().toString());
		setRequestStatusCd("RS001");
		
		
	}
	
	//	Detail 에 마스터 UUID를 세팅한다. 
	public void setRequestDtlMasterUid(String masterUid) {
		
		for(RequestDetailVO detail :requestDetail) {
			detail.setRequestMasterUid(masterUid);
		}
	}
	
}