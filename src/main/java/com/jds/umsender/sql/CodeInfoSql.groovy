package com.jds.umsender.sql

class CodeInfoSql {
	
	public static final String INSERT_CODE_INFO = """

				INSERT INTO TB_CODE_INFO
				(
					CD_ID, 
					CD_NM, 
					CD_DESC, 
					UPPERCDID, 
					USE_YN, 
					RSVRD_FLD_1, 
					RSVRD_FLD_2, 
					JSON_CONT, 
					REG_DATE, 
					REG_ID, 
					MDFY_DATE, 
					MDFY_ID
				)
				VALUES
				(
					:codeId,
					:codeName,
					:codeDesc,
					:upperCodeId,
					:useYn,
					:reserved1,
					:reserved2,
					:jsonCont,
					:registDttm,
					:registId,
					:modifyDttm,
					:modifyId
				}

	""";
}
