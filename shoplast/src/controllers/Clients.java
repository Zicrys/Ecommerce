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

@WebServlet("/Clients")
public class Clients extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (BaseConnection.UserExist(request, response)){
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8"); 
		try {
			request.setAttribute("clients", BaseConnection.getUsers()); // Готовим список всех клиентов
			request.getRequestDispatcher("views/clients.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		if(request.getParameter("IdUser")!=null) {
		    int IdUser = Integer.parseInt(request.getParameter("IdUser"));
		    HttpSession session = request.getSession();
	        session.setAttribute("sessionIdUser", IdUser); // получаем id юзера, чтобы потом по нему посмотреть заказы
		}
		
		
	}
}
