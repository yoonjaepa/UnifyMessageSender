package com.jds.umsender.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MessageTemplateVO extends BaseAuditVO {
	private String templateCd;
	private String mediaCd;
	private String templateName;
	private String messageTitle;
	private String messageContent;
	private boolean useYn;
	private String templateVariable;

	

}


