package com.jds.umsender.component;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jds.umsender.common.JsonUtil;
import com.jds.umsender.vo.RequestDetailVO;
import com.jds.umsender.vo.RequestMasterVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TestEmailMessageSender {

	@Autowired
	ThundermailMessageSender sender;
	
	RequestMasterVO request;
	
	@Test
	void TestSendMessages() {
	
		log.info("Start TestSendMessages");
		//RequestDetailVO detail = request.getRequestDetail()[0];
		
		log.info(request.toString());
		
		sender.sendMessages(request);
		
		log.info("End TestSendMessages");
		
		
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
				+ "    \"receiverInfo\":{\n"
				+ "  		\"receiverEmail\":\"bal0601@kt.com\",\n"
				+ "      	\"receiverName\":\"송정근\",\n"
				+ " 	 	\"pw\":\"new1234\"  \n"
				+ "    	},\n"
				+ "	\"templateVariable\":{\n"
				+ "		\"custName\":\"송정근\",\n"
				+ "		\"custEmail\":\"bal0601@kt.com\",\n"
				+ "		\"custTelNo\":\"01099020500\"\n"
				+ "		},\n"
				+ "    \"senderInfo\":{\n"
				+ "      	\"senderName\":\"MetaLounge\",\n"
				+ "      	\"senderEmail\":\"thundermail@andwise.com\",\n"
				+ "      	\"returnEmail\":\"thundermail@andwise.com\"\n"
				+ "    	}\n"
				+ "	},\n"
				+ "	{\n"
				+ "	\"messageNo\":2,\n"
				+ "    \"mediaTypeCd\":\"MD001\",\n"
				+ "    \"templateCd\":\"T0001\",\n"
				+ "    \"messageTitle\":\"초대메일\",\n"
				+ "    \"receiverInfo\":{\n"
				+ "  		\"receiverEmail\":\"kimwkdy@naver.com\",\n"
				+ "      	\"receiverName\":\"김현정\",\n"
				+ " 	 	\"pw\":\"new1234\"  \n"
				+ "    },\n"
				+ "	\"templateVariable\":{\n"
				+ "		\"custName\":\"김현정\",\n"
				+ "		\"custEmail\":\"kimwkdy@naver.com\",\n"
				+ "		\"custTelNo\":\"010-9902-0500\"\n"
				+ "		},\n"
				+ "    \"senderInfo\":{\n"
				+ "      	\"senderName\":\"MetaLounge\",\n"
				+ "      	\"senderEmail\":\"bal0601@kt.com\",\n"
				+ "      	\"returnEmail\":\"thundermail@andwise.com\"\n"
				+ "    	}      \n"
				+ "	},\n"
				+ "	{\n"
				+ "	\"messageNo\":3,\n"
				+ "    \"mediaTypeCd\":\"MD001\",\n"
				+ "    \"templateCd\":\"T0001\",\n"
				+ "    \"messageTitle\":\"초대메일\",\n"
				+ "    \"receiverInfo\":{\n"
				+ "  		\"receiverEmail\":\"hyun.jung.kim@kt.com\",\n"
				+ "      	\"receiverName\":\"김현정\",\n"
				+ " 	 	\"pw\":\"new1234\"  \n"
				+ "    },\n"
				+ "	\"templateVariable\":{\n"
				+ "		\"custName\":\"김현정\",\n"
				+ "		\"custEmail\":\"khyun.jung.kim@kt.com\",\n"
				+ "		\"custTelNo\":\"010-9902-0500\"\n"
				+ "		},\n"
				+ "    \"senderInfo\":{\n"
				+ "      	\"senderName\":\"MetaLounge\",\n"
				+ "      	\"senderEmail\":\"thundermail@andwise.com\",\n"
				+ "      	\"returnEmail\":\"thundermail@andwise.com\"\n"
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
