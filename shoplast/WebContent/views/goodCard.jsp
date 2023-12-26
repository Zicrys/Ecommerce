<%@page import="models.BaseConnection"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Ha связи!</title>
		<link rel="stylesheet" href="views/styles.css">
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	</head>
	<script>
		function add(id, price, count){
			var dataStr = "IdGood="+id+"&Price="+price+"&count="+count+"&photo=no";//id_good - это параметр для сервлета
			$.ajax({
				type:"POST",
				url:"catalog",
				data:dataStr,
				success:function(answer){
					alert(answer);
					
					
				}
			});
		}
	</script>
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
		<%
        int IdGood=(int)session.getAttribute("IdGoodPhoto");
        try {
			String title = BaseConnection.getTitle(IdGood);
			String type = BaseConnection.getGoodChar(IdGood, "IdType", "type");
			String photo = BaseConnection.getPhoto(IdGood);
			String mark = BaseConnection.getGoodChar(IdGood, "IdMark", "mark");
			String model = BaseConnection.getGoodChar(IdGood, "IdModel", "model");
			String price = BaseConnection.getGoodPrice(IdGood);
			out.println("<table><tr><th colspan='2'><p class='con'>"+title+"</p></th></tr>");
			out.println("<tr><td rowspan='7'><a href='uploads/large/"+photo+"'><img src='uploads/real/"+photo+"' alt='"+title+"'/></a></td><tr>");
			out.println("<tr><td class='des'><p>Тип устройства: "+type+"</p></td></tr>");
			out.println("<tr><td class='des'><p>Марка: "+mark+"</p></td></tr>");
			out.println("<tr><td class='des'><p>Модель: "+model+"</p></td></tr>");
			out.println("<tr><td class='des'><p>Цена: "+price+"</p></td></tr>");
			out.println("<tr><td class='des'><a class ='addtobk' href='#' onclick='add("+IdGood+","+price+","+1+")'><b>Добавить в корзину<b></a></td></tr></table>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}%>
	</body>
</html>