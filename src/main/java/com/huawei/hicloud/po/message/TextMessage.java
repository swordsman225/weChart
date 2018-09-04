package com.huawei.hicloud.po.message;

public class TextMessage extends Message {
	private String Content;
	
	public TextMessage() {
		super();
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
