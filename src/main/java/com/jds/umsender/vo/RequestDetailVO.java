package com.jds.umsender.vo;

import java.util.HashMap;

import lombok.Data;

@Data
public class RequestDetailVO {
	
	private String 		messageNo;
	private String		sendDttm;
	private	HashMap<String, String> paramSet;
	private String		targetUserAddr;
	private String		title;
	
	
}
