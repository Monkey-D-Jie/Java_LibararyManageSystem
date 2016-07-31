package com.bms.entity;

import com.bms.annotation.Column;
import com.bms.annotation.ID;
import com.bms.annotation.Table;

@Table(name="author_book")
public class Author_book {
	
	@Column(name = "authorID")
	private Number authorID;//设置为这个类型，是放置数据类型转换的错误
	
	@ID(name = "isbn")
	private String isbn;
	
	public Number getAuthorID() {
		return authorID;
	}
	public void setAuthorID(Number authorID) {
		this.authorID = authorID;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
}
