<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<link rel="stylesheet" href="views/styles.css">
		<meta charset="UTF-8">
		<title>Приход товара</title>
		<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
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
		<script>
		function sendMark(){
			var x = document.getElementById("idmark").value;
			var str = "idmark="+x;
			$.ajax(
					{
					type: "POST",
					url: "AddGood",
					data: str,
					success:function(answer){
						$("#modellist").html(answer)
						}
					}
			);
		}
		</script>
		<script>
	    function send() {
	            var price = $("#priceform").val();
	            var idmark = $("#idmark").val();
	            var idmodel = $("#idmodel").val();
	            var idtype = $("#idtype").val();
	            var count = $("#countform").val();
	            if(price===null||price===""||idmark===null||idmark===""||idmodel===null||idmodel===""||idtype===null||idtype===""||count===null||count===""){
	            	alert("Заполните все поля!");
	            }
	            else{
	            	var str = "&idtype="+idtype+"&idmark="+idmark+"&idmodel="+idmodel+"&price="+price+"&count="+count+"&chek=send";
	                $.ajax({
	                    method:"get",//HTTP method
	                    url:"AddGood",//your servlet or jsp page name..
	                    data:str,
	                    success:function()
	                    {
	                    }
	                });
	            }
	          }
		</script>
		<form action="" method="get">
			<table class="in">
				<tr>
					<th class="con">Приход товара</th>
					</tr>
					<tr><td>
						<p>Выберите тип устройства</p>
						<select id = 'idtype'>
						<option  value ="" id="">--------</option>
						<c:forEach var="t" items="${types}" varStatus="num">
		  					<option  value ="${t.idType}" id="type">${t.name}</option>
						</c:forEach>
						</select>
						
						<p>Выберите марку</p>
						<select id ='idmark' onchange="sendMark()">
						<option  value ="" id="">--------</option>
						<c:forEach var="t" items="${marks}" varStatus="num">
		  					<option  value ="${t.idMark}">${t.name}</option>
						</c:forEach>
						</select>
						
						<div id="modellist">
						<p>Выберите модель</p>
						<select id ='idmodel'>
						<option  value = "">-------------</option>
						</select>
						</div>
						
						<p>Введите стоимость товара (цифрами)</p>
						<input class="addgoodsform" type="text" id = 'priceform'>
						
						<p>Введите количество товара (цифрами)</p>
						<input class="addgoodsform" type="text" id = 'countform'>
					<br><br><button class="enter" id="submit" onclick="send()">Обновить информацию о товаре</button></td></tr>
			</table>

		</form>
	</body>
</html>