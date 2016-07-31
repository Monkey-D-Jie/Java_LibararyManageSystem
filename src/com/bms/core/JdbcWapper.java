package com.bms.core;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

public abstract class JdbcWapper {

	protected Connection connection;
	protected PreparedStatement statement;
	protected ResultSet resultSet;

	public JdbcWapper() {
		// 默认的配置文件
		InputStream is = JdbcWapper.class.getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);

			// 加载驱动
			Class.forName(properties.getProperty("jdbc.driver"));
			// 获取数据库连接对象
			connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
					properties.getProperty("jdbc.user"), properties.getProperty("jdbc.password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected int executeUpdate(String sql, List<Object> list) throws Exception {
		try {
			statement = connection.prepareStatement(sql);
			System.out.println(sql);
			// 绑定数据
			for (int i = 1; i <= list.size(); i++) {
				statement.setObject(i, list.get(i - 1));
			}
			return statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeResouce();
		}
	}
	
	/*protected void executeID(String sql, List<Object> values) throws Exception {
		try {
			statement = connection.prepareStatement(sql);
			*//**
			 * insert into users(a,b,c,d) valuse(?,?,?,?);select LAST_INSERT_ID();---->
			 *//*
			// 绑定数据
			for (int i = 1; i <= values.size(); i++) {
				statement.setObject(i, values.get(i - 1));
			}
			sql=sql+";select LAST_INSERT_ID();";
			System.out.println(sql);
			statement.executeUpdate();
			//
			ResultSet result=statement.getResultSet();
			while(result.next()){
				System.out.println(result.getString("LAST_INSERT_ID()"));
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			closeResouce();
		}
	}*/

	/**
	 * 查询数据
	 * @param sql 执行的sql
	 * @param values 查询条件的值
	 * @return
	 * @throws Exception
	 */
	protected ResultSet executeQurey(String sql, List<Object> values) throws Exception {
		try {
			statement = connection.prepareStatement(sql);
			if (values != null) {//排除没有查询条件
				// 绑定数据
				for (int i = 1; i <= values.size(); i++) {
					statement.setObject(i, values.get(i - 1));
				}
			}
			return statement.executeQuery();
			//这里查询的结果是空的时候，statement.executeQuery()也是null的卅？？
		} catch (Exception e){
			throw e;
		}

	}

	protected void closeResouce() throws Exception {
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

}
