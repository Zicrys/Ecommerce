<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Корзина</title>
		<link rel="stylesheet" href="views/styles.css">
		<script src="http://code.jquery.com/jquery-1.8.3.js"> </script><!-- подкл. jquery -->
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
		<script><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!--подкл. библиотеку jstl--></script>
<script>
		function deleteFromBasket(count, id){
				var dataStr = "goodCount="+count+"&IdBasket="+id+"&check=dfb";//строка запроса	
				$.ajax(
						{
							type:"POST",
							url:"Basket",
							data:dataStr,
							success:function(msg){
								$("#text").html(msg);
								window.location.href = "Basket";//после удаления обращаемся к сервлету для обновления таблицы
							}
						}		
					);
				};
			
			
			function addToOrders(id, sum){
				var dataStr = "&IdBasket="+id+"&check=so&sum="+sum;//строка запроса	
				$.ajax(
					{
						type:"POST",
						url:"Basket",
						data:dataStr,
						success:function(answer){
							window.location.href = "Basket";
							if(answer!=""){
								alert(answer);
								window.location.href = "Basket";
							}
							else{
								window.location.href = "Order";
							}
						}
					}		
				);
			};
		</script>
		
		<table>
		<tr><th colspan="6" class="con">Корзина товаров:</th></tr>
			<tr>
				<td><b>№</b></td>
				<td><b>наименование</b></td>
				<td><b>стоимость за шт. (руб)</b></td>
				<td><b>кол-во (шт)</b></td>
				<td><b>Статус товара</b></td>
				<td><b>удалить</b></td>
			</tr>
			<c:forEach var="b" items="${basket}" varStatus="num">
				<tr>
					<td>${num.count}</td>
					<td>${b.title}</td>
					<td>${b.price}</td>
					<td>${b.count}</td>
					<td>${b.status}</td>
					<td><button class="enter" onclick="deleteFromBasket(${b.goodCount}, ${b.idBasket})">удалить</button></td>
				</tr>
			</c:forEach>
			<tr><td class="sum" colspan="6">Итого: ${sum} руб.</td></tr>
			<tr>
				<td colspan="6"><button onclick="addToOrders(<%= request.getAttribute("idbasket")%>, ${sum})" class="enter">Оформить заказ</button></td>
			</tr>
		</table>
		<p class="authcaption" id = "text"></p>

		
	</body>
</html>