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



@WebServlet("/GoodCard")
public class GoodCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("views/goodCard.jsp").forward(request,response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        if (BaseConnection.UserExist(request, response)){
			int IdBasket = (int) session.getAttribute("IdBasket");
			if(request.getParameter("photo").equals("no")) { // если была нажата кнопка "добавить в корзину"
				int IdGood = Integer.parseInt(request.getParameter("IdGood"));
				int price = Integer.parseInt(request.getParameter("Price"));
				int count = Integer.parseInt(request.getParameter("count"));
				if(count!=0) {
					try {
						if(BaseConnection.addGoodBasket(IdGood, price, IdBasket)) {
							response.getWriter().print("Товар успешно добавлен в корзину!");
						}
					}
					catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
					}
				}
				else {
					response.getWriter().print("Товара нет в наличии :(");
				}
			} 
	}
        else {
			response.getWriter().print("Для совершения покупки необходимо авторизоваться!");
		}
}
	


}

