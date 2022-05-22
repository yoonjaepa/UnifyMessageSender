package com.jds.umsender.repository;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jds.umsender.common.TransferStatusCode;
import com.jds.umsender.vo.MessageTransferListVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TestMessageTransferListRepo {

	@Autowired
	MessageTransferListRepo messageRepo;
	
	List<MessageTransferListVO> messages = new ArrayList<MessageTransferListVO>(); 
	
	

	@BeforeEach
	void setEnv() {
		
		MessageTransferListVO message = new MessageTransferListVO();
		
		//List<TransferStatusVO> hst = new ArrayList<TransferStatusVO>();
		
		message.setRequestMasterUid(UUID.randomUUID().toString());
		message.setRequestDetailUid(UUID.randomUUID().toString());
		
		message.addTransferStatusHistList(TransferStatusCode.REQUEST_TRANSFER.getTransCode(),
						TransferStatusCode.REQUEST_TRANSFER.getTransName());
		/**
		hst.add(new TransferStatusVO("TR010", "요청접수"));		
		hst.add(new TransferStatusVO("TR020", "전송요청"));
		
		message.setTransferStatusCd("TR020");
		message.setTransferStatusName("전송요청");		
		
		message.setTransferStatusHistList(hst);
		**/
		messages.add(message);
		
		MessageTransferListVO message1 = new MessageTransferListVO();
		
		message1.setRequestMasterUid(UUID.randomUUID().toString());
		message1.setRequestDetailUid(UUID.randomUUID().toString());
		message1.addTransferStatusHistList(TransferStatusCode.TRANSFER_COMPLETE.getTransCode(),
						TransferStatusCode.TRANSFER_COMPLETE.getTransName());
		message1.addTransferStatusHistList(TransferStatusCode.RECEIVE_RESULT.getTransCode(),
						TransferStatusCode.RECEIVE_RESULT.getTransName());
		
		/**
		hst.add(new TransferStatusVO("TR030", "전송완료"));
		
		message1.setTransferStatusCd("TR030");
		message1.setTransferStatusName("전송완료");		
		message1.setTransferStatusHistList(hst);
		**/
		messages.add(message1);
		
	}
	
	//@Ignore
	@Test
	void testRegistMessageTransfer_OK() {

		messageRepo.registMessageTransfer((MessageTransferListVO)messages.toArray()[0]);
		
		
		
	}
	
	//@Ignore
	@Test
	void testBatchMessgeTransfer_OK() {
		messageRepo.registBatchMessageTransfer(messages.toArray(new MessageTransferListVO[] {}));
	}
	
	
	
	

}

/**
TR000	전송상태코드
TR010	요청접수
TR020	전송요청
TR030	전송완료
TR040	결과수신완료
TR999	오류
**/