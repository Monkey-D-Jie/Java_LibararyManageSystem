<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加用户</title>
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
		//提交表单
		form.submit();
		
		/* //重新刷新打开当前窗口的页面
		opener.location.reload();
		window.close(); */
		//保证数据在页面刷新之间已经保存到数据库中
		alert("点击刷新！");
		//refreshOpener();
		//不把这一句给注释掉，就不能成功的吧数据插入到数据库中。
		//注释了，添加窗口中就会在你点了更新后再多点一次，点了更新后是插入了一条，后续在出来一个选择，确定的话就会
		//再插入一条相同的进去------》怎么解决呢？是不兼容吗？
	}
</script>
</head>
<body>
<%System.out.println(request.getContextPath()); %>	
	<form action="<%=request.getContextPath()%>/UsersAction.do" name="addAuthors" method="post">
		<table>
			<input type="hidden" name="addusers" value="method_add">
			<caption>添加用户</caption>
			<tr>
				<td>
					登录账户:
				</td>
				<td>
					<input type="text" name="userName">
				</td>
			</tr>
			<tr>
				<td>
					真实姓名:
				</td>
				<td>
					<input type="text" name="realName">
				</td>
			</tr>
			<tr>
				<td>
					登录密码:
				</td>
				<td>
					<input type="password" name="password">
				</td>
			</tr>
			<tr>
				<td>
					确认密码:
				</td>
				<td>
					<input type="password" name="pwd">
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