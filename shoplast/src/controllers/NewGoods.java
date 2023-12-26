package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.BaseConnection;

@WebServlet("/NewGoods")
public class NewGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static String types = "";
	static String mark = "";
	static String model = "";
	static int price = 0;
	static int count = 0;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        if(BaseConnection.UserExist(request, response)) {
         	request.getRequestDispatcher("views/newGood.jsp").forward(request,response);
        }
        else {
        	 request.getRequestDispatcher("views/log.jsp").forward(request,response);
        }
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8"); 
				
        String type = request.getParameter("types");
        String mark = request.getParameter("mark");
        String model = request.getParameter("model");
        int price = 0;
        int count = 0;
        try {
        price = Integer.parseInt(request.getParameter("price"));
        count = Integer.parseInt(request.getParameter("count"));
		String goodAnswer = "Товар успешно добавлен!";
		try {
			if(type.equals("") || mark.equals("") || model.equals("") || count==0) { // проверка на пустые поля
				response.getWriter().print("Пожалуйста, заполните все поля!");
			}
			else {
				response.getWriter().print(goodAnswer);
	        	BaseConnection.AddNewGood(type, mark, model, price, count); // добавляем товар
			}
		}
		catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    catch (NumberFormatException e) { // проверка на некорректный ввод цифр
    	response.getWriter().print("Введите цену и количество цифрами.");
	}
		
	}
	public static String getTypes() {
		return types;
	}
	public static String getMark() {
		return mark;
	}
	public static String getModel() {
		return model;
	}
	public static int getPrice() {
		return price;
	}
	public static int getCount() {
		return count;
	}
	
}