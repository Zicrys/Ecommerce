<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Каталог товаров</title>
<link rel="stylesheet" href="views/styles.css">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		
	<script>
		function add(id, price, count){
			var dataStr = "IdGood="+id+"&Price="+price+"&count="+count+"&photo=no";//id_good - это параметр для сервлета
			$.ajax({
				type:"POST",
				url:"catalog",
				data:dataStr,
				success:function(answer){
					alert(answer);
					window.location.href = "catalog";
					
					
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
	<p class="authcaption" id="answer"></p>
	<div id="datatable">
	<table>
	<tr>
		<th colspan="8" class="con">Каталог товаров</th>
	</tr>
		<tr>
			<td><b>Номер товара</b></td>
			<td><b>Фото</b></td>
			<td><b>Тип устройства</b></td>
			<td><b>Марка</b></td>
			<td><b>Модель</b></td>
			<td><b>Стоимость товара</b></td>
			<td><b>Количество</b></td>
			<c:set var="username" value="${username}"/>
				<c:choose> 
				  <c:when test="${username == 'admin'}">
				 <td><b>Изменить</b></td>
				  </c:when>
				  <c:otherwise>
				  <td><b>Заказать</b></td>
				  </c:otherwise>
				</c:choose>
				
				
		</tr>
		<c:forEach var="good" items="${goods}" varStatus="num">
			<tr>
				<td>${num.count}</td>
				<td><a class ="addtobk" href="#" onclick="gk(${good.id})"><img src="uploads/thumbs/${good.photo}" alt="${good.type}"></a></td>
				<td><a class ="addtobk" href="#" onclick="gk(${good.id})">${good.type}</a></td>
				<td><a class ="addtobk" href="#" onclick="gk(${good.id})">${good.mark}</a></td>
				<td><a class ="addtobk" href="#" onclick="gk(${good.id})">${good.model}</a></td>
				<td><a class ="addtobk" href="#" onclick="gk(${good.id})">${good.price}</a></td>
				<td><a class ="addtobk" href="#" onclick="gk(${good.id})">${good.count}</a></td>
				<c:set var="username" value="${username}"/>
				<c:choose> 
				  <c:when test="${username == 'admin'}">
				  <td class="hidd" id="admin"><a class ="addtobk" href="#" onclick="photo(${good.id})">Изменить изображение</a></td>
				  </c:when>
				  <c:otherwise>
				  <td class="hid" id="user"><a class ="addtobk" href="#" onclick="add(${good.id},${good.price},${good.count})">Добавить в корзину</a></td>
				  </c:otherwise>
				</c:choose>
				
			</tr>
		</c:forEach>
	<%session = request.getSession();
		if (session.getAttribute("Username")!=null && session.getAttribute("Password")!=null){
		if (!session.getAttribute("Username").equals("admin")){
					out.println("<tr><td colspan='8'><a class='addtobk' href='Basket'>Перейти в корзину</a></td><td>");
				}
				if (session.getAttribute("Username").equals("admin")){
					out.println("<tr><td class = 'add' colspan='4'><a class='addtobk' href='AddGood'>Добавить товар на склад (приход)</a></td>");
					out.println("<td class = 'add' colspan='4'><a href='NewGoods' class='addtobk'>Добавить новую позицию в каталог</a></td></tr>");
					}
					}%>
		</table>
		</div>
		<script>
		function photo(id){
			var dataStr = "&id="+id+"&photo=yes";
			$.ajax({
				type:"POST",
				url:"catalog",
				data:dataStr,
				success:function(answer){
					window.location.href = "AddPhoto";	
				}
			});
		}
	</script>
	<script>
		function gk(id){
			var dataStr = "&id="+id+"&photo=gk";
			$.ajax({
				type:"POST",
				url:"catalog",
				data:dataStr,
				success:function(answer){
					window.location.href = "GoodCard";	
				}
			});
		}
	</script>
</body>
</html>