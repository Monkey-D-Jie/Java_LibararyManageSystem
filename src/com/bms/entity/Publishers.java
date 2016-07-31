package com.bms.entity;

import com.bms.annotation.Column;
import com.bms.annotation.ID;
import com.bms.annotation.Table;

@Table(name = "publishers")
public class Publishers {
	
	@ID(name = "id",isAutoIncrement=true)
	private Number id;
	
	@Column(name = "name")
	private String name;
	
	public Publishers() {
		
	}
	
	public Publishers(String name) {
		super();
		this.name = name;
	}
	
	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
