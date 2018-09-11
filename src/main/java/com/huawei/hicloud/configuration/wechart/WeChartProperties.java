package com.huawei.hicloud.configuration.wechart;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = WeChartProperties.WECHART_PREFIX)
public class WeChartProperties {

	public static final String WECHART_PREFIX = "wechart";

	private String token;

	private String appid;

	private String secret;
	
	private String EncodingAESKey;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getEncodingAESKey() {
		return EncodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		EncodingAESKey = encodingAESKey;
	}
	
}
