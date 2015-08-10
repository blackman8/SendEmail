package com.bmb.bean;

/**
 * 发送多接收者类型邮件的基本信息
 */
public class MultiMailSenderInfo extends MailSenderInfo {
	// 邮件的接收者，可以有多个
	private String[] receivers;
	// 邮件的抄送者，可以有多个
	private String[] ccs;

	public String[] getCcs() {
		return ccs;
	}

	public void setCcs(String[] ccs) {
		this.ccs = ccs;
	}

	public String[] getReceivers() {
		return receivers;
	}

	public void setReceivers(String[] receivers) {
		this.receivers = receivers;
	}
}
