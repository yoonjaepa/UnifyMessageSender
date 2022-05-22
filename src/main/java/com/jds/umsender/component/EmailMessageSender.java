package com.jds.umsender.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jds.umsender.common.ErrorCode;
import com.jds.umsender.common.TransferStatusCode;
import com.jds.umsender.repository.MessageTransferListRepo;
import com.jds.umsender.vo.MediaInfoVO;
import com.jds.umsender.vo.MessageTemplateVO;
import com.jds.umsender.vo.MessageTransferListVO;
import com.jds.umsender.vo.RequestDetailVO;
import com.jds.umsender.vo.RequestMasterVO;
import com.jds.umsender.vo.TransferStatusVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailMessageSender extends CommonMessageBuilder 
							implements IfTemplateMessageSender		{


	@Override
	public void sendMessage(RequestMasterVO request) {
		
	
	}
	

}



