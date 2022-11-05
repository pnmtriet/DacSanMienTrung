package com.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DacSanMienTrungApplication {

	public static void main(String[] args) {
		SpringApplication.run(DacSanMienTrungApplication.class, args);
	}

}
