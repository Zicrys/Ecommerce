<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<link rel="stylesheet" href="views/styles.css">
		<meta charset="UTF-8">
		<title>Регистрация</title>
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
			var fio = $('#fio').val();
			var Username = $('#Username').val();
			var Password = $('#Password').val();
			var re = /^((8|\+7)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$/;
			var phone = $('#phone').val();
			var valid = re.test(phone);
		    if (valid){
		    var str = "fio="+fio+"&phone="+phone+"&Username="+Username+"&Password="+Password;
			$.ajax(
					{
					type: "POST",
					url: "Registration",
					data: str,
					success:function(answer){
						if(answer==="Вы успешно зарегистрированы!"){
							alert(answer);
							window.location='Mainpage';
						}
						else{
							alert(answer);
						}
						}
					}
			);
		    }
		    else{
		    	alert('Номер телефона введен неправильно!');
		    }
		}
		</script>
		<table class="in">
		<tr>
		<th class="con">Регистрация</th>
		</tr>
			<tr>
				<td>
					<p>Введите ФИО</p>
					<input class="logform" type="text" value="" id="fio">
					<p>Введите номер телефона</p>
					<input class="logform" type="text" value="" id="phone">
					<p>Введите Логин</p>
					<input class="logform" type="text" value="" id="Username">
					<p>Введите Пароль</p>
					<input class="logform" type="password" value="" id="Password"> <br> <br>
					<button class = "enter" onclick="send()">Зарегистрироватьcя</button>
					<br><a class = "Registration" href='Authorization'>Войти</a>
					<div></div>
				</td>
			</tr>
		</table>			
	</body>
</html>