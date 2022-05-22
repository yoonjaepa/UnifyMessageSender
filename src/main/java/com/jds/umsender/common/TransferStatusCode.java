package com.jds.umsender.common;

import lombok.Getter;


public enum TransferStatusCode {

	REQUEST_RECEIVE("TR010","요청접수"),
	REQUEST_TRANSFER("TR020","전송요청"),
	TRANSFER_COMPLETE("TR030","전송완료"),
	RECEIVE_RESULT("TR040","결과수신완료"),
	TRANSFER_ERROR("TR999","오류");

	private final String transCode;
	private final String transName;
	
	TransferStatusCode(String transCode, String transName) {
		this.transCode = transCode;
		this.transName = transName;
	}
	
	public String getTransCode() {
		return this.transCode;
	}
	
	public String getTransName() {
		return this.transName;
	}
	
	
}
