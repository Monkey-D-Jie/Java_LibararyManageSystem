<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page
import="com.bms.entity.*"
import="com.bms.dao.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户修改</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/function.js"></script>
<script type="text/javascript">
	//检测表单中的数据
	function check(form){
		//为空判断
		if(form.userName.value==""){
			alert("登录名称不能为空！");
			form.userName.focus();
			return;
		}	
		if(form.realName.value==""){
			alert("真实姓名不能为空！");
			form.realName.focus();
			return;
		}
		if(form.password.value==""){
			alert("密码不能为空！");
			form.password.focus();
			return;
		}
		if(form.pwd.value==""){
			alert("确认密码不能为空！");
			form.pwd.focus();
			return;
		}
		//两次密码的值是否一致
		if(form.password.value!=form.pwd.value){
			alert("两次输入密码不相同！");
			form.pwd.focus();
			return;
		}
		
		//判断密码是否修改了
		var changed_pwd=form.password.value;
		var pwd2=form.pwd.defaultValue;
		if(changed_pwd!=pwd2){
			//如果结果为真，说明当前值与默认值不等，说明密码是被修改了的，此时就需要传未加密的密码到usersaction
			form.password.value=changed_pwd;
			form.pwdIsModify.value="yes";
		}
		//提交表单
		form.submit();
		//------>这里除了个问题诶，就是，死活都蹦不出这个刷新的框框出来，-----》问题 - -!
		//是因为你的jsp页面出错了！
		/* //重新刷新打开当前窗口的页面
		opener.location.reload();
		window.close(); */
		//保证数据在页面刷新之间已经保存到数据库中
		alert("点击刷新！");
		refreshOpener();
	}
</script>
</head>
<body>
<%
out.print(request.getParameter("modifyusers"));
//在这里通过index.jsp传过来的ID值，找到数据库中对应用户信息，并返将这些值放到表单中
		String mod_id=request.getParameter("ModId");
		int Modify_ID=Integer.parseInt(mod_id);
		Users modify_user=new Users();
		Users_Dao modify_UsersDao=new Users_Dao();
		modify_user=modify_UsersDao.findEntityById(Modify_ID);
%>
	<form action="<%=request.getContextPath()%>/UsersAction.do?Modify_ID=<%=mod_id %>" name="addAuthors" method="post">
		<table>
		<input type="hidden" name="modifyusers" value="method_mod">
		<input type="hidden" name="pwdIsModify" value="no">
			<caption>修改用户</caption>
			<tr>
				<td>
					登录账户:
				</td>
				<td>
					<input readonly="readonly" type="text" name="userName" value="<%=modify_user.getUsername() %>" >
				</td>
			</tr>
			<tr>
				<td>
					真实姓名:
				</td>
				<td>
					<input type="text" name="realName" value="<%=modify_user.getRealname()%>">
				</td>
			</tr>
			<tr>
				<td>
					登录密码:
				</td>
				<td>
					<input type="password" name="password" value="<%=modify_user.getPassword() %>">
				</td>
			</tr>
			<tr>
				<td>
					确认密码:
				</td>
				<td>
					<input type="password" name="pwd" value="<%=modify_user.getPassword() %>">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="保存" onclick="check(addAuthors);">
					<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>