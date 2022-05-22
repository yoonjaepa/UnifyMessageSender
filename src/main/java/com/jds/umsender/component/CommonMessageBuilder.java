package com.jds.umsender.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.jds.umsender.common.ErrorCode;
import com.jds.umsender.common.TransferStatusCode;
import com.jds.umsender.repository.MediaInfoRepo;
import com.jds.umsender.repository.MessageTransferListRepo;
import com.jds.umsender.repository.TemplateRepo;
import com.jds.umsender.vo.MediaInfoVO;
import com.jds.umsender.vo.MessageTemplateVO;
import com.jds.umsender.vo.MessageTransferListVO;
import com.jds.umsender.vo.RequestDetailVO;
import com.jds.umsender.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonMessageBuilder {
	
	
	@Autowired
	TemplateRepo templateRepo;
	
	@Autowired
	MediaInfoRepo mediaInfoRepo;
	
	@Autowired
	MessageTransferListRepo transRepo;
	
	
	protected List<MessageTransferListVO> buildMessageList(RequestMasterVO request) {
		//	Message 전송 내역 
		List<MessageTransferListVO> messages = new ArrayList<MessageTransferListVO>();
		
		MessageTransferListVO message;
		MessageTemplateVO template = null;
		MediaInfoVO mediaInfo 	= 	null;
		String errorCode		=	"";
		String errorName		=	"";
		String transStatusCode	=	"";
		String transStatusName	=	"";
		
		//	디테일 가져오기 
		RequestDetailVO[] details = request.getRequestDetail();
		
		try {
			
			//	템플릿 가져오기
			template = getMessageTemplate(request.getTemplateCd());
			
			try {
				//	미디어서버 정보 가져오기
				mediaInfo = getMediaInfo(template.getMediaCd());
			} catch (EmptyResultDataAccessException e) {
				log.error(ErrorCode.SelectMediaInfoFailException.getErrorDesc());
				log.error(e.toString());
				log.error("해당 에러가 발생 할 경우는 템플릿과 미디어코드의 기준정보가 정상적으로 매핑되어 있는지 확인 필요");
				errorCode = ErrorCode.SelectMediaInfoFailException.getErrorCode();
				errorName = ErrorCode.SelectMediaInfoFailException.getErrorName();
				transStatusCode = TransferStatusCode.TRANSFER_ERROR.getTransCode();
				transStatusName = TransferStatusCode.TRANSFER_ERROR.getTransName();
				
			}	//	end try
			
			
		} catch (EmptyResultDataAccessException e) {
			log.error(ErrorCode.SelectTemplateFailException.getErrorDesc());
			log.error(e.toString());
			errorCode = ErrorCode.SelectTemplateFailException.getErrorCode();
			errorName = ErrorCode.SelectTemplateFailException.getErrorName();
			transStatusCode = TransferStatusCode.TRANSFER_ERROR.getTransCode();
			transStatusName = TransferStatusCode.TRANSFER_ERROR.getTransName();
		} catch(Exception e) {
			log.error(ErrorCode.Exception.getErrorDesc());
			log.error(e.toString());
			errorCode = ErrorCode.Exception.getErrorCode();
			errorName = ErrorCode.Exception.getErrorName();
			transStatusCode = TransferStatusCode.TRANSFER_ERROR.getTransCode();
			transStatusName = TransferStatusCode.TRANSFER_ERROR.getTransName();
		} //	end try
		
		//	상세 메시지를 검증/발송 
		for(RequestDetailVO detail : details) {
			
			message = setMessageTransferListInfo(detail);
			
			//	위에서 템플릿 누락 또는 매체 오류일 경우 하위 상세는 무조건 오류 처리
			if (transStatusCode.equals(TransferStatusCode.TRANSFER_ERROR.getTransCode())) {
				
				message.addTransferStatusHistList(transStatusCode, transStatusName);
				message.setErrorCd(errorCode);
				message.setErrorDetail(errorName);
				
			} else {
				
				//	파라메터 검증하기 
				if (!isMatchParameter(template.getTemplateVariable(), detail.getTemplateVariable())) {
					
					// 오류 이력 추가 
					//	전송상태 세팅(오류) 
					message.addTransferStatusHistList(TransferStatusCode.TRANSFER_ERROR.getTransCode(),
									TransferStatusCode.TRANSFER_ERROR.getTransName());
					message.setErrorCd(ErrorCode.ParameterOmissionException.getErrorCode());
					message.setErrorDetail(ErrorCode.ParameterOmissionException.getErrorName());
					
				} else {
					//	메시지 제목에 파라메터 바인딩 하기
					message.setMessageTitle(buildMessage(template.getMessageTitle(), detail.getTemplateVariable()));
					
					//	메시지 본문에 파라메터 바인딩 하기 
					message.setMessageContent(buildMessage(template.getMessageContent(), detail.getTemplateVariable()));
					
					//	전송상태 세팅(전송요청)
					message.addTransferStatusHistList(TransferStatusCode.REQUEST_TRANSFER.getTransCode()
									, TransferStatusCode.REQUEST_TRANSFER.getTransName());
					
				}	//	end if	
				
			}
			
			//	메시지 발송 대상 추가
			messages.add(message);		
			
			
			//log.info("MSG:" + messages.toString());			
			
		}	//	end for
		
		//	발송이력 저장 
		this.registMessgeTransferList(messages);
		
		//	메시지 발송 
		
		return messages;
	}
	
	
	/**
	 * 템플릿 가져오기 
	 * @param tempCd
	 * @return
	 */
	protected MessageTemplateVO getMessageTemplate(String tempCd)  {
		
		MessageTemplateVO template = null;
		
		//	parameter setting
		Map<String, String> params = new HashMap<String, String>();
		params.put("templateCd", tempCd);
		
		try {
			
			//	템플릿 가져오기 
			template = templateRepo.findByTemplateCode(params);
		
			//log.info(template.toString());
			
			
		} catch(EmptyResultDataAccessException e) {
			log.error(e.toString());
			throw e;
		}	//	end try
			
		return template;
		
	}
	
	/**
	 * 템플릿에 파라메터를 바인딩하여 메시지 만들기 
	 * @param cont
	 * @param reqParam
	 * @return
	 */
	protected String buildMessage(String cont , Map<String, String> reqParam) {
		
		String paramFromat = "<%=@@@%>";
		String msg = cont;
		String param = "";
		String retStr ="";
		
		Set<String> keys = reqParam.keySet();
		
		for(String key :keys) {
			
			param = paramFromat.replaceAll("@@@",key);
						
			retStr = msg.replaceAll(param, reqParam.get(key));
			
			msg = retStr;
			
		}
		
		//log.info("Message : {}", retStr);
		
		return retStr;
	}
	
	/**
	 * 요청된 파라메터 정보가 템플릿과 동일한지 여부 확인 
	 * @param param
	 * @param reqParam
	 * @return
	 */
	protected boolean isMatchParameter(String param, Map<String, String> reqParam ) {
		
		String[] params = param.split("#");
		boolean retBool = true; 
		
		for(String p :params) {
			
			//log.info("temp p : {}", p);
			//	템플릿은 파라메타가 요청파라메터에 없으면 false 반납 
			if (!reqParam.containsKey(p)) retBool = false;
		}	//	end for
		
		
		return retBool;
		
	}
	
	/**
	 * 매체 서버 정보 가져오기 
	 * @param mediaCd
	 * @return
	 */
	protected MediaInfoVO getMediaInfo(String mediaCd) {
		MediaInfoVO  mediaInfo = null;
		
//		parameter setting
		Map<String, String> params = new HashMap<String, String>();
		params.put("mediaCd", mediaCd);
		
		try {
			
			//	템플릿 가져오기 
			mediaInfo = mediaInfoRepo.findByMediaCode(params);
		
			//log.info(mediaInfo.toString());
			
			
		} catch(EmptyResultDataAccessException e) {
			log.error(e.toString());
			throw e;
		}	//	end try
			
		return mediaInfo;
				
	}
	
	private MessageTransferListVO setMessageTransferListInfo(RequestDetailVO detail) {
		
		MessageTransferListVO message = new MessageTransferListVO();
				
		message.setRequestMasterUid(detail.getRequestMasterUid());
		message.setRequestDetailUid(detail.getRequestDetailUid());
		
		return message;
	}
	
	@SuppressWarnings("unused")
	protected void registMessgeTransferList(List<MessageTransferListVO> messages) {
		
		transRepo.registBatchMessageTransfer(messages.toArray(new MessageTransferListVO[] {}));
		
	}
	
		
}
