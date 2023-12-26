<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 <link rel="stylesheet" href="views/styles.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Все заказы</title>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	</head>
	<body>
		<header>
		<div class ="head">
			<div class = "log">
				<p class="logt"><b>На связи!</b><p class="slog"><b>Твой телефон - твой выбор!</b></p>
			</div>
			<div >
			<%
			session = request.getSession();
			if(session.getAttribute("Username")!=null){
				String Username = (String) session.getAttribute("Username");
				out.println("<p class='hello'>Привет, "+Username+"!</p>");
			}%>
				</div>
		</div>
		</header>
		
		<nav>
			<ul class = "ulbut">
				<li class = "men"><a class = "m" href="Mainpage">Главная</a></li>
				<li class = "men"><a class = "m" href="catalog">Каталог товаров</a></li>
				<li class ="men" ><a class = "m" href="Info">О нас</a></li>
				<%session = request.getSession();
		
				if (session.getAttribute("Username")!=null && session.getAttribute("Password")!=null){
					
					if (session.getAttribute("Username").equals("admin")){
						out.println("<li class = 'men'><a class = 'm' href='ChangeOrderStatus'>Заказы</a></li>");
						out.println("<li class = 'men'><a class = 'm' href='Clients'>Клиенты</a></li>");
						out.println("<li class = 'men'><a class = 'm' href='Exit'>Выход</a></li>");
					}
					else{
						out.println("<li class = 'men'><a class = 'm' href='Order'>Мои заказы</a></li>");
						out.println("<li class = 'men'><a class = 'm' href='Basket'>Корзина</a></li>");
						out.println("<li class = 'men'><a class = 'm' href='Exit'>Выход</a></li>");
					}
					
				}
				else{
					out.println("<li class = 'men'><a class = 'm' href='Authorization'>Вход</a></li>");
					out.println("<li class = 'men'><a class = 'm' href='Registration'>Регистрация</a></li>");
				}
				%>
			</ul>
		</nav>
		<table>
		<tr>
		<th colspan="4" class="con">Список товаров заказа:</th>
			</tr>
			<tr>
				<td><b>№</b></td>
				<td><b>Наименование</b></td>
				<td><b>Количество</b></td>
				<td><b>Цена</b></td>
			</tr>
			<c:forEach var="OrderGood" items="${OrderGoods}" varStatus="num" >
				<tr>
					<td width="10%">${num.count}</td>
					<td>${OrderGood.title}</td>		
					<td>${OrderGood.count}</td>
					<td>${OrderGood.price}</td>			
				</tr>
			</c:forEach>
			<tr><td class="sum" colspan="6">Итого: ${sum} руб.</td></tr>
			<tr><td colspan="4"><a class="addtobk" href = Order>Назад</a></td></tr>
		</table>
		</body>
</html>