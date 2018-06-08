package com.ubtechinc.corpus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CorpusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorpusServiceApplication.class, args);
	}
}