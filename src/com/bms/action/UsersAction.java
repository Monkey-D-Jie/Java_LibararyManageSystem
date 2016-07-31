package com.bms.action;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.bms.dao.Users_Dao;
import com.bms.entity.Users;


@WebServlet("/UsersAction.do")
public class UsersAction extends HttpServlet{
	
	//这个有什么用呢？？-----》就是程序的身份证咯
	private static final long serialVersionUID = -4070889140466137506L;
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//在这之前--》先用过滤器解决乱码问题：如果有必要的话---
//		CharSetFilter filter1=new CharSetFilter();//是这样用的不？
		//这个的话是放在xml文件中的，你不用去调用他。当你执行程序的时候它会自动的被用到你要去处理的数据上
		
		
		HttpSession session=request.getSession();
		//生成一个session对象,请求发过来哪儿带来的Session
		
		
		
		//获取到表单提交过来时的hidden标签
		String method1=request.getParameter("addusers");
		String method2=request.getParameter("deleteusers");
		String method3=request.getParameter("modifyusers");
		String method4=request.getParameter("action");
		String method5=request.getParameter("LogOut");
		if("method_add".equals(method1)){
			//得到表单中的有效数据
			String userName=request.getParameter("userName");
			String realName=request.getParameter("realName");
			//密码是属于安全性的数据，所以这里要进行一个加密后，才插入到数据库中
			String password=DigestUtils.md5Hex(request.getParameter("password"));
			
			//传入的参数不要搞混咯哦！
			Users user=new Users(userName,password,realName);
			Users_Dao userDao=new Users_Dao();
			
			//执行插入的操作
			try {
				userDao.saveEntity(user);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}else if("method_del".equals(method2)){
			//这里要执行删除操作的逻辑
			/**
			 * 目标：delete from users where id=?
			 * 关键：获取到index端的ID
			 * 获取方法：
			 * 在index.jsp中通过超链接的链接参数
			 * ?deleteusers='method_del'&ID='<%=users.getId()%>'
			 * 传到这个类中的
			 */
			try {
				String str=request.getParameter("ID");
				if(str!=null){
					int id=Integer.parseInt(str);
					Users user=new Users();
					user.setId(id);
					Users_Dao del_UsersDao=new Users_Dao();
					//执行数据库的删除操作
					
						int isDel=del_UsersDao.deleteById(user);
						//---------》新问题：这里删除成功了，怎样实现当前页面Index.jsp的实时更新呢
						//-----》先采用重新回到本页面的方法，相当于再加载了
						if(isDel!=0){
							//采用的是从新再跳到index.jsp页面
							response.sendRedirect(request.getContextPath()+"/users/index.jsp");
						}
				}
				else{
					System.out.println("Servelet端没有获取到ID值");
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
			
		}else if("method_mod".equals(method3)){
			//修改操作的逻辑
			/**
			 * 目标：修改当前用户信息
			 * 实现：通过id来修改对应用户的信息
			 * 关键：数据回显和修改后的重传
			 * 
			 * 回显----》
			 * 可以通过ID值查找到数据库中对应用户的信息，然后显示到弹出的窗口中。
			 * 怎样给显示到窗口中呢？---->可以在modify.jsp中获取到修改用的id，
			 * 再在界面中通过BaseDao的方法找到对应的用户，将这些信息赋值到对应的修改框中。
			 * 这里要注意，密码是加密的，所以需要判断密码的值是重新输入的还是原来的。原来的就可以不做处理
			 * 若是新改的，则要在这个方法中将传过来的密码加密后才放到数据库中
			 * ----》这里坑存在的问题：
			 * 将数据库中的值赋值到更改页面，那么我在修改页面修改好信息后，我修改的表单数据是否能传到这里呢？
			 * 造成该问题可能的原因：我把数据库中根据相应ID找到的值给了表单数据的value。
			 * 希望--->传过来的是我打开后更新的表单数据
			 * 
			 * 
			 * 重传----》
			 * 在modify.jsp中，存放修改信息的是一张表单，可以采用保存用户时的方法
			 * 将表单提交动作指定的对象为这个servelet，然后在这里执行同保存用户时一样的逻辑
			 */
			//执行重传的逻辑
			//得到表单中的有效数据
	
			String modify_userName=request.getParameter("userName");
			String modify_realName=request.getParameter("realName");
			String changed_pwd=request.getParameter("pwdIsModify");
			String password=request.getParameter("password");
			String modify_password=null;
			//这里要判断密码有没有被修改------>怎样获取到页面的值是默认值还是修改了的值呢？
			//这个已经在modify.jsp页面处理了
			
			String ID=request.getParameter("Modify_ID");//获取到要修改用户的ID
			int userID=Integer.parseInt(ID);
			if("yes".equals(changed_pwd)){
				//如果密码修改了，那就将修改的密码加密后放回到sql中
				modify_password=DigestUtils.md5Hex(password);
			}
			else if("no".equals(changed_pwd)){
				//如果没改，那就原封不动的插入到sql中就是了
				modify_password=password;
			}
				System.out.println("成功的跳到修改的执行代码处了！");
				//传入的参数不要搞混咯哦！
				Users user=new Users(userID,modify_userName,modify_password,modify_realName);
				Users_Dao userDao=new Users_Dao();
				
				//执行插入的操作
				try {	
					userDao.updateEntity(user);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			
			
		}else if("login".equals(method4)){
			//这里是考虑到登录判断的执行逻辑
			//首先要获取到那边登录界面传过来的值
			String username=request.getParameter("log_userName");
			String password=request.getParameter("log_password");
			if(username!=null&&password!=null){
				Users_Dao login_user=new Users_Dao();
				System.out.println(username);
				ResultSet result=null;
				try {
						result = login_user.IsLogin(username, password);
						if(result==null){
							System.out.println("result是空的");
						}
						if(result.next()){
									response.sendRedirect(request.getContextPath()+"/users/index.jsp?"+username); 
									System.out.println("执行到用户验证的逻辑了");
									session.setAttribute("Login_User", username);
						}
						else{
							System.out.println("执行到用户验证失败的逻辑了");
								response.sendRedirect(request.getContextPath()+"/login.jsp"); 
								session.setAttribute("Login_User", "failed");
						}
//					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}else if("logout".equals(method5)){
			//这里执行的是退出的逻辑
			response.sendRedirect(request.getContextPath()+"/login.jsp?"); 
			session.removeAttribute("Login_User");
			session.invalidate();
		}
	}

}
