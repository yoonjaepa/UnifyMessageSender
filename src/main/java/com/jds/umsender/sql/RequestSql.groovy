package com.jds.umsender.sql;

import java.time.LocalDateTime

class RequestSql {

	public static final String SELECT_REQUEST = """
					SELECT * FROM TB_REQUEST_MASTER;
	""";
	
	
	public static final String INSERT_REQUEST_MASTER = """
				INSERT INTO TB_REQ_MSG_MST
					(
						REQ_MST_UID, 
						CHNNL_UID, 
						BIZCUST_UID, 
						REQ_RCV_DTTM, 
						TMPLT_CD,
						TMPLT_YN, 
						MASS_YN, 
						REQ_CHNNL_IP, 
						MSG_CNT, 
						REQ_EDOC, 
						RSVRD_FLD_1, 
						RSVRD_FLD_2, 
						REQ_STTS_CD, 
						REQ_STTS_MDFY_DTTM, 
						ERR_CD, 
						ERR_DTL, 
						REG_DATE, 
						REG_ID, 
						MDFY_DATE, 
						MDFY_ID
					)
				VALUES
					(
						:requestMasterUid
						,:channelUid
						,:bizCustUid          
						,:requestRecieveDttm  
						,:templateCd
						,:isTemplate              
						,:isMass        
						,:requestChannelIp           
						,:messageCount            
						,:requestDoc             
						,:reservedStr1            
						,:reservedStr2          
						,:requestStatusCd
						,:requestStatusChangeDttm
						,:errorCode
						,:errorDetail
						,:registDttm
						,:registId
						,:modifyDttm
						,:modifyId
					);

	""";
	

	public static final String INSERT_REQUEST_DTL = """
			
				INSERT INTO TB_REQ_MSG_DTL
						(
						REQ_MST_UID, 
						REQ_DTL_UID, 
					    MSG_NO,
						MEDIA_TYPE_CD, 
						TMPLT_CD, 
						MSG_TITLE, 
						RCVR_INFO, 
						TMPLT_VAR, 
						SNDR_INFO,
						MSG_CONT, 
						REG_DATE, 
						REG_ID, 
						MDFY_DATE, 
						MDFY_ID
						)
				VALUES
						(
						:requestMasterUid  
						,:requestDetailUid 
    					,:messageNo 
						,:mediaTypeCd       
						,:templateCd        
						,:messageTitle      
						,:recieverInfoJson      
						,:templateVariableJson  
						,:senderInfoJson
						,:messageContents   
						,:registDttm
						,:registId  
						,:modifyDttm
						,:modifyId  
						);
	""";
	
	
	public static final	String INSERT_REQUEST_STATUS_HIST ="""

					INSERT INTO TB_REQ_STTS_LST
						(
						REQ_MST_UID, 
						SEQ_NO, 
						REQ_STTS_CD, 
						REQ_STTS_MDFY_DTTM, 
						REG_DATE, 
						REG_ID, 
						MDFY_DATE, 
						MDFY_ID
						)
					VALUES
					(
						:requestMasterUid
						,SELECT IFNULL(MAX(SEQ_NO),0)+1 FROM TB_REQ_STTS_LST WHERE REQ_MST_UID=:requestMasterUid 
						,:requestStatusCd
						,:requestStatusChangeDttm
						,:registDttm
						,:registId  
						,:modifyDttm
						,:modifyId  
					);


	""";
	
	public static final String UPDATE_REQUEST_MASTER_STATUS = """

					UPDATE TB_REQ_MSG_MST
					SET 
						REQ_STTS_CD = :requestStatusCd, 
						REQ_STTS_MDFY_DTTM = :requestStatusChangeDttm, 
						ERR_CD = :errorCode, 
						ERR_DTL = :errorDetail, 
						MDFY_DATE = :modifyDttm, 
						MDFY_ID = :modifyId
					WHERE REQ_MST_UID= :requestStatusCd
	""";
	
	public static final String INSERT_MESSAGE_TRANS_LIST ="""

					INSERT INTO kvaram_ums.TB_MSG_TRNS_LST
					(
						REQ_MST_UID, 
						REQ_DTL_UID, 
						TRNS_SEQ_NO, 
						TRNS_STTS_CD, 
						REG_DATE, 
						REG_ID, 
						MDFY_DATE, 
						MDFY_ID
					)
					VALUES
					(
						:requestmasterUid,
						:requestDetailUid,
						(SELECT IFNULL(MAX(TRNS_SEQ_NO),0)+1 FROM TB_MSG_TRNS_LST
						 WHERE
							REQ_MST_UID = :requestmasterUid
							AND REQ_DTL_UID = :requestDetailUid),
						:transferStatusCd,
						:registDttm,
						:registId,  
						:modifyDttm,
						:modifyId  
					);

	""";

	
	}
	
	
	