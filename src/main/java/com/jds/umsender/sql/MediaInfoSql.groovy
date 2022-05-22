package com.jds.umsender.sql

class MediaInfoSql {
	
	public static final String SELECT_MEDIA_INFO = """
		
					SELECT 
						MEDIA_CD		as mediaCd, 
						BIZCUST_UID		as bizCustUid, 
						(SELECT BIZCUST_NM FROM TB_CUST_INFO CI WHERE CI.BIZCUST_UID = MI.BIZCUST_UID) as bizCustName,
						MEDIA_TYPE_CD	as mediaTypeCd, 
						(SELECT CD_NM FROM TB_CODE_INFO CDI WHERE CDI.CD_ID = MI.MEDIA_TYPE_CD) as mediaTypeName,
						MEDIA_NM		as mediaName, 
						MEDIA_CFG_INFO	as mediaConfigInfoJson, 
						USE_YN			as useYn, 
						REG_DATE		as registDttm, 
						REG_ID			as registId, 
						MDFY_DATE		as modifyDttm, 
						MDFY_ID			as modifyId
					FROM TB_MEDIA_INFO MI
					WHERE
						MEDIA_CD = :mediaCd
					;

	"""
	
}
