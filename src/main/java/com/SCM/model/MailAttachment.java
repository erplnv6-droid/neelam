package com.SCM.model;

public class MailAttachment {
	
private String subject;
	
	private String body;
	
	private String attachmnet;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttachmnet() {
		return attachmnet;
	}

	public void setAttachmnet(String attachmnet) {
		this.attachmnet = attachmnet;
	}

	public MailAttachment(String subject, String body, String attachmnet) {
		super();
		this.subject = subject;
		this.body = body;
		this.attachmnet = attachmnet;
	}

	public MailAttachment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
