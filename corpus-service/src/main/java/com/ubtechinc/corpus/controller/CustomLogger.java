package com.ubtechinc.corpus.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Component
@Slf4j
@Async("threadPoolExecutor")
public class CustomLogger {

	public void info(String msg) {
		log.info(msg);
	}

	public void debug(String msg) {
		log.debug(msg);
	}

	public void error(String msg) {
		log.error(msg);
	}

	public void trace(String msg) {
		log.trace(msg);
	}
}
