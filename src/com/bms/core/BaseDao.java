package com.bms.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bms.annotation.Column;
import com.bms.annotation.ID;
import com.bms.annotation.Table;


@SuppressWarnings("unchecked")
public abstract class BaseDao<T> extends JdbcWapper {

	/**
	 * 将数据保存到数据库中
	 * 
	 * @param t
	 *            保存的数据的对象
	 * @return
	 * @throws Exception
	 */	
	
	
	public final int saveEntity(T t) throws Exception {
		// 目标的sql:inser into users () values(?,?,?)
		StringBuffer buffer = null;
		Class<T> clazz = (Class<T>) t.getClass();
		// 判断是否加有Table注解
		boolean flag = clazz.isAnnotationPresent(Table.class);
		if (!flag) {// 没有注解则抛出异常
			throw new Exception(clazz.getName() + "没有添加Table注解");
		}
		// 有注解--->StringBuffer对象准备拼接sql语句
		buffer = new StringBuffer();
		buffer.append("insert into ");
		// 获取表的注解
		Table table = clazz.getAnnotation(Table.class);
		buffer.append(table.name() + "(");
		// 拼接插入的列---->保存对应属性的值
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			List<Object> values = new ArrayList<Object>();
			for (Field field : fields) {
				// 设置为可以访问该属性的值
				field.setAccessible(true);
				// 判断是否为主键
				boolean isID = field.isAnnotationPresent(ID.class);
				if (isID) {
					ID id = field.getAnnotation(ID.class);
					if (!id.isAutoIncrement()) {// 非自增主键，需要在程序里面进行赋值
						buffer.append(id.name() + ",");
						// 存储id的值
						values.add(field.get(t));
					}
				} else {// 普通列
					Column column = field.getAnnotation(Column.class);
					buffer.append(column.name() + ",");
					// 存储值
					values.add(field.get(t));
				}
			}
			// 删除多余的","
			buffer.deleteCharAt(buffer.length() - 1).append(") values(");
			// 遍历的添加值的集合--->决定？个数
			for (int i = 0; i < values.size(); i++) {
				buffer.append("?").append(",");
			}
			// 删除最后一个逗号
			buffer.deleteCharAt(buffer.length() - 1).append(")");
			// jdbc里面的操作--->将数据保存到数据库
			System.out.println("执行的sql语句为：" + buffer);
			return executeUpdate(buffer.toString(), values);
		}
		return 0;
	}

	public final int updateEntity(T t) throws Exception {
		// update users set username=?,address=? where id=?
		StringBuffer buffer = null;
		Class<T> clazz = (Class<T>) t.getClass();
		boolean flag = clazz.isAnnotationPresent(Table.class);
		if (!flag) {
			throw new Exception(clazz.getName() + "没有添加Table注解");
		}
		buffer = new StringBuffer();
		buffer.append("update ");
		// 确定更新表
		Table table = clazz.getAnnotation(Table.class);
		buffer.append(table.name()).append(" ");
		// 确定更新列
		// 获取到所有的属性
		Field[] fields = clazz.getDeclaredFields();
		List<Object> values = null;
		if (fields != null && fields.length > 0) {
			// 定义变量存储id所对应的字段
			Field idField = null;

			// 初始化数据的存储集合
			values = new ArrayList<Object>();
			buffer.append("set ");
			for (Field field : fields) {// 处理普通的属性---->是否存在的有column这种注解的属性
				field.setAccessible(true);
				boolean isColum = field.isAnnotationPresent(Column.class);
				if (isColum) {// 获取值 拼接sql里面
					// 是否是初始值问题
					Object value = field.get(t);
					if (value != null && !isInitValue(value)) {// ---基本数据类型中数字类型和浮点类型的数据
						Column column = field.getAnnotation(Column.class);
						buffer.append(column.name()).append("=").append("?").append(",");
						// 存储该列的值
						values.add(value);
					}
				} else {
					boolean isID = field.isAnnotationPresent(ID.class);
					if (isID) {
						idField = field;// 将当前循环得到的field赋值给
					}
				}
			}
			// 如果没有普通列--->update users set
			if (values.isEmpty()) {
				throw new Exception(clazz.getName() + "没有指定更新的列");
			}
			// 删除最后一个逗号
			buffer.deleteCharAt(buffer.length() - 1).append(" ");
			// 处理主键字段--->更新条件
			if (idField != null) {// 指定了id的列
				idField.setAccessible(true);
				Object value = idField.get(t);
				if (value != null && !isInitValue(value)) {
					buffer.append("where ");
					// 得到id的注解
					ID id = idField.getAnnotation(ID.class);
					buffer.append(id.name()).append("=").append("?");
					// 存id的值
					values.add(value);
				} else {// id这个列是初始值的问题
					throw new Exception(clazz.getName() + "没有制定更新条件的值");
				}
			} else {
				throw new Exception(clazz.getName() + "没有制定更新条件及ID属性");
			}

			// 完成更新操作
			System.out.println(buffer.toString());
			return this.executeUpdate(buffer.toString(), values);
		} else {
			throw new Exception(clazz.getName() + "没有添加任何属性");
		}
	}

	public final int deleteById(T t) throws Exception {
		// 目标sql：delete from users where id=?
		StringBuffer buffer = null;
		Class<T> clazz = (Class<T>) t.getClass();
		boolean flag = clazz.isAnnotationPresent(Table.class);
		if (!flag) {
			throw new Exception(clazz.getName() + "没有添加Table注解");
		}
		buffer = new StringBuffer();
		buffer.append("delete from ");
		// 获取table注解
		Table table = clazz.getAnnotation(Table.class);
		buffer.append(table.name());
		// 获取属性
		Field[] fields = clazz.getDeclaredFields();
		List<Object> values;
		if (fields != null && fields.length > 0) {
			values = new ArrayList<Object>();
			for (int i = 0; i < fields.length; i++) {// 开始找id 主键
				Field field = fields[i];

				boolean isID = field.isAnnotationPresent(ID.class);
				if (isID) {// 具有删除的条件------>如果标示为id的属性不是在第一个
					field.setAccessible(true);
					// 获取属性的值
					Object value = field.get(t);
					if (value != null && !isInitValue(value)) {// 排除初始值
						ID id = field.getAnnotation(ID.class);
						buffer.append(" where ").append(id.name()).append("=?");
						values.add(value);
					} else {
						throw new Exception(clazz.getName() + "加了ID注解但是没有给id属性进行赋值");
					}
					// 判断是否为初始值的问题
					break;
				} else if (i == fields.length - 1) {// 整个for循环执行到最后一次循环
					throw new Exception(clazz.getName() + "没有添加ID标示的属性");
				}
			}
		} else {
			throw new Exception(clazz.getName() + "没有添加任何属性");
		}
		
		System.out.println("执行的sql语句为：" + buffer);
		return executeUpdate(buffer.toString(), values);
	}

	public final T findEntityById(Serializable id) throws Exception{
		// --->判断id数据是否有效
				// 目标sql：select * from users where id=?
				//这里为什么要用Serializable来作为传入ID的类型喃
				//--------------------->问题
				/**
				 * 解答：传入的参数有其类型，可能是int，可能是String等等数据类型。
				 * 这里如果不是用Serializable来把ID置成是通用型的类型，那你就得进行数据的强转。
				 * 就是在传入参数将数据强转为这里指定的数据类型。这样的话，就很可能有我要传的参数不能
				 * 强转为这里的这种参数类型的情况的发生，自然就不能执行到这个方法中的逻辑
				 * Serializable来修饰的话，不管是什么类型的基本数据，因为大都是实现了Serializable
				 * 接口的，所以就可以不用再担心数据类型的转化问题;你要做的只是传值就好了，我这边保证你的参数可以用在
				 * 这里的执行逻辑当中！
				 * 就是这么溜，滴！
				 */
				StringBuffer buffer = null;
				// 获取父类的泛型化参数
				ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();// 泛型父类
//				System.out.println("得到的this.getClass()，"+this.getClass().toString());
//				System.out.println("this.getClass().getGenericSuperclass()为："+this.getClass().getGenericSuperclass().toString());
//				System.out.println("得到的type.getActualTypeArguments()---》"+type.getActualTypeArguments()[0].toString());
				Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
				//这里的用个下标0表示渠道超类泛型化的参数中的第一个！
				/**
				 * type.getActualTypeArguments()得到的是超类的泛型参数的数组，
				 * 在这里也就是BaseDao<T>中的T，意即这是一个大小为1的数组，元素就只有T，
				 * 因为用了user将T实例化成user了，所以这里得到的就是user 类了。
				 * 为什么是0？说啦，是数组大小为0啦，再进一步来说得话，这里的超类就只是设置了一个泛型参数啦
				 * Just one！
				 */
				boolean flag = clazz.isAnnotationPresent(Table.class);
				if (!flag) {
					throw new Exception(clazz.getName() + "没有添加Table注解");
				}
				buffer = new StringBuffer();
				buffer.append("select * from ");
				// 确定查询的表
				Table table = clazz.getAnnotation(Table.class);
				buffer.append(table.name());

				Field[] fields = clazz.getDeclaredFields();
				if (fields != null && fields.length > 0) {
					// 开始拼接id查询条件
					for (int i = 0; i < fields.length; i++) {// 属性中有加了ID注解的属性，没加
						Field field = fields[i];
						boolean isID = field.isAnnotationPresent(ID.class);
						if (isID) {// 获取id属性对应在数据库中的字段
							buffer.append(" where ");
							buffer.append(field.getAnnotation(ID.class).name()).append("=").append("?");
							break;
						} else if (i == fields.length - 1) {// 找到最后都没有找到ID字段
							throw new Exception(clazz.getName() + "没有添加ID标示的属性");
						}
					}
					try {
						System.out.println("执行的sql语句为：" + buffer.toString());
						// 数据封装成List集合
						List<Object> values = new ArrayList<Object>();
						values.add(id);
						// 执行查询得到结果集
						resultSet = this.executeQurey(buffer.toString(), values);
						List<T> result = convertValues(clazz, fields);
						return result.isEmpty() ? null : result.get(0);
					} catch (Exception e) {
						throw e;
					} finally {// 释放资源
//						this.closeResouce();
					}
				} else {
					throw new Exception(clazz.getName() + "没有添加任何属性");
				}
			
	}
	
	public final List<T> findEntity(T t) throws Exception {
		// 目标sql：select * from users
		StringBuffer buffer = null;
		Class<T> clazz = (Class<T>) t.getClass();
		boolean flag = clazz.isAnnotationPresent(Table.class);
		if (!flag) {
			throw new Exception(clazz.getName() + "没有添加Table注解");
		}
		buffer = new StringBuffer();
		buffer.append("select * from ");
		// 确定查询的表
		Table table = clazz.getAnnotation(Table.class);
		buffer.append(table.name());

		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			try {
				System.out.println("执行的sql语句为："+buffer.toString());
				// 执行查询得到结果集
				resultSet = this.executeQurey(buffer.toString(), null);
				List<T> result = convertValues(clazz, fields);
				return result.isEmpty() ? null : result;
			} catch (Exception e) {
				throw e;
			} finally {// 释放资源
//				this.closeResouce();
			}
		} else {
			throw new Exception(clazz.getName() + "没有添加任何属性");
		}
	}
	
	//-------------------------------------------------
		/*
		 * 定义重写findEntityByid的方法，返回
		 */
		public ResultSet findAuthorById(Serializable id){
					
//					ResultSet result=null;
					StringBuffer buffer = null;
					// 获取父类的泛型化参数
					ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();// 泛型父类
					Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
					boolean flag = clazz.isAnnotationPresent(Table.class);
					if (!flag) {
						try {
							throw new Exception(clazz.getName() + "没有添加Table注解");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					buffer = new StringBuffer();
					buffer.append("select authorID from ");
					// 确定查询的表
					Table table = clazz.getAnnotation(Table.class);
					buffer.append(table.name()+" where isbn=?");

					/*Field[] fields = clazz.getDeclaredFields();
					if (fields != null && fields.length > 0) {
						// 开始拼接id查询条件
						for (int i = 0; i < fields.length; i++) {// 属性中有加了ID注解的属性，没加
							Field field = fields[i];
							boolean isID = field.isAnnotationPresent(ID.class);
							if (isID) {// 获取id属性对应在数据库中的字段
								buffer.append(" where ");
								buffer.append(field.getAnnotation(ID.class).name()).append("=").append("?");
								break;
							} else if (i == fields.length - 1) {// 找到最后都没有找到ID字段
								try {
									throw new Exception(clazz.getName() + "没有添加ID标示的属性");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}*/
						try {
							System.out.println("执行的sql语句为：" + buffer.toString());
							// 数据封装成List集合
							List<Object> values = new ArrayList<Object>();
							values.add(id);
							// 执行查询得到结果集
							resultSet=this.executeQurey(buffer.toString(), values);
							
						} 
						catch (Exception e) {
								e.printStackTrace();
						} 
						 finally {// 释放资源
							try {
								this.closeResouce();
							} 
							catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return resultSet;
					
					/*else {
						
						try {
							throw new Exception(clazz.getName() + "没有添加任何属性");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}*/
			
		}
		
		
		public  ResultSet OtherAuthorById(Serializable id){
			
			List<Object> values=new ArrayList<Object>();
			StringBuffer buffer = null;
			// 获取父类的泛型化参数
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();// 泛型父类
			Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
			boolean flag = clazz.isAnnotationPresent(Table.class);
			if (!flag) {
				try {
					throw new Exception(clazz.getName() + "没有添加Table注解");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			buffer = new StringBuffer();
			buffer.append("select * from ");
			// 确定查询的表
			Table table = clazz.getAnnotation(Table.class);
			buffer.append(table.name()+" where id!=?");
			String sql=buffer.toString();
			System.out.println("在OtherAuthorById方法中执行的SQL语句为:"+sql);
			values.add(id);
			try {
				resultSet=this.executeQurey(sql, values);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return resultSet;
		}
		//-------------------------------------------------
		
		
	

	private List<T> convertValues(Class<T> clazz, Field[] fields)
			throws SQLException, InstantiationException, IllegalAccessException {
		// 存的集合
		List<T> result = new ArrayList<T>();
		T temp = null;
		// 处理结果集
		while (resultSet.next()) {//resultSet中存放的是一行一行的数据
			temp=clazz.newInstance();
			// 转换每行的数据
			for(Field field:fields){
				String lable=null;//列名
				//获取列名----> id  column
				boolean isID = field.isAnnotationPresent(ID.class);
				if(isID){
					lable=field.getAnnotation(ID.class).name();
				}else{
					boolean isColum = field.isAnnotationPresent(Column.class);
					if(isColum){
						lable=field.getAnnotation(Column.class).name();
					}
				}
				if(lable!=null){//找到列名，
					/**
					 * ↑----》它这里的前提就是那些属性都要有注解才是合法的哟
					 * 思考：列名为空的话，那就直接抛出异常就是了呀。
					 * 这里本身要做的就是查询那些合法录入的信息。
					 */
					field.setAccessible(true);
					//得到该属性在数据库中的值
					Object value = resultSet.getObject(lable);
					//特殊处理,数据库中取出的不一定就是int之类的基本数据类型，大多数情况下是封装类
					value=convertData(value);
					field.set(temp, value);//将获取的值放到temp代表的这行中的指定列
				}
			}
			result.add(temp);//这里的result就是单独的用来存放一张表中的一行数据而已啦，不干别的事情
			//这里存的是？单独的一种表，还是说不同的表呢？-----》是一张表中的所有数据
			System.out.println("----->得到的result的大小是："+result.size());
			temp = null;
		}
		//循环结束后，数据库中的表的每行数据就都被放到了temp中，这些temp共同组成了这个表---》result
		return result;
	}
	
	

	/**
	 * 特殊类型的数据特殊处理
	 * @param value
	 * @return
	 * 问题说明：导致这种情况出现的原因是
	 * 在java代码端定义的属性数据类型跟从数据库中抽取出来的数据类型不匹配
	 * 比如这里：
	 * 在数据库中的得到的数据类型是BigDecimal型的，在entity类中
	 * 定义的则是
	 * 
	 */
	private Object convertData(Object value) {
		if(value instanceof BigDecimal){
			return ((BigDecimal) value).floatValue();
		}
		return value;
	}

	/**
	 * 整形和浮点类型的数据
	 * 
	 * @param value
	 * @return
	 */
	private boolean isInitValue(Object value) {
		if (value.equals(0) || value.equals(0.0)) {
			return true;
		}
		return false;
	}

}