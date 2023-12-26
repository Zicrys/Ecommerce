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
import javax.servlet.http.HttpSession;

import models.BaseConnection;

@WebServlet("/Basket")
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException{
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
		if (BaseConnection.UserExist(request, response)){
			try {
				HttpSession session = request.getSession();
		        int SessionIdBasket = (int) session.getAttribute("IdBasket");
				request.setAttribute("basket", BaseConnection.getDataFromBasket(SessionIdBasket));
				request.setAttribute("sum", BaseConnection.findSum(SessionIdBasket));
				request.setAttribute("idbasket", SessionIdBasket);
			} 
			catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getRequestDispatcher("views/basket.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("views/log.jsp").forward(request, response);
		}
}





	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
        		int IdBasket = Integer.parseInt(request.getParameter("IdBasket"));
        		String check = request.getParameter("check");
        		switch (check) {
                	case  ("dfb"): // если нажали кнопку "удалить"
    	        		try {
    	            		int id_good = Integer.parseInt(request.getParameter("goodCount"));
    	            		BaseConnection.InsertDeletedFromBasket(id_good, IdBasket);
    	        			BaseConnection.deleteFromBasket(id_good, IdBasket);
    	        		} 
    	        		catch (ClassNotFoundException e) {
    	        			// TODO Auto-generated catch block
    	        			e.printStackTrace();
    	        		}
    	        		catch (SQLException e) {
    	        			// TODO Auto-generated catch block
    	        			e.printStackTrace();
    	        		}
                    break;
                	case ("so"): // Если нажали кнопку "оформить заказ"
                		HttpSession session = request.getSession();
        	        	int IdUser = (int) session.getAttribute("IdUser");
        	        	int sum = Integer.parseInt(request.getParameter("sum"));
        				Date date = new Date(); // получаем текущую дату
        				SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd"); // приводим ее к удобому формату
        				String StartDate = formatForDateNow.format(date);
        				String FinishDate = null;

                		try {
            				if(BaseConnection.checkBasketEmpty(IdBasket)==true) { // Если корзина не пуста
            						BaseConnection.changeItemStatus(1, IdBasket); // изменяем статус товаров в корзине
            						BaseConnection.addToOrders(IdBasket); // добавляем товары в постоянную корзину
		             				BaseConnection.createOrder(IdUser, IdBasket, StartDate, FinishDate, sum); // Создаем заказ
		             				BaseConnection.changeBasketStatus(1, IdBasket); // Закрываем корзину 
		    						session.removeAttribute("IdBasket"); // убиваем старую ненужную сессию с информацией об id уже закрытой корзины.
		    						BaseConnection.clearBasket(IdBasket); // чистим временную корзину от товаров из текущего заказа
		    						if(BaseConnection.getOpenBasketId(IdUser)==0) { // снова проверка на наличие незакрытой корзины
			            				IdBasket = BaseConnection.findMaxId("IdBasket", "informuserbasket")+1;
			            				BaseConnection.CreateBaskets(IdUser, IdBasket); // создаем новую корзину
		    						}
		            				else if(BaseConnection.getOpenBasketId(IdUser)!=0){ // если у юзера есть незакрытая корзина используем ее
		                    		       IdBasket = BaseConnection.getOpenBasketId(IdUser);
		                    		       }
		            				session.setAttribute("IdBasket", IdBasket); // Устанавливаем новое значение сессии
            				}
            				else {
            					response.getWriter().println("Корзина пуста!");
            				}
	             		} 
                		catch (ClassNotFoundException e1) {
	             				e1.printStackTrace();
	             		} catch (SQLException e1) {
	             				e1.printStackTrace();
	             			}
                	break;
                    default:
        		}
	}
}