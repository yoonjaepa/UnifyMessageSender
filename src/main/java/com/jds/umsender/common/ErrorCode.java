package com.jds.umsender.common;

public enum ErrorCode {

	
	ParameterOmissionException("ER001","템플릿 파라메터 누락","요청된 파라메터가 템플릿에 정의된 파라메터에 누락되거나 변수명이 같지 않는경우 발생"),
	DataRegistException("ER501","데이터 등록 오류","데이터를 등록하는 과정에서 오류 발생"),
	SelectMediaInfoFailException("ER510","미디어정보가져오기오류","미디어정보를 DB에서 가져오는 업무 실패"),
	SelectTemplateFailException("ER511","템플릿정보가져오기오류","템플릿정보를 DB에서 가져오는 업무 실퍠"),
	Exception("ER999","정의되지않은오류","정의되지 않은 오류");


	private final String errorCode;
	private final String errorName;
	private final String errorDesc;
	
	ErrorCode(String errorCode, String errorName, String errorDesc) {
		this.errorCode = errorCode;
		this.errorName = errorName;
		this.errorDesc = errorDesc;
		
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrorDesc() {
		return this.errorDesc;
	}
	
	public String getErrorName() {
		return this.errorName;
	}
}
