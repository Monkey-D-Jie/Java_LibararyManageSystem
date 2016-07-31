package com.bms.entity;

import com.bms.annotation.Column;
import com.bms.annotation.ID;
import com.bms.annotation.Table;

@Table(name = "authors")
public class Authors {
	
	@ID(name = "id",isAutoIncrement=true)
	private Number id;
	
	
	@Column(name = "firstName")
	private String firstName; 
	
	
	@Column(name = "lastName")
	private String lastName;
	
	
	public Authors(){
		
	}
	public Authors(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public Number getId() {
		return id;
	}
	public void setId(Number id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
