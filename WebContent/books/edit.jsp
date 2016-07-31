<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page
	import="com.bms.dao.*" 
	import="com.bms.entity.*"
	import="java.util.*"
	import="java.sql.*"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>图书管理信息系统-图书管理</title>
<link rel=stylesheet href="../inc/main.css" type="text/css">
<style type="text/css">
table select {
	width: 200px;
}

.tcolumn {
	background: #DDDDFF;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/function.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	//fromSel: 源选择框， toSel:目标选择框
	function moveitem(fromSel, toSel) {
		var fromOpts = fromSel.options; //源选择项列表数组
		var toOpts = toSel.options; //目标选择项列表数组
		var idx = 0; //当前索引
		var toLen = toOpts.length; // 目标选择框已有的项数
		//遍历所有的源选择项列表数组
		for (var i = fromOpts.length - 1; i >= 0; i--) {
			if (fromOpts[i].selected) { //如果选中
				toOpts[toLen + idx] = new Option(fromOpts[i].text,
						fromOpts[i].value);//添加到目标选择框中
				fromOpts[i] = null; //删除源选择框中的选中项
				idx++;
			}
		}

	}

	//表单提交
	function checkForm(myform) {
		//获取右边选择列表中的选项
		var authorOpts = myform.authorIds.options;
		if (authorOpts != null) {

			for (var i = 0; i < authorOpts.length; i++) {
				//使得当前的选项为选中状态
				authorOpts[i].selected = true;
			}
		}

		if (myform.isbn.value == "") {
			alert('isbn必须输入！');
			myform.isbn.focus();
			return;
		}
		if (myform.name.value == "") {
			alert('书名必须输入！');
			myform.name.focus();
			return;
		}
		if (myform.publisherId.value == "") {
			alert('出版社必须选择！');
			myform.publisherId.focus();
			return;
		}
		//parseFloat：字符串的数据转换成浮点型数据
		//isNaN：is not a number：判断数据是否是数字
		var price = parseFloat(myform.price.value);
		if (isNaN(price)) {
			alert('价格必须为数字！');
			myform.price.focus();
			return;
		}
		//parseInt：字符串的数据转换成整型数据
		var copyright = myform.publishTime.value;
		if (copyright==null) {
			alert('出版年份不能为空!');
			myform.publishTime.focus();
			return;
		}
		var editionNumber = parseInt(myform.version.value);
		if (isNaN(editionNumber)) {
			alert('版本号必须为数字！');
			myform.version.focus();
			return;
		}
		
		myform.submit();
		
		alert("点击刷新！");
		window.close();
		refreshOpener();
	}
	//绑定失去焦点时的事件
	$(function(){
		$("#isbn").blur(function (){
			var isbn_value=$(this).val();
			if(isbn_value!=""){
				//通过ajax提交数据至服务器：
				$.ajax({
					url:"<%=request.getContextPath()%>/BookAction.do",
					data:{isbn:isbn_value,method:"checkIsbn"},
					success:function(data){
						if(data.flag){
							$("#msg").text("当前isbn可用");
						}
						else{
							$("#msg").text("当前isbn不可用");
							$("#isbn").focus();
						}
						
					},
					dataType:"json"
				});
			}
		});
	});
	
</script>
</head>
<body>




<%
//在这里通过index.jsp传过来的ID值，找到数据库中对应作者信息，并返将这些值放到表单中
	
		//这里先找到要修改的那本书，通过EditIsbn主键来找
		String EditIsbn=request.getParameter("EditIsbn");
		//int EditIsbn=Integer.parseInt(mod_id);
		Books modify_book=new Books();
		Books_Dao modify_BooksDao=new Books_Dao();
		modify_book=modify_BooksDao.findEntityById(EditIsbn);//找到要修改的那本书
		
	
		//出版社名字
		Publishers_Dao publishers_Dao=new Publishers_Dao();
		Publishers publisher=new Publishers();
		List<Publishers> publishers=new ArrayList<Publishers>();
%>	
	
	<form action="<%=request.getContextPath()%>/BookAction.do?method=edit" method="post" name="addBook">
		<table>
			<caption>修改图书</caption>
			<%if(modify_book!=null){
				
			 %>
			<tr>
				<td width="250" class="tcolumn">图书ISBN</td>
				<td width="730">
					<input type="text" name="isbn" size="40" id="isbn" value="<%=modify_book.getIsbn() %>" >* 
					<span style="color: red; font-weight: bold" id="msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tcolumn">书名</td>
				<td><input type="text" name="name" size="40"  value="<%=modify_book.getName()%>"> *</td>
			</tr>
			<tr>
				<td class="tcolumn">出版社</td>
				<td>
					<select name="publisherId">
						<option value="">请选择...</option>
						<%		publishers=publishers_Dao.findEntity(publisher);
									if(publisher!=null){
										String flag="";
										for(Publishers pub:publishers){
											if(pub.getId()==modify_book.getPublisherId()){
												flag="selected";
											}
											else{
												flag="";
											}
											//这里是为什么呢？？直接在value后面给到一个"<%=flag %"就行了，而不是常规的selected="selected"来获取到默认值喃？
											%>
											<option value="<%=pub.getId()%>" <%=flag %>	> <%=pub.getName()%></option>
											<%
										}
							}
							 %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tcolumn">价格</td>
				<td><input type="text" name="price" value="<%=modify_book.getPrice() %>"> *</td>
			</tr>
			<tr>
				<td class="tcolumn">出版年份</td>
				<td><input class="Wdate" type="text" name="publishTime"  onClick="WdatePicker()" value="<%=modify_book.getPublishTime() %>"> *</td>
			</tr>
			<tr>
				<td class="tcolumn">版本号</td>
				<td><input type="text" name="version" value="<%=modify_book.getVersion() %>"> *</td>
			</tr>
			<tr>
				<td class="tcolumn">作者</td>
				<td>
					<table>
						<tr>
							<td>
								<!-- 无关的作者信息 -->
								<select name="allauthors" multiple="multiple" size="8">
								<%
								Authors_Dao author_dao=new Authors_Dao();
								//通过这个获得该EditIsbn对应作者的ID，再由作者的ID去找到相应的作者
								ResultSet other_authors=null;
								//无关作者
								other_authors=author_dao.findOthterAuthor(EditIsbn);
								//if(other_authors!=null){
									while(other_authors.next()){

										%>
									<option value="<%=other_authors.getInt("id")%>"><%=other_authors.getString("firstName")%>&nbsp;<%=other_authors.getString("lastName")%></option>	
				
									<% 	
									}
								//}
								%>	
							
								</select>
							</td>
							<td><input type="button" value="选定>>"
								onclick="javascript:moveitem(addBook.allauthors, addBook.authorIds);">
								<br> <input type="button" value="<<删除"
								onclick="javascript:moveitem(addBook.authorIds, addBook.allauthors);">
							</td>
							<td>
								<!-- 出版该图书的所有的作者的信息 -->
								<select name="authorIds" multiple="multiple" size="8">
							<%	
									ResultSet authors=null;
								//有关作者
									authors=author_dao.findAuthor(EditIsbn);
								//if(authors!=null){
									while(authors.next()){

										%>
									<option value="<%=authors.getInt("id")%>"><%=authors.getString("firstName")%>&nbsp;<%=authors.getString("lastName")%></option>	
				
									<% 	
									}
								//}
								%>	
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" class="tcolumn">
					<input type="button" value="增加" onclick="checkForm(addBook);"> 
					<input type="reset" value="清空">
				</td>
			</tr>
			<%} %>
		</table>
	</form>
</body>
</html>