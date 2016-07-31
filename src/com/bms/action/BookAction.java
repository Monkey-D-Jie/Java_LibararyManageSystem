package com.bms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.dao.Author_bookDao;
import com.bms.dao.Books_Dao;
import com.bms.entity.Author_book;
import com.bms.entity.Books;
import com.google.gson.Gson;

@WebServlet("/BookAction.do")
public class BookAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String Map = null;
	private Number book_price;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*//设置响应的格式
		response.setContentType("application/json;charset=utf-8");
		//流进行输出
		PrintWriter writer = response.getWriter();
		
		Map<String, Object> data=new HashMap<String,Object>();
		data.put("msg","不用");
		
		Gson gson=new Gson();
		String s = gson.toJson(data);
		
		writer.write(s);
		writer.flush();*/
		String action=request.getParameter("method");
		//执行删除的逻辑
		if("delete".equals(action)){
			//如果是删除操作
			
			//获取到isbn
			String book_isbn=request.getParameter("delIsbn");
			
			//先删除author_book中相关的信息
			Author_bookDao del_connection=new Author_bookDao();
			Author_book del_ab=new Author_book();
			del_ab.setIsbn(book_isbn);
			
			Books_Dao del_Dao=new Books_Dao();
			Books del_book=new Books();
			del_book.setIsbn(book_isbn);
			
			if(book_isbn!=null){
				del_ab.setIsbn(book_isbn);
				try {
					System.out.println("isbn有效，图书删除操作已执行！");
					del_connection.deleteById(del_ab);
					del_Dao.deleteById(del_book);
					//还要把书删除了
					
					//删除成功后跳转至index的界面
					response.sendRedirect(request.getContextPath()+"/books/index.jsp");
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				System.out.println("isbn的值未能传入图书删除操作中，其值为"+book_isbn);
			}
		}else if("add".equals(action)){
			//执行添加图书操作
			System.out.println("开始执行添加图书操作-----》");
			//首先要获得从add.jsp页面传递过来的基本信信息
			String book_isbn=request.getParameter("isbn");
			String book_name=request.getParameter("name");
			
			int book_publisherId=Integer.parseInt(request.getParameter("publisherId"));
			
			float book_price=Float.parseFloat(request.getParameter("price"));
			
			SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
			Date book_publishTime=null;
			try {
				book_publishTime = new Date(date.parse(request.getParameter("publishTime")).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
					

			
			String temp_version=request.getParameter("version");//int型的
			int book_version=Integer.parseInt(temp_version);
			
			
			String[] temp_authorIds=request.getParameterValues("authorIds");//当数组来处理---->先用String来测试一下它传递过来的值
			int[] book_authorIds=new int[temp_authorIds.length];
			if(temp_authorIds!=null){
				for(int i=0;i<temp_authorIds.length;i++){
					book_authorIds[i]=Integer.parseInt(temp_authorIds[i]);
				}
			}
			
			
			Books addBook=new Books(book_isbn, book_name, book_version, book_price, book_publishTime, book_publisherId, book_authorIds);
			Books_Dao add_Dao=new Books_Dao();
			
			
			try {
				add_Dao.insert_Book(addBook);
			} catch (Exception e) {
				System.out.println("执行添加图书时出错！");
				e.printStackTrace();
				
			}
			response.sendRedirect(request.getContextPath()+"/books/index.jsp");
			
		}else if("edit".equals(action)){
			//执行修改图书操作
			
			//首先删除isdn对应的图书
			String book_isbn=request.getParameter("isbn");	
			//先删除author_book中相关的信息
			Author_bookDao del_connection=new Author_bookDao();
			Author_book del_ab=new Author_book();
			del_ab.setIsbn(book_isbn);
			
			Books_Dao del_Dao=new Books_Dao();
			Books del_book=new Books();
			del_book.setIsbn(book_isbn);
			
			if(book_isbn!=null){
				del_ab.setIsbn(book_isbn);
				try {
					System.out.println("isbn有效，修改中的图书删除操作已执行！");
					del_connection.deleteById(del_ab);
					//还要把书删除了
					del_Dao.deleteById(del_book);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				//然后把新的信息填加到表中，这里用的是add的方法
				String book_name=request.getParameter("name");
				int book_publisherId=Integer.parseInt(request.getParameter("publisherId"));
				float book_price=Float.parseFloat(request.getParameter("price"));
				SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
				Date book_publishTime=null;
				try {
					book_publishTime = new Date(date.parse(request.getParameter("publishTime")).getTime());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String temp_version=request.getParameter("version");//int型的
				int book_version=Integer.parseInt(temp_version);
				String[] temp_authorIds=request.getParameterValues("authorIds");//当数组来处理---->先用String来测试一下它传递过来的值
				int[] book_authorIds=new int[temp_authorIds.length];
				if(temp_authorIds!=null){
					for(int i=0;i<temp_authorIds.length;i++){
						book_authorIds[i]=Integer.parseInt(temp_authorIds[i]);
					}
				}
				Books modBook=new Books(book_isbn, book_name, book_version, book_price, book_publishTime, book_publisherId, book_authorIds);
				Books_Dao mod_Dao=new Books_Dao();
				try {
					mod_Dao.insert_Book(modBook);
				
				} catch (Exception e) {
					System.out.println("执行添加图书时出错！");
					e.printStackTrace();
					
				}
				
			
			}
			response.sendRedirect(request.getContextPath()+"/books/index.jsp");
		}else if("checkIsbn".equals(action)){
			//检测isbn的合法性
			
			boolean flag=true;
			//获得isbn
			String isbn=request.getParameter("isbn");
			
			//看数据库中有没有该isbn
			Books_Dao book_dao=new Books_Dao();
			Books reshult_book;
			try {
				reshult_book = book_dao.findEntityById(isbn);
				if(reshult_book!=null){
					//说明是有该isbn的
					flag=false;
				}
				
				Map<String,Boolean> result=new HashMap<String,Boolean>();
				result.put("flag", flag);
				
				Gson gson=new Gson();
				String temp=gson.toJson(result);
				
				response.setContentType("application/json;charset=utf-8");
				PrintWriter print=response.getWriter();
				print.write(temp);
				print.flush();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

}
