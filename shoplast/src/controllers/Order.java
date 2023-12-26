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


@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
		if (BaseConnection.UserExist(request, response)){
			HttpSession session = request.getSession();
	        int IdUser = (int) session.getAttribute("IdUser");

			try {
				request.setAttribute("infoOrders", BaseConnection.getInfoOrders(IdUser));//общая информация о заказе
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			request.getRequestDispatcher("views/allorders.jsp").forward(request,response);
		}
		else {
			request.getRequestDispatcher("views/log.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
		String IdOrder = (String) request.getParameter("idOrder");
        HttpSession session = request.getSession();
        session.setAttribute("IdOrder", IdOrder);

	}

}
