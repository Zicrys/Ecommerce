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

@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static String Username = "";
	static String Password = "";
	static String fio = "";
	static String phone = "";
	static int IdBasket = 0;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("views/reg.jsp").forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8"); 
				
		fio = request.getParameter("fio");
		phone = request.getParameter("phone");
		Username = request.getParameter("Username");
		Password = request.getParameter("Password");
		int IdUser = 0;
		String goodAnswer = "Вы успешно зарегистрированы!";
		try {
			if(fio.equals("") || phone.equals("") || Username.equals("") || Password.equals("")) {
				response.getWriter().print("Пожалуйста, заполните все поля!");
			}
			else {
				if (BaseConnection.findIdUser(phone)==-1) {//если нет клиента с таким телефоном и логином, то создаем его. По фио не проверяем, т.к возможны полные тезки
					if(BaseConnection.findbyUsername(Username)!=-1) {
					response.getWriter().print("\nДанный логин уже занят!");
					}
					else if(BaseConnection.addUser(Username, Password, fio, phone)) { // создаем юзера

					HttpSession session = request.getSession();
			        session.setAttribute("Username", Username);
			        session.setAttribute("Password", Password); //устанавливаем пароль и логин на текущую сессию.
			        IdUser = BaseConnection.findbyUsername(Username); // получаем id юзера
			        session.setAttribute("IdUser", IdUser); // устанавливаем id юзера на сессию. 
			        if(BaseConnection.getOpenBasketId(IdUser)==0) { // если на юзега нет корзины, то открываем новую, если есть, то используем ее id.
			        	try {
							IdBasket = BaseConnection.findMaxId("IdBasket", "informuserbasket")+1;

						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
			        	BaseConnection.CreateBaskets(IdUser, IdBasket);
			        }
			        else if(BaseConnection.getOpenBasketId(IdUser)!=0){
			        	IdBasket = BaseConnection.getOpenBasketId(IdUser);
			        }
			        session.setAttribute("IdBasket", IdBasket);
					response.getWriter().print(goodAnswer);
					}
				}
				else {
					response.getWriter().print("\nДанный номер уже зарегистрирован!");
				}
				}
			} 
		catch (ClassNotFoundException e) {

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