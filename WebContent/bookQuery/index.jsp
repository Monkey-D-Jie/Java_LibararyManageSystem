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
<title>图书管理信息系统-图书管理</title>
<link rel=stylesheet href="<%=request.getContextPath() %>/inc/main.css" type="text/css">
<style type="text/css">
</style>
<script type="text/javascript">
function checkForm(form1) {
	if (form1.isbn.value == "") {
		alert('isbn必须输入！');
		form1.isbn.focus();
		return;
	}
	form1.submit();
	//alert("点击刷新！");
	//refreshOpener();
}
</script>
</head>
<body>
	<%@include file="/inc/head.jsp"%>
	<form action="" method="post" name="form1">
		<table align="center" width="980">
			<tr>
				<td>ISBN <input type="text" name="isbn" size="20"> 
					书名  <input type="text" name="name" size="20" > 
					<input type="button" value="快速搜索" onclick="checkForm(form1);">
				</td>
			</tr>
		</table>
	</form>
	<table align="center" width="980">
		<caption>图书列表</caption>
		<tr>
			<th width="50">序号</th>
			<th width="130">图书ISBN</th>
			<th width="300">书名</th>
			<th width="150">出版社名称</th>
			<th width="70">价格</th>
			<th width="70">版本号</th>
			<th width="80">出版年份</th>
			<th width="80">操作</th>
		</tr>
<%
//在这里来显示数据库中中的数据
	Books_Dao books_dao=new Books_Dao();
	Books book=new Books();
//这里的List的是util包下的，不能是awt包下的（这个不支持泛型）
	List<Books> result=books_dao.findEntity(book);

//在这里来显示数据库中中的数据
	Publishers_Dao publishers_Dao=new Publishers_Dao();
	Publishers publisher=new Publishers();
	String publisher_name=null;
//这里的List的是util包下的，不能是awt包下的（这个不支持泛型）
	int count=0;
	boolean flag=true;
	//Map<String,Integer> map=new HashMap<String,Integer>();
	String ch_isbn=request.getParameter("isbn");
	if(ch_isbn!=null){
		flag=false;
	}
	if(flag){
	if(result!=null){
for(Books books:result){
%>
		<tr>
		<%
			session.setAttribute(books.getIsbn(),count);
			//out.print(map.get(books.getIsbn()));
		%>
		<td style="text-align: center;"><%=count++%></td>
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
		<a href="#" onclick="window.open('<%=request.getContextPath()%>/bookQuery/detail.jsp?isbn=<%=books.getIsbn()%>','查看图书信息窗口', 'width=500,height=300')">详细信息</a>
		</td>
		</tr>
<%		}
	}
}else{
//if(ch_isbn!=null){
	Books the_book=new Books();
	the_book=books_dao.findEntityById(ch_isbn);
	if(the_book!=null){
		%>
		<tr>
		<td style="text-align: center;"><%=session.getAttribute(ch_isbn)%></td>
		<%session.removeAttribute(ch_isbn); %>
		<td style="text-align: center;"><%=the_book.getIsbn() %></td>
		<td style="text-align: center;"><%=the_book.getName()%></td>
		<%
		publisher=publishers_Dao.findEntityById(the_book.getPublisherId());
		if(publisher!=null){
			publisher_name=publisher.getName();
		}
		 %>
		<td style="text-align: center;"><%=publisher_name%></td>
		<td style="text-align: center;"><%=the_book.getPrice()%></td>
		<td style="text-align: center;"><%=the_book.getVersion()%></td>
		<td style="text-align: center;"><%=the_book.getPublishTime()%></td>
		<td style="text-align: center;">
		<a href="#" onclick="window.open('<%=request.getContextPath()%>/bookQuery/detail.jsp?isbn=<%=the_book.getIsbn()%>','查看图书信息窗口', 'width=500,height=300')">详细信息</a>
		</td>
		</tr>
		<tr><td colspan="8" style="text-align: center;">
		<a href="<%=request.getContextPath()%>/bookQuery/index.jsp">点我返回查询页面</a>
		</td></tr>
		<% 
		}
	else{
		%>
		<tr><td colspan="8" style="text-align: center;">抱歉，没有这本书(ㄒoㄒ)</td></tr>
		<tr><td colspan="8" style="text-align: center;">
		<a href="<%=request.getContextPath()%>/bookQuery/index.jsp">点我返回查询页面</a>
		</td></tr>
		<%
	}
//	}
}
%>

	</table>
</body>
</html>