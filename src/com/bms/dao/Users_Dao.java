package com.bms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.bms.core.BaseDao;
import com.bms.entity.Users;

public class Users_Dao extends BaseDao<Users>{
	
	
		public ResultSet IsLogin(String name,String password) throws Exception{
			ResultSet result=null;
			String sql="select * from users where userName=? and `password`=?";
			List<Object> list=new ArrayList<Object>();
			if(name!=null&&password!=null){
				list.add(name);
				password=DigestUtils.md5Hex(password);
				list.add(password);
		}
		result=executeQurey(sql, list);	
		if(result!=null){
			return result;
		}else{
			return null;
		}
	}
}
