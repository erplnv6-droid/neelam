package com.SCM.NotificationRequest;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="tokens" , uniqueConstraints = {
		  
		  @UniqueConstraint(columnNames = {"staffid","token"}),
}
)
public class Tokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	private String token;
	
	private String staffname;
	
	private String rolename;
	
	private long staffid;
	

	
	
	


	public Tokens(long id, String token, String staffname, String rolename, long staffid) {
		super();
		this.id = id;
		this.token = token;
		this.staffname = staffname;
		this.rolename = rolename;
		this.staffid = staffid;

	}

	public Tokens() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public long getStaffid() {
		return staffid;
	}

	public void setStaffid(long staffid) {
		this.staffid = staffid;
	}

	


	
	

	
	
	
	
}
