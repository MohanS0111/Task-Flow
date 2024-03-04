package com.todoapplication.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	
	@Id
	private String emailId;
	private String password;
	
	public User() {
	}
	public User(String emailId, String password) {
		this.emailId = emailId;
		this.password = password;
	}
	
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
	
	@Override
	public String toString() {
		return "User [emailId=" + emailId + ", password=" + password + "]";
	}
	
	

}
