<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>图书管理信息系统-登录</title>
<style type="text/css">
table {
	font-size: 12px;
}

table caption {
	font-size: 14px;
}
</style>
<script type="text/javascript">
	function checkForm(myForm) {
		var msg = "";
		var setFocused = false;
		if (myForm.log_userName.value == "") {
			msg += "帐号不能为空！\n";
			myForm.log_userName.focus();
			setFocused = true;
		}

		if (myForm.log_password.value == "") {
			msg += "密码不能为空！\n";
			if (!setFocused) {
				myForm.log_password.focus();
				setFocused = true;
			}
		}

		if (msg != "") {
			alert(msg);
			return false;
		}

		return true;

	}
</script>
</head>
<body>

	<table align="center">
		<tr>
			<td><img src="<%=request.getContextPath()%>/images/head.jpg">
			</td>
		</tr>
	</table>
<%String isLogin=(String)session.getAttribute("Login_User"); 
String str="登录名或密码错误";
%>	
	<form action="<%=request.getContextPath()%>/UsersAction.do?action=login" name="myForm" method="post"
		onsubmit="return checkForm(this);">
		<table align="center">
			<caption>用户登录</caption>
			<tr>
			<td colspan="2" align="center">
			<%
			if("failed".equals(isLogin)){
				//这里别把最基本的给忘了啊！tr是行，里面还得有列才能把信息显示到你想显示的地方
			%>
			<font color="red"><%=str%></font>
			<%
			}
			%>
			</td>	
				</tr>
			<tr>
				<td>用户帐号</td>
				<td><input type="text" style="width: 150px" name="log_userName">
				</td>
			</tr>
			<tr>
				<td>用户密码</td>
				<td><input type="password" style="width: 150px" name="log_password">
					
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="登录">
					<input type="reset" value="清空"></td>
			</tr>
		</table>
		<center>
		<a href="resign.jsp" style="">没有账号？注册一个</a>
		</center>
	</form>
	
</body>
</html>