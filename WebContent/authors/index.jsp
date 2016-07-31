<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
	import="com.bms.dao.*" 
	import="com.bms.entity.*"
	import="java.util.*"
%>
<html>
<head>
<title>图书管理信息系统-作者管理</title>
<link rel=stylesheet href="../inc/main.css" type="text/css">
<style type="text/css">
</style>
</head>
<body>
	<%@include file="/inc/head.jsp"%>
	<table align="center" width="980">
		<tr>
			<td align="right">
				<a href="#" onclick="window.open('<%=request.getContextPath()%>/authors/add.jsp','添加作者窗口', 'width=300,height=150')">添加作者</a>
			</td>
		</tr>
	</table>
	<table align="center" width="980">
		<caption>作者列表</caption>
		<tr>
			<th width="150">作者ID</th>
			<th width="300">名</th>
			<th width="300">姓</th>
			<th width="230">操作</th>
		</tr>
<%
Authors_Dao authors_Dao=new Authors_Dao();
Authors author=new Authors();
//这里的List的是util包下的，不能是awt包下的（这个不支持泛型）
	List<Authors> result=authors_Dao.findEntity(author);
if(result!=null){
for(Authors authors:result){
%>
		<tr>
		<td style="text-align: center;"><%=authors.getId()%></td>
		<td style="text-align: center;"><%=authors.getFirstName() %></td>
		<td style="text-align: center;"><%=authors.getLastName()%></td>
		<td style="text-align: center;">
		<a href="<%=request.getContextPath()%>/AuthorsAction.do?method=delete&delID=<%=authors.getId()%>" onclick="return confirm('确认删除该数据吗?');">删除</a>
		&nbsp;&nbsp;
		<a href="#" onclick="window.open('<%=request.getContextPath()%>/authors/edit.jsp?authorID=<%=authors.getId()%>','修改作者窗口', 'width=300,height=200')">修改</a>
		</td>
		</tr>
<%}

}%>
	</table>
</body>
</html>