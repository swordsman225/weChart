package com.huawei.hicloud.po.message;

import com.huawei.hicloud.po.message.constant.MessageType;

public class TextMessage extends Message {
	private String Content;
	
	public TextMessage() {
		super();
		this.setMsgType(MessageType.TEXT);
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
