package com.bms.dao;

import java.sql.SQLException;

import com.bms.core.BaseDao;
import com.bms.entity.Books;

public class Books_Dao extends BaseDao<Books>{
		public void insert_Book(Books books){
			//设置事务不自动提交
			try {
				//这里数据库语句的批量执行，用事务的机制，还需要再下点儿功夫来看看哦！
				//设置事务不自动提交
				connection.setAutoCommit(false);
				String sql="insert into books(isbn,name,version,price,publishTime,publisherId) values(?,?,?,?,?,?)";
				statement = connection.prepareStatement(sql);
				statement.setString(1, books.getIsbn());
				statement.setString(2, books.getName());
				statement.setInt(3, (int)books.getVersion());
				statement.setFloat(4, (float)books.getPrice());
				statement.setDate(5, books.getPublishTime());
				statement.setInt(6, (int)books.getPublisherId());
				statement.execute();
				//接下来添加author_book
				
				int authorID[] =books.getAuthorID();
				String sql2="insert into author_book(authorID,isbn) values(?,?)";
//				sql="insert into author_book(authorID,isbn) values(?,?)";
				statement = connection.prepareStatement(sql2);
				for(int i=0;i<authorID.length;i++){
					statement.setInt(1, authorID[i]);
					statement.setString(2, books.getIsbn());
					statement.addBatch();//批量处理事务
				}
				statement.executeBatch();//批量执行sql语句
				connection.commit();
		
			} 
			catch (SQLException e) {
				//出错的话就回滚请求！
				try {
					connection.rollback();
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
}
