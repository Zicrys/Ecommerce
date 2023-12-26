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

@WebServlet("/OrdersItemsStatus")
public class OrdersItemsStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OrdersItemsStatus() {
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
		if (BaseConnection.UserExist(request, response)){
			HttpSession session = request.getSession();
	        String IdOrder = Integer.toString((int) session.getAttribute("sessionIdOrder"));
	        try {
				request.setAttribute("OrderGoods", BaseConnection.getOrderItems(IdOrder)); // получаем товары в заказе по id заказа
				request.setAttribute("sum", BaseConnection.getSum(IdOrder)); // получаем список товаров в заказе по idOrder
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
	        
			request.getRequestDispatcher("views/orderit.jsp").forward(request,response);
		}
		else {
			request.getRequestDispatcher("views/log.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
	}

}
