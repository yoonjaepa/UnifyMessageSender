package com.jds.umsender.repository;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.jds.umsender.sql.MessageTransferSql;
import com.jds.umsender.vo.MessageTransferListVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MessageTransferListRepo {
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public MessageTransferListRepo(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public int registMessageTransfer(MessageTransferListVO messageTransfer) {
		
		int cnt = 0;
		
		SqlParameterSource param =new BeanPropertySqlParameterSource(messageTransfer);
		
		cnt = namedParameterJdbcTemplate
				.update(MessageTransferSql.INSERT_MESSAGE_TRANSFER_LIST,param);
		
		return cnt;
	} // end registMessageTransfer
	
	public int[] registBatchMessageTransfer(MessageTransferListVO[] transfer) {
		
		int[] cnt ;
		
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(transfer);
		
		cnt = namedParameterJdbcTemplate
				.batchUpdate(MessageTransferSql.INSERT_MESSAGE_TRANSFER_LIST, params);
		
		return cnt;
	}	//	end registBatchMessageTransfer()
	
	public int updateMessageTransferStatus(MessageTransferListVO messageTransfer) {
		int cnt = 0;
		SqlParameterSource param =new BeanPropertySqlParameterSource(messageTransfer);
		cnt = namedParameterJdbcTemplate
				.update(MessageTransferSql.CHANGE_TRANSFER_SATAUS, param);
		return cnt;
		
	}
	
	public int[] updateBatchMessageTransferStatus(MessageTransferListVO[] transfer) {
		int[] cnt ;
		
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(transfer);
		
		cnt = namedParameterJdbcTemplate
				.batchUpdate(MessageTransferSql.CHANGE_TRANSFER_SATAUS, params);
		
		return cnt;
	}
}
