<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 <link rel="stylesheet" href="views/styles.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Список заказов</title>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<script src="http://code.jquery.com/jquery-1.8.3.js"> </script><!-- подкл. jquery -->
		<script>
		function SeeOrderItems(id){
				var dataStr ="idOrder="+id+"&check=see";//строка запроса	
				$.ajax(
						{
							type:"POST",
							url:"Order",
							data:dataStr,
							success:function(){
								window.location='OrderItemInform';
							}
						}		
					);
				};
		</script>
		<table>
			<tr>
				<th colspan="6" class="con">Информация о заказах:</th>
			</tr>
			<tr>
				<td><b>номер заказа</b></td>
				<td><b>Дата создания заказа</b></td>
				<td><b>ФИО покупателя</b></td>
				<td><b>контактный телефон</b></td>
				<td><b>статус заказа</b></td>
				<td><b>информация</b></td>
			</tr>
				<c:forEach var="i" items="${infoOrders}" varStatus="num">
				<tr>
					<td>${num.count}</td>
					<td>${i.startDate}</td>		
					<td>${i.fio}</td>
					<td>${i.phone}</td>
					<td>${i.status}</td>
					<td><button class="enter" onclick="SeeOrderItems(${i.id})">Посмотреть</button></td>	
				</tr>	
			</c:forEach>	
		</table>
	</body>
</html>