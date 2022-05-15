package com.jds.umsender.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BaseAuditVO {

	private LocalDateTime registDttm;
	private String registId;
	private LocalDateTime modifyDttm;
	private String modifyId;

	public BaseAuditVO() {
		
		setRegistDttm(LocalDateTime.now());
		setRegistId("bal0601");
		setModifyDttm(LocalDateTime.now());
		setModifyId("bal0601");
	}
	

}