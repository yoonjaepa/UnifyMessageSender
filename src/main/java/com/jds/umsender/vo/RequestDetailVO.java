package com.jds.umsender.vo;


import java.util.Map;

import org.apache.kafka.common.Uuid;

import com.jds.umsender.common.JsonUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RequestDetailVO extends BaseAuditVO {
	
	private String 		requestMasterUid;
	private String		requestDetailUid;
	private int			messageNo;
	private String		mediaTypeCd;
	private String		templateCd;
	private String		messageTitle;
	private Map<String, String>	recieverInfo;
	private Map<String, String>	templateVariable;
	private Map<String, String> senderInfo;
	private String		recieverInfoJson;
	private	String		templateVariableJson;
	private String		senderInfoJson;
	private String		messageContents;
	

	public RequestDetailVO() {
		
		setRequestDetailUid(Uuid.randomUuid().toString());
		
	}
	
	public void setRecieverInfo(Map<String, String> map) {
		this.recieverInfo = map;
		
		setRecieverInfoJson(JsonUtil.object2Json(map));
	}
	
	public void setTemplateVariable(Map<String, String> map) {
		this.templateVariable = map;
		
		setTemplateVariableJson(JsonUtil.object2Json(map));
		
	}
	
	public void setSenderInfo(Map<String, String> map) {
		this.senderInfo = map;
		
		setSenderInfoJson(JsonUtil.object2Json(map));
				
	}
	
	
}
