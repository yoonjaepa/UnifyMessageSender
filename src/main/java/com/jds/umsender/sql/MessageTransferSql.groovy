package com.jds.umsender.sql

class MessageTransferSql {
	public static final String INSERT_MESSAGE_TRANSFER_LIST = """

			INSERT INTO TB_MSG_TRNS_LST
			(
				MSG_TRNS_UID,
				REQ_MST_UID, 
				REQ_DTL_UID, 
				TRNS_SEQ_NO, 
				TRNS_STTS_CD, 
				TRNS_STTS_HST,
				ERR_CD,
				ERR_DETAIL,
				MSG_TITLE, 
				MSG_CONT, 
				REG_DATE, 
				REG_ID, 
				MDFY_DATE, 
				MDFY_ID
			)
			VALUES
			(
				:messageTransferUid,
				:requestMasterUid,
				:requestDetailUid,
				(SELECT IFNULL(MAX(TRNS_SEQ_NO),0)+1 FROM TB_MSG_TRNS_LST lst
				WHERE
					REQ_MST_UID = :requestMasterUid
					AND REQ_DTL_UID = :requestDetailUid) ,
				:transferStatusCd,
				:transferStatusHist,
				:errorCd,
				:errorDetail,
				:messageTitle,
				:messageContent,
				:registDttm,
				:registId,
				:modifyDttm,
				:modifyId
			);

	"""
	
	public static final String SELECT_MASTER_MESSAGE_TRANSFER_LIST = """
		SELECT 
			MSG_TRNS_UID as messageTransferUid,
			REQ_MST_UID	as requestMasterUid, 
			REQ_DTL_UID	as requestDetailUid, 
			TRNS_SEQ_NO	as transferSeqNo, 
			TRNS_STTS_CD	as transferStatusCd, 
			(SELECT CD_NM FROM TB_CODE_INFO CI WHERE CI.CD_ID = MTL.TRNS_STTS_CD) as transferStatusName,
			TRNS_STTS_HST	as transferStatusHist,
			MSG_TITLE	as messageTitle, 
			MSG_CONT	as messageContent,
			ERR_CD		as errorCd,	
			ERR_DETAIL	as errorDetial,
			REG_DATE	as registDttm, 
			REG_ID		as registId, 	
			MDFY_DATE	as modifyDttm, 
			MDFY_ID		as modifyId
		FROM TB_MSG_TRNS_LST MTL
		WHERE
			REQ_MST_UID = :requestMasterUid
		;
	"""
	
	public static final String SELECT_DETAIL_MESSAGE_TRANSFER_LIST = """
		SELECT 
			MSG_TRNS_UID as messageTransferUid,
			REQ_MST_UID	as requestMasterUid, 
			REQ_DTL_UID	as requestDetailUid, 
			TRNS_SEQ_NO	as transferSeqNo, 
			TRNS_STTS_CD	as transferStatusCd, 
			(SELECT CD_NM FROM TB_CODE_INFO CI WHERE CI.CD_ID = MTL.TRNS_STTS_CD) as transferStatusName,
			TRNS_STTS_HST	as transferStatusHist,
			MSG_TITLE	as messageTitle, 
			MSG_CONT	as messageContent, 
			ERR_CD		as errorCd,	
			ERR_DETAIL	as errorDetial,
			REG_DATE	as registDttm, 
			REG_ID		as registId, 	
			MDFY_DATE	as modifyDttm, 
			MDFY_ID		as modifyId
		FROM TB_MSG_TRNS_LST MTL
		WHERE
			REQ_MST_UID = :requestMasterUid
			AND REQ_DTL_UID = :requestDetailUid
		;
	"""
	
	public static final String SELECT_ONE_MESSAGE_TRANSFER_LIST = """
		SELECT 
			MSG_TRNS_UID as messageTransferUid,
			REQ_MST_UID	as requestMasterUid, 
			REQ_DTL_UID	as requestDetailUid, 
			TRNS_SEQ_NO	as transferSeqNo, 
			TRNS_STTS_CD	as transferStatusCd, 
			(SELECT CD_NM FROM TB_CODE_INFO CI WHERE CI.CD_ID = MTL.TRNS_STTS_CD) as transferStatusName,
			TRNS_STTS_HST	as transferStatusHist,
			MSG_TITLE	as messageTitle, 
			MSG_CONT	as messageContent, 
			ERR_CD		as errorCd,	
			ERR_DETAIL	as errorDetial,
			REG_DATE	as registDttm, 
			REG_ID		as registId, 	
			MDFY_DATE	as modifyDttm, 
			MDFY_ID		as modifyId
		FROM TB_MSG_TRNS_LST MTL
		WHERE
			REQ_MST_UID = :requestMasterUid
			AND REQ_DTL_UID = :requestDetailUid
			AND MSG_TRNS_UID = :messageTransferUid
		;
	"""
	
	public static final String CHANGE_TRANSFER_SATAUS = """
		UPDATE TB_MSG_TRNS_LST
		SET 
			TRNS_STTS_CD=:transferStatusCd,
			TRNS_STTS_HST=:transferStatusHist,
			ERR_CD=:errorCd,
			ERR_DETAIL=:errorDetail,
			MDFY_DATE=now(),
			MDFY_ID=:modifyId
		WHERE
			REQ_MST_UID = :requestMasterUid
			AND REQ_DTL_UID = :requestDetailUid
			AND MSG_TRNS_UID = :messageTransferUid
		
	"""
	
	
}
