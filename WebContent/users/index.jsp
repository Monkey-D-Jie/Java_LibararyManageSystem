<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page 
import="com.bms.dao.*" 
import="com.bms.entity.*"
import="java.util.*"
%>
<html>
<head>
<title>图书管理信息系统-用户管理</title>
<link rel=stylesheet href="../inc/main.css" type="text/css">
<style type="text/css">
</style>
</head>
<body>

	<!-- 引入head.jsp文件 -->
	<%@include file="/inc/head.jsp"%>
	<table align="center" width="980">
		<tr>
			<td align="right">
				<a href="#" onclick="window.open('<%=request.getContextPath()%>/users/add.jsp','添加用户窗口', 'width=300,height=150')">添加用户</a>
			</td>
		</tr>
	</table>
	<table align="center" width="980" class="head" cellpadding="0" cellspacing="0">
		<caption>用户列表</caption>
		<tr>
			<th width="150">用户ID</th>
			<th width="300">登录帐户</th>
			<th width="300">真实姓名</th>
			<th width="230">操作</th>
		</tr>
<%
//在这里来显示数据库中中的数据
Users_Dao userDao=new Users_Dao();
Users user=new Users();
//这里的List的是util包下的，不能是awt包下的（这个不支持泛型）
	List<Users> result=userDao.findEntity(user);
if(result!=null){
	

for(Users users:result){
%>
		<tr>
		<td style="text-align: center;"><%=users.getId()%></td>
		<td style="text-align: center;"><%=users.getUsername()%></td>
		<td style="text-align: center;"><%=users.getRealname()%></td>
		<td style="text-align: center;">
		<a href="<%=request.getContextPath()%>/UsersAction.do?deleteusers=method_del&ID=<%=users.getId()%>" onclick="return confirm('确认删除该数据吗?');">删除</a>
		&nbsp;&nbsp;
		<a href="#" onclick="window.open('<%=request.getContextPath()%>/users/modify.jsp?ModId=<%=users.getId()%>','修改用户窗口', 'width=300,height=200')">修改</a>
		</td>
		</tr>
<%	} 
}
%>
	</table>
</body>
</html>