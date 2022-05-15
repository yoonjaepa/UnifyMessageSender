package com.jds.umsender.sql

class TemplateSql {
	
	public static final String INSERT_TEMPLATE_INFO = """
			INSERT INTO kvaram_ums.TB_TMPLT_INFO
			(
				TMPLT_CD, 
				MEDIA_CD,  
				TMPLT_NM,
				MSG_TITLE,
				MSG_CONT, 
				TMPLT_VAR, 
				REG_DATE, 
				REG_ID, 
				MDFY_DATE, 
				MDFY_ID)
			VALUES(
				:templateCd,
				:mediaCd,
				:templateTitle,
				:messageContent,
				:templateVariable,
				:registDttm,
				:registId,
				:modifyDttm,
				:modifyId
			);
	""";
	
	public static final String SELECT_TEMPLATE_INFO = """
			SELECT 
				TMPLT_CD	templateCd, 
				MEDIA_CD	mediaCd, 
				TMPLT_NM	templateName, 
				MSG_TITLE	messageTitle,
				MSG_CONT	messageContent, 
				TMPLT_VAR	templateVariable, 
				REG_DATE	registDttm, 
				REG_ID		registId, 
				MDFY_DATE	modifyDttm, 
				MDFY_ID		modifyId
			FROM kvaram_ums.TB_TMPLT_INFO
			WHERE
				TMPLT_CD = :templateCd
				AND MEDIA_CD = :mediaCd
			;
	""";
}



