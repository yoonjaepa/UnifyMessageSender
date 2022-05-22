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
class TestMessageSender {

	@Autowired
	EmailMessageSender sender;
	
	RequestMasterVO request;
	
	@Test
	void TestSendMessage() {
	
		//RequestDetailVO detail = request.getRequestDetail()[0];
		
		sender.sendMessage(request);
		
		
		
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
