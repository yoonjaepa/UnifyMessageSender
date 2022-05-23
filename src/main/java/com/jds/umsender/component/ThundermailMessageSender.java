package com.jds.umsender.component;


import java.util.List;

import com.andwise.tm6.api.jars.automail.AutomailAPI;
import org.springframework.stereotype.Component;

import com.jds.umsender.common.ErrorCode;
import com.jds.umsender.common.TransferStatusCode;
import com.jds.umsender.vo.MessageTransferListVO;
import com.jds.umsender.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ThundermailMessageSender extends CommonMessageBuilder 
							implements IfTemplateMessageSender		{

	
	
	
	@Override
	public void sendMessages(RequestMasterVO request) {
		
		//	발송내역 가져오기 
		List<MessageTransferListVO> messages = buildMessageList(request);
		
		for(MessageTransferListVO message : messages) {
			message = sendMessage(message);
		}	//	end for
		
		log.info(messages.toString());
		
		//	최종 상태 업데이트 
		changeTransferStatus(messages);
		
	}

	/**
	 * 실제 발송로직 구현
	 * @param message
	 * @return
	 */
	private MessageTransferListVO sendMessage(MessageTransferListVO message) {
		
		AutomailAPI automailAPI = new AutomailAPI();
		
		try {
		
			automailAPI.setApiURL(message.getMediaConfigInfo().get("apiUrl"));
			automailAPI.setAutomailIDEncrypt(message.getMediaConfigInfo().get("id"));
			
			
			automailAPI.setReceiverName(message.getRecieverInfo().get("receiverName"));
			automailAPI.setReceiverEmail(message.getRecieverInfo().get("receiverEmail"));
			
			automailAPI.setMailTitle(message.getMessageTitle());
			automailAPI.setMailContent(message.getMessageContent());
			
			automailAPI.setSenderName(message.getSenderInfo().get("senderName"));
			automailAPI.setSenderEmail(message.getSenderInfo().get("senderEmail"));
			automailAPI.setReturnMail(message.getSenderInfo().get("returnEmail"));
		
		
			automailAPI.sendEmail();
			
			if(automailAPI.getCode().equals(("100"))) {
				
				message.addTransferStatusHistList(TransferStatusCode.TRANSFER_COMPLETE.getTransCode()
								, TransferStatusCode.TRANSFER_COMPLETE.getTransName());
				
			} else if(automailAPI.getCode().equals("200")) {
				message.addTransferStatusHistList(TransferStatusCode.TRANSFER_ERROR.getTransCode()
								, TransferStatusCode.TRANSFER_ERROR.getTransName());
				message.setErrorCd(ErrorCode.ParameterOmissionException.getErrorCode());
				message.setErrorDetail(ErrorCode.ParameterOmissionException.getErrorName());
				
			} else if(automailAPI.getCode().equals("300")){
				message.addTransferStatusHistList(TransferStatusCode.TRANSFER_ERROR.getTransCode()
						, TransferStatusCode.TRANSFER_ERROR.getTransName());
				message.setErrorCd(ErrorCode.ServerWorkingException.getErrorCode());
				message.setErrorDetail(ErrorCode.ServerWorkingException.getErrorName());
				
			} else if(automailAPI.getCode().equals("-400")) {
				message.addTransferStatusHistList(TransferStatusCode.TRANSFER_ERROR.getTransCode()
						, TransferStatusCode.TRANSFER_ERROR.getTransName());
				message.setErrorCd(ErrorCode.ServerCommunicationException.getErrorCode());
				message.setErrorDetail(ErrorCode.ServerCommunicationException.getErrorName());
				
			} else if(automailAPI.getCode().equals("500")) {
				message.addTransferStatusHistList(TransferStatusCode.TRANSFER_ERROR.getTransCode()
						, TransferStatusCode.TRANSFER_ERROR.getTransName());
				message.setErrorCd(ErrorCode.ServerAuthenticationException.getErrorCode());
				message.setErrorDetail(ErrorCode.ServerAuthenticationException.getErrorName());
				
			} else {
				message.addTransferStatusHistList(TransferStatusCode.TRANSFER_ERROR.getTransCode()
						, TransferStatusCode.TRANSFER_ERROR.getTransName());
				message.setErrorCd(ErrorCode.Exception.getErrorCode());
				message.setErrorDetail(ErrorCode.Exception.getErrorName());
				
			}
			 
			if ((automailAPI.getCode() != null) 
					&& !(automailAPI.getCode().isEmpty()) 
					&& !(automailAPI.getCode().equals("100"))) {
				log.error("[{}]/{}",automailAPI.getCode(),automailAPI.getMsg());
			} 	//	end if
			
		} catch (Exception e) {
			log.error(e.toString());
			message.addTransferStatusHistList(TransferStatusCode.TRANSFER_ERROR.getTransCode()
					, TransferStatusCode.TRANSFER_ERROR.getTransName());
			message.setErrorCd(ErrorCode.Exception.getErrorCode());
			message.setErrorDetail(ErrorCode.Exception.getErrorName());
		}
		
		
		
		return message;
	}
	

}



