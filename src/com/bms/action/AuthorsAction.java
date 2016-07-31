package com.bms.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.dao.Authors_Dao;
import com.bms.entity.Authors;

@WebServlet("/AuthorsAction.do")
public class AuthorsAction extends HttpServlet {

	/**
	 * 下面这个是它的身份证
	 */
	private static final long serialVersionUID = 1L;
	
	
	//重写service方法，他能兼顾到post和get
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//添加作者
		/*
		 * 过程：获得adduser的数据----》添加有效数到数据库
		 */
		String action=request.getParameter("method");
		if("add".equals(action)){
			//获取到添加作者的信息，主键为ID，且自增
			String firstName=request.getParameter("firstName");
			String	lastName=request.getParameter("lastName");
			Authors author=new Authors(firstName, lastName);
			
			Authors_Dao  authorsDao=new Authors_Dao();
			try {
				authorsDao.saveEntity(author);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}else if("edit".equals(action)){
			//修改作者
			int Edit_autorID=Integer.parseInt(request.getParameter("editID"));
			String firstName=request.getParameter("firstName");
			String	lastName=request.getParameter("lastName");
			Authors author=new Authors(firstName, lastName);
			author.setId(Edit_autorID);
			Authors_Dao  authorsDao=new Authors_Dao();
			try {
				authorsDao.updateEntity(author);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else if("delete".equals(action)){
			//删除作者
			int Del_autorID=Integer.parseInt(request.getParameter("delID"));
			Authors author=new Authors();
			author.setId(Del_autorID);
			Authors_Dao  authorsDao=new Authors_Dao();
			
			try {
				authorsDao.deleteById(author);
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath()+"/authors/index.jsp");
			
		}
		
	}

}
