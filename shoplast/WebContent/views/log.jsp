<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <link rel="stylesheet" href="views/styles.css">
	<head>
		<meta charset="UTF-8">
		<title>Авторизация</title>
		
		<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
	</head>
	<body>
		<header>
		<div class ="head">
			<div class = "log">
				<p class="logt"><b>На связи!</b></p><p class="slog"><b>Твой телефон - твой выбор!</b></p>
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
				var Username = $('#Username').val();
				var Password = $('#Password').val();
				var str = "Username="+Username+"&Password="+Password;
				$.ajax(
						{
						type: "POST",
						url: "Authorization",
						data: str,
						success:function(answer){
							$("#wrong").html(answer)
							if(answer=="   "){
								window.location='Mainpage';
							}
							}
						}
				);
			}
			</script>
		<table class="in">
		<tr>
		<th class="con">Авторизация</th>
		</tr>
			<tr>
				<td>
					<p>Введите логин</p>
					<input class="logform" type="text" id="Username">
					<p>Введите пароль</p>
					<input class="logform" type="password" id="Password"> <br> <br>
					<button class = "enter" onclick="send()">Вход</button>
					<br><a class = "Registration" href='Registration'>Регистрация</a>
					<div id="wrong"></div>

				</td>
			</tr>
		</table>			
	</body>
</html>