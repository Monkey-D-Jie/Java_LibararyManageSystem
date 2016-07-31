<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page
	import="com.bms.dao.*" 
	import="com.bms.entity.*"
	import="java.util.*"

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>图书管理信息系统-出版社管理</title>
<link rel=stylesheet href="../inc/main.css" type="text/css">
<style type="text/css">
</style>
</head>
<body>
	<%@include file="/inc/head.jsp"%>
	<table align="center" width="980">
		<tr>
			<td align="right">
				<a href="#" onclick="window.open('<%=request.getContextPath()%>/publishers/add.jsp','添加出版社窗口', 'width=300,height=150')">添加出版社</a>
			</td>
		</tr>
	</table>
	<table align="center" width="980">
		<caption>出版社列表</caption>
		<tr>
			<th width="200">出版社ID</th>
			<th width="550">出版社名称</th>
			<th width="230">操作</th>
		</tr>
<%
//在这里来显示数据库中中的数据
	Publishers_Dao publishers_Dao=new Publishers_Dao();
	Publishers publisher=new Publishers();
//这里的List的是util包下的，不能是awt包下的（这个不支持泛型）
	List<Publishers> result=publishers_Dao.findEntity(publisher);
if(result!=null){
for(Publishers publishers:result){
%>
		<tr>
		<td style="text-align: center;"><%=publishers.getId()%></td>
		<td style="text-align: center;"><%=publishers.getName()%></td>
		<td style="text-align: center;">
		<a href="<%=request.getContextPath()%>/PublishersAction.do?method=delete&delID=<%=publishers.getId()%>" onclick="return confirm('确认删除该数据吗?');">删除</a>
		&nbsp;&nbsp;
		<a href="#" onclick="window.open('<%=request.getContextPath()%>/publishers/edit.jsp?EditId=<%=publishers.getId()%>','修改出版社窗口', 'width=300,height=150')">修改</a>
		</td>
		</tr>
<%	}
}
%>		
	</table>
</body>
</html>