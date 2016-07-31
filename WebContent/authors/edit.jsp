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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/function.js"></script>
<script type="text/javascript">
	function checkForm(myform) {
		var fName = myform.firstName.value;
		var lName = myform.lastName.value;
		if (fName == "") {
			alert('名不能为空！');
			myform.firstName.focus();
			return;
		}
		if (lName == "") {
			alert('姓不能为空！');
			myform.lastName.focus();
			return;
		}
		//提交表单
		myform.submit();
		
		alert("点击刷新！");
		refreshOpener();
	}
</script>
</head>
<body>
<%
//在这里通过index.jsp传过来的ID值，找到数据库中对应作者信息，并返将这些值放到表单中
		String mod_id=request.getParameter("authorID");
		if(mod_id==null){
			out.print("EditId的值没有成功的传递过来！");
		}
		int Edit_ID=Integer.parseInt(mod_id);
		Authors modify_author=new Authors();
		Authors_Dao modify_AuthorsDao=new Authors_Dao();
		modify_author=modify_AuthorsDao.findEntityById(Edit_ID);
%>	
	<form action="<%=request.getContextPath()%>/AuthorsAction.do?method=edit&editID=<%=Edit_ID %>" name="addAuthor" method="post">
		<table>
			<caption>修改作者</caption>
			<tr>
				<td>
					作者姓：
				</td>
				<td>
					<input type="text" name="firstName" value="<%=modify_author.getFirstName()%>">
				</td>
			</tr>
			<tr>
				<td>
					作者名：
				</td>
				<td>
					<input type="text" name="lastName" value="<%=modify_author.getLastName() %>">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="保存" onclick="checkForm(addAuthor);">
					<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>