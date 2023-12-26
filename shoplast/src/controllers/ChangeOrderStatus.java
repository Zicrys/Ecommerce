package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.BaseConnection;


@WebServlet("/ChangeOrderStatus")
public class ChangeOrderStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
		if (BaseConnection.UserExist(request, response)){
			try {
				request.setAttribute("infoOrder", BaseConnection.getNewOrders()); // информация о всех заказах
				
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			request.getRequestDispatcher("views/changeOrderStatus.jsp").forward(request,response);
		}
		else {
			request.getRequestDispatcher("views/log.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        int IdOrder = Integer.parseInt(request.getParameter("idOrder"));
        Date date = new Date();
		SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
		String finDate = formatForDateNow.format(date); //получам текущую дату (дата завершения сборки заказа)
        try {
			BaseConnection.changeOrderStatus(IdOrder, finDate); // меняем статус заказа с "в работе" на "отправлен"
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
