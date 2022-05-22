package com.jds.umsender.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TransferStatusVO  {
	private String statusCd;		// 	TODO: 해당 컬럼을 넣어야 할지에 대한 고민 필요 
	private String statusName;
	private LocalDateTime chageDttm	= LocalDateTime.now();

	@Builder
	public TransferStatusVO(String cd, String nm) {
		this.statusCd=cd;
		this.statusName=nm;
				
	}
	

	
	
	

}
