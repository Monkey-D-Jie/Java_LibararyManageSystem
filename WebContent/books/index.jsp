<%@page import="com.mysql.jdbc.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
	import="com.bms.dao.*" 
	import="com.bms.entity.*"
	import="java.util.*"
	import="com.bms.utils.*" 
%>
<html>
<head>
<title>图书管理信息系统-图书管理</title>
<link rel=stylesheet href="../inc/main.css" type="text/css">
<style type="text/css">
</style>
</head>
<body>
	<%@include file="/inc/head.jsp"%>
	<table align="center" width="980">
		<caption>图书列表</caption>
		<tr>
			<td colspan="8">
				<a href="#" onclick="window.open('<%=request.getContextPath()%>/books/add.jsp?method=add','添加图书窗口', 'width=550,height=400')">+添加新图书</a>
			</td>
		</tr>
		<tr>
			<th width="50">序号</th>
			<th width="130">图书ISBN</th>
			<th width="300">书名</th>
			<th width="150">出版社名称</th>
			<th width="70">价格</th>
			<th width="70">版本号</th>
			<th width="80">出版年份</th>
			<th width="130">操作</th>
		</tr>
<%
//在这里来显示数据库中中的数据
Books_Dao authors_Dao=new Books_Dao();
Books book=new Books();
//这里的List的是util包下的，不能是awt包下的（这个不支持泛型）
List<Books> result=authors_Dao.findEntity(book);

//在这里来显示数据库中中的数据
	Publishers_Dao publishers_Dao=new Publishers_Dao();
	Publishers publisher=new Publishers();
	String publisher_name=null;
//这里的List的是util包下的，不能是awt包下的（这个不支持泛型）


//作分页显示的类
	int pagesize=5;
	int totalsize=result.size();
	int currentPage=1;
	String changePage=request.getParameter("page");
	String DestPage=request.getParameter("DesPage");
	if(!StringUtils.isNullOrEmpty(DestPage)){
		currentPage=Integer.parseInt(DestPage);
	}
	else if(!StringUtils.isNullOrEmpty(changePage)){
		currentPage=Integer.parseInt(changePage);
	}
	SplitPage sp=new SplitPage(currentPage,pagesize,totalsize);//首页
	
	if(result!=null){
	for(int i=sp.BeginIndex();i<sp.EndIndex();i++){
		Books books=result.get(i);
%>
		<tr>
		<td style="text-align: center;"><%=i+1%></td>
		<td style="text-align: center;"><%=books.getIsbn() %></td>
		<td style="text-align: center;"><%=books.getName()%></td>
		<%
		publisher=publishers_Dao.findEntityById(books.getPublisherId());
		if(publisher!=null){
			publisher_name=publisher.getName();
		}
		 %>
		<td style="text-align: center;"><%=publisher_name%></td>
		<td style="text-align: center;"><%=books.getPrice()%></td>
		<td style="text-align: center;"><%=books.getVersion()%></td>
		<td style="text-align: center;"><%=books.getPublishTime()%></td>
		<td style="text-align: center;">
		<a href="<%=request.getContextPath()%>/BookAction.do?method=delete&delIsbn=<%=books.getIsbn()%>" onclick="return confirm('确认删除该数据吗?');">删除</a>
		&nbsp;&nbsp;
		<a href="#" onclick="window.open('<%=request.getContextPath()%>/books/edit.jsp?EditIsbn=<%=books.getIsbn()%>','修改图书窗口', 'width=750,height=400')">修改</a>
		</td>
		</tr>
<%}
} %>
	<tr>
	<td colspan="8" align="right">
	
	   记录总数	<%=totalsize%> 条	  每页显示  <%=pagesize%> 条	   当前页/总页数	  <%=currentPage%>/<%=sp.totalPage() %>    
	   <a href="<%=request.getContextPath()%>/books/index.jsp?page=1">首页</a>	
	   <a href="<%=request.getContextPath()%>/books/index.jsp?page=<%=sp.prePage()%>">上页</a>	
	   <a href="<%=request.getContextPath()%>/books/index.jsp?page=<%=sp.nextPage()%>">下页</a>	
	   <a href="<%=request.getContextPath()%>/books/index.jsp?page=<%=sp.totalPage()%>">末页</a>	
	  	<form action="" method="post">
	  	 跳转到   <input type="text" name="DesPage" size="4"/>
	  	 <input type="submit" value="Go" />
	  	</form>
	</td>
	
	</tr>	
	</table>
</body>
</html>