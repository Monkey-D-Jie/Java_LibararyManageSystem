package com.bms.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.bms.dao.Authors_Dao;

public class Edit_Test {

	@Test
	public void test() throws SQLException {
//		Authors author=new Authors();
		Authors_Dao author_dao=new Authors_Dao();
		String EditIsbn="0130852473";
		ResultSet authors=null;
		//有关作者
		authors=author_dao.findAuthor(EditIsbn);
		while(authors.next()){
		System.out.println(authors.getString("firstName")+" "+authors.getString("lastName"));
		}
	}
}
