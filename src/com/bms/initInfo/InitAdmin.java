package com.bms.initInfo;

import com.bms.dao.Users_Dao;
import com.bms.entity.Users;

public class InitAdmin {
	public static void main(String[] args) throws Exception {
		Users admin=new Users();
		admin.setUsername("admin");
		admin.setRealname("管理员a");
		admin.setPassword("admin");
		
		Users_Dao user=new Users_Dao();
		user.saveEntity(admin);
	}
}
