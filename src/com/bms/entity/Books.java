package com.bms.entity;

import java.sql.Date;

import com.bms.annotation.Column;
import com.bms.annotation.ID;
import com.bms.annotation.Table;

@Table(name = "books")
public class Books {
	
	
	
	@ID(name = "isbn")
	private String isbn;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "version")
	private Number version;
	
	@Column(name = "price")
	private Number price;
	
	@Column(name = "publishTime")
	private Date publishTime;
//	private String publishTime;
	
	@Column(name = "publisherId")
	private Number publisherId;
	
	public Books(){
		
	}
	public Books(String isbn, String name, Number version, Number price, Date book_publishTime, Number book_publisherId,int[] book_authorIds) {
		super();
		this.isbn = isbn;
		this.name = name;
		this.version = version;
		this.price = price;
		this.publishTime = book_publishTime;
		this.publisherId = book_publisherId;
		this.authorID=book_authorIds;
	}
//	@Column(name = "authorID")
	private int[] authorID;
	public int[] getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int[] authorID) {
		this.authorID = authorID;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Number getVersion() {
		return version;
	}

	public void setVersion(Number version) {
		this.version = version;
	}

	public Number getPrice() {
		return price;
	}

	public void setPrice(Number price) {
		this.price = price;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Number getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Number publisherId) {
		this.publisherId =  publisherId;
	}
	
	
	
}
