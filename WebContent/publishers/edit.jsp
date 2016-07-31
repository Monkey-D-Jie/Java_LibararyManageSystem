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
		var pName = myform.name.value;
		if (pName == "") {
			alert('出版社名称不能为空！');
			myform.name.focus();
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
//在这里通过index.jsp传过来的ID值，找到数据库中对应出版社信息，并返将这些值放到表单中
		String mod_id=request.getParameter("EditId");
		int Edit_ID=Integer.parseInt(mod_id);
		Publishers modify_publishers=new Publishers();
		Publishers_Dao modify_PublishersDao=new Publishers_Dao();
		modify_publishers=modify_PublishersDao.findEntityById(Edit_ID);
%>	
	<form action="<%=request.getContextPath()%>/PublishersAction.do?method=edit&editID=<%=Edit_ID%>"" method="post" name="addPublisher">
		<table>
			<tr>
				<td>
					出版社名称 :
				</td>
				<td>
					<input type="text" name="name" size="20"  value="<%=modify_publishers.getName()%>">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="保存" onclick="checkForm(addPublisher);">
					<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>