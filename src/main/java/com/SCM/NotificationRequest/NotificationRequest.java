package com.SCM.NotificationRequest;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="notification_tbl")
public class NotificationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @ElementCollection
	private List<String> token;
	
	private String title;
	
	private String body;

	public NotificationRequest(long id, List<String> token, String title, String body) {
		super();
		this.id = id;
		this.token = token;
		this.title = title;
		this.body = body;
	}

	public NotificationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getToken() {
		return token;
	}

	public void setToken(List<String> token) {
		this.token = token;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
