package com.huawei.hicloud;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= {"com.huawei.hicloud.dao"})
public class WeChartApplication {

	private static final Logger logger = LoggerFactory.getLogger(WeChartApplication.class);
	
	public static void main(String[] args) {

		logger.info("### WeChartApplication start ...");
		SpringApplication.run(WeChartApplication.class, args);
		
	}

}
