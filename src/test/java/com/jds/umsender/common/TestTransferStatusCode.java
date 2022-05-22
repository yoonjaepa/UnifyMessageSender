package com.jds.umsender.common;



import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestTransferStatusCode {

	@Test
	void test() {
		log.info(TransferStatusCode.RECEIVE_RESULT.toString());
		log.info(TransferStatusCode.RECEIVE_RESULT.name());
		log.info(TransferStatusCode.RECEIVE_RESULT.getTransCode());
	}

}
