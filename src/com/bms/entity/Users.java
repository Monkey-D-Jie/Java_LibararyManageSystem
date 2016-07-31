package com.bms.entity;

import com.bms.annotation.Column;
import com.bms.annotation.ID;
import com.bms.annotation.Table;

@Table(name = "users")
public class Users {
	
	public Users(){
		
	}
	
	public Users( Number id,String username, String password, String realname) {
		this.id=id;
		this.username = username;
		this.password = password;
		this.realname = realname;
	}
	public Users(String username, String password, String realname) {
		this.username = username;
		this.password = password;
		this.realname = realname;
	}

	
	
	@ID(name = "id",isAutoIncrement=true)
	private Number id;
	
	@Column(name = "userName")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "realName")
	private String realname;
	
	
	
	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	
}
