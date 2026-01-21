package com.SCM.NotificationRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Role;
import com.SCM.model.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class NotificationDataCreate {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

@OneToMany(fetch = FetchType.EAGER)

    private List<Tokens> tokens= new ArrayList<>();


public NotificationDataCreate(long id, List<Tokens> tokens, String staffname, String rolename, long staff_id) {
	super();
	this.id = id;
	this.tokens = tokens;

}

public NotificationDataCreate() {
	super();
	// TODO Auto-generated constructor stub
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public List<Tokens> getTokens() {
	return tokens;
}

public void setTokens(List<Tokens> tokens) {
	this.tokens = tokens;
}


	
	





	
	
	
	
	

	


    
    
    

}
