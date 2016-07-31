<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%
String LoginUser=(String)session.getAttribute("Login_User");
%>
<table align="center" width="980" id="headtb"
	style="background: #6688AA">
	<tr>
		<td colspan="2"><img src="<%=request.getContextPath() %>/images/head.jpg"></td>
	</tr>
	<tr style="background: url(<%=request.getContextPath() %>/images/headbg.jpg) repeat-x;">

		<td>欢迎  &nbsp; &nbsp;<font color="red" size="+2"><% if(LoginUser!=null&&!"failed".equals(LoginUser)){out.print(LoginUser);} %></font> &nbsp;  &nbsp;      &nbsp;&nbsp;  [ 
	
		<a href="<%=request.getContextPath() %>/UsersAction.do?LogOut=logout">退出系统</a>]
		
		</td>
		<td align="right" height="25">
		<a href="<%=request.getContextPath()%>/authors/index.jsp">作者管理</a> 
		| <a href="<%=request.getContextPath()%>/publishers/index.jsp">出版社管理</a>
			| 
		<a href="<%=request.getContextPath()%>/books/index.jsp">图书管理</a> | 
		<a href="<%=request.getContextPath()%>/bookQuery/index.jsp">图书查询</a> | 
			<a href="<%=request.getContextPath()%>/users/index.jsp">用户管理</a> 
		|</td>
	</tr>
</table>
