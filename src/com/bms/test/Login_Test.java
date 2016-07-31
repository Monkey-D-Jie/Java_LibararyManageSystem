package com.bms.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.bms.dao.Users_Dao;

public class Login_Test {

	@Test
	public void test() throws Exception {
		//测试
		//发现的问题是没有查询到值-----》可能是语句的错误
			String name="admin";
			String password="123456";
//			password=DigestUtils.md5Hex(password);
			Users_Dao test=new Users_Dao();
			ResultSet result=test.IsLogin(name, password);
//			System.out.println(result.toString()));
			while(result.next()){
				System.out.println(result.getString("userName"));
			}
			
	}

}
