<%@page import="models.BaseConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title>О нас</title>
		<link rel="stylesheet" href="views/styles.css">
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
		<tr><th class="con">Изменить изображение</th></tr>
		<tr>
		<td>
		<%
			session = request.getSession();
		 	int IdGoodPhoto=(int)session.getAttribute("IdGoodPhoto");
		 	String title = BaseConnection.getTitle(IdGoodPhoto);
		 	out.println("<p class='addtobk'>"+title+"</p>");
			%>
				<form action="AddPhoto" method="post" enctype="multipart/form-data">
                <input type="file" name="file" /><br><br>
                <input class ="enter" type="submit" value="Изменить" /><br>
            </form> 
            <div class="addtobk" id="result">
            <p>${requestScope["message"]}</p>
        </div><br>
        <a class="addtobk" href ="catalog">Назад</a></td></tr>
	  	</table>
	</body>
</html>