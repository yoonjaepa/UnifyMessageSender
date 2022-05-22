package com.jds.umsender.component;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;


import com.jds.umsender.common.JsonUtil;
import com.jds.umsender.common.TransferStatusCode;
import com.jds.umsender.vo.MediaInfoVO;
import com.jds.umsender.vo.MessageTemplateVO;
import com.jds.umsender.vo.MessageTransferListVO;
import com.jds.umsender.vo.RequestDetailVO;
import com.jds.umsender.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TestCommonMessageBuilder {
	
	@Autowired
	CommonMessageBuilder commonMessageBuilder;
	
	RequestMasterVO request;

	//@Ignore
	@DisplayName("메시지 템플릿가져오기_정상")
	@Test
	void testGetMessageTemplate_OK() {
		
		log.info("Start testGetMessageTemplate_OK");
		
		MessageTemplateVO template = null;
		String tempCd = "T0001";
		
		try {
			template = commonMessageBuilder.getMessageTemplate(tempCd);
			log.info(template.toString());

		} catch (EmptyResultDataAccessException e) {
			log.error(e.toString());
		} finally {
			assertEquals(tempCd, template.getTemplateCd());
		}
		
		
				
		log.info("End testGetMessageTemplate_OK");
		
	}
	
	//@Ignore
	@DisplayName("메시지 템플릿가져오기_미존재코드입력_실패")
	@Test
	void testGetMessageTemplate_Fail() {
		
		log.info("Start testGetMessageTemplate_Fail");

		MessageTemplateVO template = null;
		String tempCd = "A0001";
		
		try {
			template = commonMessageBuilder.getMessageTemplate(tempCd);
			log.info(template.toString());

		} catch (EmptyResultDataAccessException e) {
			log.error(e.toString());
		} finally {
			
			assertNull(template);
		}
		
		
	}
	
	//@Ignore
	@DisplayName("파라매터 매치여부 검사 테스트_정상")
	@Test
	void testIsMatchParameter_OK() {
		
		log.info("Start testIsMatchParameter_OK");

		RequestDetailVO detail = request.getRequestDetail()[0];
		
		try {
			MessageTemplateVO template = commonMessageBuilder.getMessageTemplate(detail.getTemplateCd());
			/**
			if(commonMessageBuilder.isMatchParameter(template.getTemplateVariable(), detail.getTemplateVariable())) {
				log.info("OK");
			} else {
				log.info("Not OK");
			}
			**/
			
			assertEquals(true,
					commonMessageBuilder.isMatchParameter(template.getTemplateVariable(), detail.getTemplateVariable()));
		} catch (EmptyResultDataAccessException e) {
			log.error(e.toString());
		}
		
		log.info("End testIsMatchParameter_OK");
		
	}
	
	//@Ignore
	@DisplayName("파라매터 매치여부 검사 테스트_미존재코드_실패")
	@Test
	void testIsMatchParameter_Fail() {
		
		log.info("Start testIsMatchParameter_Fail");
		
		MessageTemplateVO template = null;
	
		RequestDetailVO detail = request.getRequestDetail()[1];
		
		detail.getTemplateVariable().remove("custName");
		
		try {
			template = commonMessageBuilder.getMessageTemplate(detail.getTemplateCd());
			assertEquals(false,
					commonMessageBuilder.isMatchParameter(template.getTemplateVariable(), detail.getTemplateVariable()));

		} catch (EmptyResultDataAccessException e) {
			log.error(e.toString());
		}
		
		
		
				
		
	}
	
	@DisplayName("메시지 바인딩 테스트")
	@Test
	void testBuildMessage() {
		
		log.info("Start testBuildMessage");

		RequestDetailVO detail = request.getRequestDetail()[0];
		
		try {
			MessageTemplateVO template = commonMessageBuilder.getMessageTemplate(detail.getTemplateCd());
			if(commonMessageBuilder.isMatchParameter(template.getTemplateVariable(), detail.getTemplateVariable())) {
				String msg = commonMessageBuilder.buildMessage(template.getMessageContent(), detail.getTemplateVariable());
				//log.info("contents : {}" , msg);
				assertEquals("안녕하세요. 송정근\n"
						+ "     메타라운지 가입을 축하드립니다.\n"
						+ "     우리는 민족 중흥의 역사적 사명을 http://METAROUNGE.KT.COM 뜨고 이땅에 태어났다.\n"
						+ "	 KT001 입니다.", msg);
			} else {
				log.info("Not OK");
			}
		} catch (EmptyResultDataAccessException e) {
			log.error(e.toString());
		}
		
		log.info("End testBuildMessage");
		
	}
	
	@DisplayName("매체 정보가져오기_정상")
	@Test
	void testGetMediaInfo_OK() {
	
		log.info("Start testGetMediaInfo_OK");
		
		String mediaCd = "MI001";
		MediaInfoVO mediaInfo = null;
		
		try {
			mediaInfo = commonMessageBuilder.getMediaInfo(mediaCd);
			assertEquals(mediaCd,mediaInfo.getMediaCd());
			//log.info(mediaInfo.toString());
		} catch (EmptyResultDataAccessException e) {
			log.error(e.toString());
		}
		
		log.info("End testGetMediaInfo_OK");
	}
	
	@DisplayName("매체 정보가져오기_미존재코드_실패")
	@Test
	void testGetMediaInfo_Fail() {

		log.info("Start testGetMediaInfo_Fail");
		
		String mediaCd = "MMI001";
		MediaInfoVO mediaInfo = null;
		
		try {
			mediaInfo = commonMessageBuilder.getMediaInfo(mediaCd);
			//assertEquals(mediaCd,mediaInfo.getMediaCd());
			//log.info(mediaInfo.toString());
		} catch (EmptyResultDataAccessException e) {
			log.error(e.toString());
		}
		
		assertNull(mediaInfo);
		
		log.info("End testGetMediaInfo_Fail");
	}
	
	//@Ignore
	@DisplayName("발송할 모든 메시지 생성_정상")
	@Test
	void TestBuildMessageList_OK() {
		
		log.info("Start TestBuildMessageList_OK");
				
		List<MessageTransferListVO> hist = commonMessageBuilder.buildMessageList(request);
		
		for(MessageTransferListVO msg : hist) {
			assertEquals(TransferStatusCode.TRANSFER_COMPLETE.getTransCode()
					, msg.getTransferStatusCd());
		}
		
		
		
		log.info("End TestBuildMessageList_OK");
	}
	
	/**
	 * 템플릿 변수가 누락되어거나 변수명이 잘못 들어간 경우 
	 */
	@DisplayName("발송할 모든 메시지 생성_템플릿변수누락_실패")
	@Test
	void TestBuildMessageList_Var_Fail() {
		
		log.info("Start TestBuildMessageList_Var_Fail");
		
		//	필수 변수값인 custName 누락 
		request.getRequestDetail()[0].getTemplateVariable().remove("custName");
				
		List<MessageTransferListVO> hist = commonMessageBuilder.buildMessageList(request);
		
		for(MessageTransferListVO msg : hist) {
			assertEquals(TransferStatusCode.TRANSFER_COMPLETE.getTransCode()
					, msg.getTransferStatusCd());
		}
		
		log.info("End TestBuildMessageList_Var_Fail");
	}
	
	

	
	@BeforeEach
	//	kafka에서 넘어온 데이터 값 세팅 
	void getRequestMasterVO() {
		String json = "{\n"
				+ "  \"channelUid\":\"\",\n"
				+ "  \"bizCustUid\":\"\",\n"
				+ "  \"templateCd\":\"T0001\",\n"
				+ "  \"isTemplate\":true,\n"
				+ "  \"isMass\":true,\n"
				+ "  \"requestChannelIp\":\"0.0.0.0\",\n"
				+ "  \"messageCount\":1,\n"
				+ "  \"reservedStr1\":\"bcd\",\n"
				+ "  \"reservedStr2\":\"abc\",\n"
				+ "  \"requestDetail\":[{\n"
				+ "	\"messageNo\":1,\n"
				+ "    \"mediaTypeCd\":\"MD001\",\n"
				+ "    \"templateCd\":\"T0001\",\n"
				+ "    \"messageTitle\":\"초대메일\",\n"
				+ "    \"recieverInfo\":{\n"
				+ "  		\"receiverEmail\":\"bal0601@kt.com\",\n"
				+ "      	\"receiverName\":\"송정근\",\n"
				+ " 	 	\"pw\":\"new1234\"  \n"
				+ "    	},\n"
				+ "	\"templateVariable\":{\n"
				+ "		\"custName\":\"송정근\",\n"
				+ "		\"custEmail\":\"http://METAROUNGE.KT.COM\",\n"
				+ "		\"custTelNo\":\"KT001\"\n"
				+ "		},\n"
				+ "    \"senderInfo\":{\n"
				+ "      	\"senderName\":\"MetaLounge\",\n"
				+ "      	\"senderEmail\":\"meta@kt.com\",\n"
				+ "      	\"returnEmail\":\"return@kt.com\"\n"
				+ "    	}\n"
				+ "	},\n"
				+ "	{\n"
				+ "	\"messageNo\":2,\n"
				+ "    \"mediaTypeCd\":\"MD001\",\n"
				+ "    \"templateCd\":\"T0001\",\n"
				+ "    \"messageTitle\":\"초대메일\",\n"
				+ "    \"recieverInfo\":{\n"
				+ "  		\"receiverEmail\":\"yoonjaepa@gmail.com\",\n"
				+ "      	\"receiverName\":\"강정근\",\n"
				+ " 	 	\"pw\":\"new1234\"  \n"
				+ "    },\n"
				+ "	\"templateVariable\":{\n"
				+ "		\"custName\":\"송윤재\",\n"
				+ "		\"custEmail\":\"http://METAROUNGE.KT.COM\",\n"
				+ "		\"custTelNo\":\"KT001\"\n"
				+ "		},\n"
				+ "    \"senderInfo\":{\n"
				+ "      	\"senderName\":\"MetaLounge\",\n"
				+ "      	\"senderEmail\":\"meta@kt.com\",\n"
				+ "      	\"returnEmail\":\"return@kt.com\"\n"
				+ "    	}      \n"
				+ "	}\n"
				+ "	]\n"
				+ "}\n"
				+ "";
		
		request = (RequestMasterVO) JsonUtil.Json2Object(json, RequestMasterVO.class);
		
		request.setRequestMasterUid(UUID.randomUUID().toString());
		request.setRequestRecieveDttm(LocalDateTime.now());
		request.setRequestStatusChangeDttm(LocalDateTime.now());
		request.setRequestMasterUid(UUID.randomUUID().toString());
		request.setRequestStatusCd("RS001");
		request.setRequestDtlMasterUid(request.getRequestMasterUid());
		
		//Log.info(request.toString());
		
		
		
	}
}
