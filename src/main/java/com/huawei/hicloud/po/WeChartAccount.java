package com.huawei.hicloud.po;

/**
 * WeChat Official Account infomation.
 * 
 * @author JiaxuYan
 * @since 2018-9-12 09:12:02
 */
public class WeChartAccount extends BaseClass {

	private String id;

	/** Account owner name. */
	private String name;

	private String appid;

	private String token;

	private String secret;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
