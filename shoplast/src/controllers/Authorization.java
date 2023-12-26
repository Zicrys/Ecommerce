package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.BaseConnection;

@WebServlet("/Authorization")
public class Authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static String Username = "";
	static String Password = "";
	static String fio = "";
	static String phone = "";
	static String acc = "";
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("views/log.jsp").forward(request,response);
        if(BaseConnection.UserExist(request, response)){
        }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8"); 
				
		Username = request.getParameter("Username");
		Password = request.getParameter("Password");
		int IdBasket = 0;
		int IdUser = 0;


		String access = "   ";
		String noUser = "Неверный логин или пароль!";
		try {
			if (BaseConnection.FindUser(Username, Password)==-1) {//если нет клиента, то создаем
				response.getWriter().print(noUser);				
			}
			else{
				HttpSession session = request.getSession();
		        session.setAttribute("Username", Username);
		        session.setAttribute("Password", Password);
				IdUser = BaseConnection.findbyUsername(Username);
				session.setAttribute("IdUser", IdUser);
				
		        if(BaseConnection.getOpenBasketId(IdUser)==0) { // если у пользователя нет открытых корзин, то создаем новую.
		        	try {
						IdBasket = BaseConnection.findMaxId("IdBasket", "informuserbasket")+1;

					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		        	BaseConnection.CreateBaskets(IdUser, IdBasket);
		        }
		        else if(BaseConnection.getOpenBasketId(IdUser)!=0){ // если есть, то получаем ее id и используем id старой корзины
		        	IdBasket = BaseConnection.getOpenBasketId(IdUser);
		        }
		        session.setAttribute("IdBasket", IdBasket);
				response.getWriter().print(access);
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
	public static String getUsername() {
		return Username;
	}
	public static String getPassword() {
		return Password;
	}
	public static String getFIO() {
		return fio;
	}
	public static String getPhone() {
		return phone;
	}
}
