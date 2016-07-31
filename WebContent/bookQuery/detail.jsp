<%@page import="java.sql.ResultSet"%>
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
<title>朗慧图书管理信息系统-图书管理</title>
<link rel=stylesheet href="../inc/main.css" type="text/css">
<style type="text/css">
table select {
	width: 200px;
}

.tcolumn {
	background: #DDDDFF;
}
</style>
</head>
<body>
<%
String isbn=request.getParameter("isbn");
Books the_book=new Books();
Books_Dao book_dao=new Books_Dao();
if(isbn!=null){
	//通过isbn或者book_name找到那本书
	the_book=book_dao.findEntityById(isbn);
%>
	<table>
		<caption>图书详细信息</caption>
		<%
		if(the_book!=null){
		%>
		<tr>
			<td width="250" class="tcolumn">图书ISBN</td>
			<td width="730">
				<input type="text" name="isbn" size="40" id="isbn" value="<%=the_book.getIsbn()%>" > 	
			</td>
		</tr>
		<tr>
			<td class="tcolumn">书名</td>
			<td>
			<input type="text" name="isbn" size="40"  value="<%=the_book.getName()%>" > 	
			</td>
		</tr>
		<tr>
			<td class="tcolumn">出版社</td>
			<td>
		<%	
		Publishers publisher=new Publishers();
		Publishers_Dao publisher_dao=new Publishers_Dao();
		publisher=publisher_dao.findEntityById(the_book.getPublisherId());
		%>
		<input type="text" name="isbn" size="40"  value="<%=publisher.getName()%>" > 	
			</td>
		</tr>
		<tr>
			<td class="tcolumn">价格</td>
			<td>
			<input type="text" name="price" size="40"  value="<%=the_book.getPrice()%>" > 	
			</td>
		</tr>
		<tr>
			<td class="tcolumn">出版年份</td>
			<td>
		<input type="text" name="publishtime" size="40"  value="<%=the_book.getPublishTime()%>" > 		
			</td>
		</tr>
		<tr>
			<td class="tcolumn">版本号</td>
			<td>
		<input type="text" name="version" size="40"  value="<%=the_book.getVersion()%>" > 		
			</td>
		</tr>
		<tr>
			<td class="tcolumn">作者</td>
			<td>
			<%	
			ResultSet authors=null;
			Authors author=new Authors();
			Authors_Dao author_dao=new Authors_Dao();
			authors=author_dao.findAuthor(isbn);
			while(authors.next()){
			%>
			<input type="text" name="author" size="40"  value="<%=authors.getString("firstName")%>&nbsp;<%=authors.getString("lastName")%>"> 		
			<% 	
			}
			%>	
			</td>
		</tr>
<%
	}
}
%>
		<tr>
			<td colspan="2" align="center" class="tcolumn"><input
				type="button" value="关闭窗口" onclick="window.close();"></td>
		</tr>
	</table>
</body>
</html>