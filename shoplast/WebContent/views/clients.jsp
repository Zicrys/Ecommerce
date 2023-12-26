<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Список клиентов</title>
		<link rel="stylesheet" href="views/styles.css">
		<script src="http://code.jquery.com/jquery-1.8.3.js"> </script><!-- подкл. jquery -->
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!--подкл. библиотеку jstl-->
	</head>
	<body>
		<header>
		<div class ="head">
			<div class = "log">
				<p class="logt"><b>На связи!</b></p><p class="slog"><b>Твой телефон - твой выбор!</b></p>
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
		
		<script>
			function redirect(IdUser){
				var dataStr = "IdUser="+IdUser;//строка запроса	
				$.ajax(
					{
						type:"POST",
						url:"Clients",
						data:dataStr,
						success:function(answer){
								window.location='Orders';
						}
					}		
				);
			}
		</script>
		<table>
			<tr>
		<th colspan="5" class="con">Список клиентов</th>
	</tr>
		<tr>
				<td><b>№</b></td>
				<td><b>Имя</b></td>
				<td><b>Телефон</b></td>
				<td><b>Логин</b></td>
				<td><b>Информация о заказах</b></td>
			</tr>
			<c:forEach items="${clients}"  var="cl"  varStatus="num">
				<tr>
					<td>${num.count}</td>
					<td>${cl.fio}</td>
					<td>${cl.phone}</td>
					<td>${cl.username}</td>
					<td><button class="enter" onclick="redirect(${cl.id})">Посмотреть</button></td>
				</tr>
			</c:forEach>
		</table>
		
		<h2></h2>
	</body>
</html>