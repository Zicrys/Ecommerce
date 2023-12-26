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

@WebServlet({ "/MyCatalog", "/catalog" })
public class Catalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("Username");
		request.setAttribute("username", username);
		if(request.getParameter("IdGood")==null) {
			//Подготовим данные для отправки в представление
			try {
				request.setAttribute("goods", BaseConnection.getGoods());//goods - это название переменной для jsp файла, то есть для view
				
				//Отправляем коллекцию goods в представление
				request.getRequestDispatcher("views/goods.jsp").forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			}
		else {
			
		}
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
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
			else if(request.getParameter("photo").equals("yes")) { // если нажaта кнопка "добавить изображение"
				int IdGoodPhoto = Integer.parseInt(request.getParameter("id")); // получаем id товара 
					session.setAttribute("IdGoodPhoto", IdGoodPhoto); // передаем его в сессию.
			}
		}
		else {
			response.getWriter().print("Для совершения покупки необходимо авторизоваться!");
		}
		if(request.getParameter("photo").equals("gk")) {
			int IdGoodPhoto = Integer.parseInt(request.getParameter("id"));
			session.setAttribute("IdGoodPhoto", IdGoodPhoto);
		}
	}


}