<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<link rel="stylesheet" href="views/styles.css">
		<meta charset="UTF-8">
		<title>Добавление товара</title>
		<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
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
		function send(){
			var price = $("#price").val();
            var mark = $("#mark").val();
            var model = $("#model").val();
            var types = $("#types").val();
            var count = $("#count").val();
        	if(price!="" && count!=""){
        	var str = "types="+types+"&mark="+mark+"&model="+model+"&price="+price+"&count="+count;
			$.ajax(
					{
					type: "POST",
					url: "NewGoods",
					data: str,
					success:function(answer){
						if(answer==="Товар успешно добавлен!"){
						alert(answer);
						window.location='NewGoods';
					}
					else{
						alert(answer);
					}
				}
			}
			);
        }
        else{
        	alert("Заполните все поля!");
        	}
		}
		</script>
		<table class="in">
		<tr>
		<th class="con">Добавление нового товара</th>
			</tr>
				<tr>
					<td>
						<p>Введите тип устройства</p>
						<input class="logform" type="text" value="" id = 'types'>
						
						<p>Введите марку</p>
						<input class="logform" type="text" value="" id = 'mark'>
						
						<p>Введите модель</p>
						<input class="logform" type="text" value="" id = 'model'>
						
						<p>Введите стоимость товара</p>
						<input class="logform" type="text" value="" id = 'price'>
						
						<p>Введите количество товара</p>
						<input class="logform" type="text" value="" id = 'count'><br><br>
						<button class="enter" id="submit" onclick="send()">Добавить новый товар</button>
					</td>
				</tr>
				
			</table>		
	</body>
</html>