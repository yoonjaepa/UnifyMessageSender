package com.jds.umsender.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jds.umsender.common.JsonUtil;
import com.jds.umsender.common.TransferStatusCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Delegate;

@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
public class MessageTransferListVO extends BaseAuditVO {

	private String		messageTransferUid = UUID.randomUUID().toString();
	private String 		requestMasterUid;
	private String		requestDetailUid;
	private int			transferSeqNo;
	private String		transferStatusCd = "TR010";
	private String		transferStatusName;
	@Delegate
	private List<TransferStatusVO> transferStatusHistList= new ArrayList<TransferStatusVO>();
	private String		transferStatusHist;
	private String		messageTitle;
	private String		messageContent;
	private String		errorCd;
	private String		errorDetail;
	
	public MessageTransferListVO() {
		addTransferStatusHistList(TransferStatusCode.REQUEST_RECEIVE.getTransCode(),
						TransferStatusCode.REQUEST_RECEIVE.getTransName());
	}
	
	
	@SuppressWarnings("unchecked")
	public void setTransferStatusHist(String json) {
		transferStatusHist = json;
		
		transferStatusHistList = (List<TransferStatusVO>) JsonUtil.Json2Object(json, List.class);
		
				
	}
	
	public void addTransferStatusHistList(String cd, String nm) {

		
		transferStatusHistList.add(TransferStatusVO.builder().cd(cd).nm(nm).build());
		
		transferStatusHist = JsonUtil.object2Json(transferStatusHistList);
		
		//	최종 상태세팅 
		transferStatusCd = transferStatusHistList.get(transferStatusHistList.size()-1).getStatusCd();
		
		//	최종 상태이름 
		transferStatusName = transferStatusHistList.get(transferStatusHistList.size()-1).getStatusName();
		
	}
	

}
