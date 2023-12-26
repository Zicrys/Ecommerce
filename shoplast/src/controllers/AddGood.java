package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import models.BaseConnection;

@WebServlet("/AddGood")
public class AddGood extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static int IdType = 0;
	static int IdMark = 0;
	static int IdModel = 0;
	static int Price = 0;
	static int Count = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        if(BaseConnection.UserExist(request, response)) { // проверка на авторизацию

        	 try {
				request.setAttribute("types", BaseConnection.getTypes()); // готовим данные для передачи в список
				request.setAttribute("marks", BaseConnection.getMarks());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

         	request.getRequestDispatcher("views/addgood.jsp").forward(request,response);
         	if(request.getParameter("chek")!=null && request.getParameter("chek").equals("send")){ // проверяем, нажата ли кнопка отправки формы
          	 int idtype = Integer.parseInt(request.getParameter("idtype"));
          	 int idmark = Integer.parseInt(request.getParameter("idmark"));
          	 int idmodel = Integer.parseInt(request.getParameter("idmodel"));
          	 try {
          	 int price = Integer.parseInt(request.getParameter("price"));
          	 int count = Integer.parseInt(request.getParameter("count"));
          	 try {
				BaseConnection.findGoodByTitle(idtype, idmark,  idmodel, price ,count);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
         	}
         	catch(NumberFormatException e) {
         	}

        }
        
        }
        else {
       	 request.getRequestDispatcher("views/log.jsp").forward(request,response);
        }
        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        if(request.getParameter("chek")==null) {
        int idmark = Integer.parseInt(request.getParameter("idmark"));
        try {
			response.getWriter().print("<p>Выберите модель</p><select id ='idmodel'>"+BaseConnection.getModel(idmark)+"</select>"); // динамически создаем выпадающий список с моделями, которые зависят от выбраной марки
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
  
        }
     }
}
