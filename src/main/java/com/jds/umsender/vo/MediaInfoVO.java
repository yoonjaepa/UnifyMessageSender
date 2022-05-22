package com.jds.umsender.vo;

import java.util.Map;

import com.jds.umsender.common.JsonUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MediaInfoVO extends BaseAuditVO {
	
	private String mediaCd;
	private String bizCustUid;
	private String bizCustName;
	private String mediaTypeCd;
	private String mediaTypeName;
	private String mediaName;
	private Map<String, String> mediaConfigInfo;
	private String mediaConfigInfoJson;
	private boolean useYn;
		

	@SuppressWarnings("unchecked")
	public void setMediaConfigInfoJson(String json) {
		
		this.mediaConfigInfoJson = json;
		
		mediaConfigInfo = (Map<String, String>)JsonUtil.Json2Object(json, Map.class);
				
	}
	
}
