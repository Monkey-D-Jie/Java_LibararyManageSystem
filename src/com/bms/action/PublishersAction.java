package com.bms.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.dao.Publishers_Dao;
import com.bms.entity.Publishers;

@WebServlet("/PublishersAction.do")
public class PublishersAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8511964717914543409L;
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//添加作者
		/*
		 * 过程：获得addpublishers的数据----》添加有效数到数据库
		 */
		String action=request.getParameter("method");
		if("add".equals(action)){
			
			String	publisher_name=request.getParameter("name");
			Publishers publisher=new Publishers(publisher_name);
			Publishers_Dao  publisherDao=new Publishers_Dao();
			try {
				publisherDao.saveEntity(publisher);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}else if("edit".equals(action)){
			//修改作者
			int Edit_publisherID=Integer.parseInt(request.getParameter("editID"));
			String Edit_publisher=request.getParameter("name");
			Publishers publisher=new Publishers(Edit_publisher);
			publisher.setId(Edit_publisherID);
			Publishers_Dao  authorsDao=new Publishers_Dao();
			try {
				authorsDao.updateEntity(publisher);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}else if("delete".equals(action)){
			//删除作者
			int Del_publisherID=Integer.parseInt(request.getParameter("delID"));
			Publishers publisher=new Publishers();
			publisher.setId(Del_publisherID);
			Publishers_Dao  publishersDao=new Publishers_Dao();
			
			try {
				publishersDao.deleteById(publisher);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath()+"/publishers/index.jsp");
		}
	}
	
}
