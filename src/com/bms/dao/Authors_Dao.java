package com.bms.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bms.core.BaseDao;
import com.bms.entity.Authors;

public class Authors_Dao extends BaseDao<Authors>{
	
	public ResultSet findAuthor(Serializable id){
		ResultSet author_result=null;
		String sql="select * from authors where id in(select authorID from author_book where isbn=?)";
		List<Object> list=new ArrayList<Object>();
		if(id!=null){
			list.add(id);
		}
		try {
			author_result=executeQurey(sql, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(author_result!=null){
			return author_result;
		}else{
			return null;
		}
	}
	
	public ResultSet findOthterAuthor(Serializable id){
		ResultSet other_author=null;
		String sql="select * from authors where id not in(select authorID from author_book where isbn=?)";
		List<Object> list=new ArrayList<Object>();
		if(id!=null){
			list.add(id);
		}
		try {
			other_author=executeQurey(sql, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(other_author!=null){
			return other_author;
		}else{
			return null;
		}
	}
}
