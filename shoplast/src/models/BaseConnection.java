package models;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BaseConnection {
	static Connection connection;
	static Statement statement;
	public static ArrayList<Goods> goods = new ArrayList<Goods>();
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<BasketInit> basket = new ArrayList<BasketInit>();
	public static ArrayList<OrderList> orders = new ArrayList<OrderList>();
	public static ArrayList<OrderItems> orderitems = new ArrayList<OrderItems>();
	public static ArrayList<Types> types = new ArrayList<Types>();
	public static ArrayList<Marks> marks = new ArrayList<Marks>();
	static String optionStr = "";
	
	// Подключение к базе данных
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		   Class.forName("com.mysql.jdbc.Driver");
		   String url = "jdbc:mysql://localhost/phonedb?serverTimezone="+TimeZone.getDefault().getID();
		   return DriverManager.getConnection(url,"root","dmkddmkd1");
	   }

	// Функция используется при увличении количества товара на складе (приход товара). 
	//Получаем id товара по выбранным марке и модели, и к уже имеющемуся на складе количеству товара прибавляем вновь поступивший.
	public static int findGoodByTitle(int IdType, int IdMark, int IdModel, int Price , int newCount) throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT IdGood, Count FROM good WHERE IdMark = '"+IdMark+"'and IdModel = '"+IdModel+"'");
		try {
		ResultSet rs = ps.executeQuery();
		int IdGood = 0;
		int Count = 0;
		if (rs.next()) {
			IdGood = rs.getInt("IdGood");
			Count = rs.getInt("Count");
			try {
				String query = "update good set Count = ?, Price = ? where IdGood = ?";
				PreparedStatement preparedStmt = c.prepareStatement(query);
				preparedStmt.setInt   (1, Count+newCount);
				preparedStmt.setInt   (2, Price);
				preparedStmt.setInt   (3, IdGood);
				preparedStmt.executeUpdate();
				}
				catch (Exception e){
				  System.err.println("Got an exception! ");
					      System.err.println(e.getMessage());
					    }
				   }
				   else {
					   return 0;
				   }
					return 1;
		}
		finally {
			c.close();
			ps.close();
		}
		}
	
	// Проверка на наличии в бд товара с неким названием
	public static int getGoodByTitle(int IdType, int IdMark, int IdModel) throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT IdGood, Count FROM good WHERE IdMark = '"+IdMark+"'and IdModel = '"+IdModel+"'");
		try{
			ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return 1;
			}
		else {
			return 0;
		}
		}
		finally {
			c.close();
			ps.close();
			}
	}
	
	// Функция используется при добавлении в каталог новых  позиций. Во избежание дублирования записей идет проверка на тип устройства, марку и модель.
	// Если, например, у нас уже есть в базе тип устройства "смартфон", то в таблице "Тип" мы не заводим для него новую запись с новым id, а берем уже имеющийся id.
	// В случае, если указанного типа еще нет в базе, мы создаем новую запись в графе тип.
	// После того, как id типа, марки и модели получены, мы проверяем, нет ли точно такого товара в базе.
	// Если есть, то увеличиваем количство на указанное число, если нет, то создам в good новую запись.
	// По сути, можно было бы использовать эту функцию для добавления и на склад и в каталог, не создавая две формы добавления товара,
	// Но форма "приход товара" экономит время за счет того, что не приходится каждый раз вбивать уже имеющийся товар в ручную. 
	// Достаточно выбрать из выпадающего списка нужную запись.
	public static int AddNewGood(String Type, String Mark, String Model, int Price, int Count) throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT IdType FROM type WHERE Name = '"+Type+"'");
		PreparedStatement ps2 = c.prepareStatement("SELECT IdMark FROM mark WHERE Name = '"+Mark+"'");
		PreparedStatement ps3 = c.prepareStatement("SELECT IdModel FROM model WHERE Name = '"+Model+"'");
		try {
		ResultSet rs3 = ps3.executeQuery();
		ResultSet rs2 = ps2.executeQuery();
		ResultSet rs = ps.executeQuery();
		int IdType = 0;
		int IdMark = 0;
		int IdModel = 0;
		if (rs.next()) {
			IdType = rs.getInt("IdType");
		}
		else {
			IdType = findMaxId("IdType", "type")+1;
			PreparedStatement ps1 = c.prepareStatement("INSERT INTO `type`(`IdType`, `Name`) VALUES ('"+IdType+"','"+Type+"')");
			try {
			if(ps1.executeUpdate()>0) {
			}
			else {
			}
			}
			finally{
				ps1.close();
			}
		}
		if (rs2.next()) {
			IdMark = rs2.getInt("IdMark");
		}
		else {
			IdMark = findMaxId("IdMark", "mark")+1;
			PreparedStatement ps4 = c.prepareStatement("INSERT INTO `mark`(`IdMark`, `Name`) VALUES ('"+IdMark+"','"+Mark+"')");
			try {
			if(ps4.executeUpdate()>0) {
			}
			else {
				
			}
			}
			finally {
				ps4.close();
			}
		}
		if (rs3.next()) {
			IdModel = rs3.getInt("IdModel");
		}
		else {
			IdModel = findMaxId("IdModel", "model")+1;
			PreparedStatement ps5 = c.prepareStatement("INSERT INTO `model`(`IdModel`, `IdMark`, `Name`) VALUES ('"+IdModel+"', '"+IdMark+"','"+Model+"')");
			try {
			if(ps5.executeUpdate()>0) {
			}
			else {
			}
			}
			finally {
				ps5.close();
			}
		}
		if(getGoodByTitle(IdType, IdMark, IdModel)==0) {
			addGood(IdType, IdMark, IdModel, Price, Count);
		}
		else {
			findGoodByTitle(IdType, IdMark, IdModel, Price, Count);
		}
		return 1;
		}
		finally {
			c.close();
			ps.close();
			ps2.close();
			ps3.close();
			
		}
	}
	
	// Универсальныя функция для поиска наибольшего id в таблице.
	public static int findMaxId(String id, String table) throws ClassNotFoundException, SQLException {
		   Connection c = getConnection();
		   PreparedStatement ps = c.prepareStatement("SELECT MAX("+id+") FROM "+table);
		   try {
		   ResultSet rs = ps.executeQuery();
		   int max_id = 0;
		   if (rs.next()) {
			   max_id = rs.getInt(1);
		   }
		   return max_id;
		   }
		   finally {
			   c.close();
			   ps.close();
		   }
	}
	
	// ищем сумму заказа юзера
	public static int findSum(int IdBasket) throws ClassNotFoundException, SQLException {
		   Connection c = getConnection();
		   PreparedStatement ps = c.prepareStatement("SELECT SUM(Price) FROM basketitems_tmp WHERE IdBasket='"+IdBasket+"'");
		   try {
		   ResultSet rs = ps.executeQuery();
		   int sum = 0;
		   if (rs.next()) {
			   sum = rs.getInt("SUM(Price)");
		   }
		   return sum;
		   }
		   finally {
			   c.close();
			   ps.close();
		   }
	}
	
	// Функция добавления нового товара в базу данных
	public static boolean addGood(int IdType, int IdMark, int IdModel, int Price, int Count) throws ClassNotFoundException, SQLException {
		   int IdGood = findMaxId("IdGood", "good")+1;
		   Connection c = getConnection();
		   PreparedStatement ps = c.prepareStatement("INSERT INTO `good`(`IdGood`, `IdType`,`IdMark`, `IdModel`, `Price`, `Count`) VALUES ('"+IdGood+"','"+IdType+"', '"+IdMark+"', '"+IdModel+"', '"+Price+"', '"+Count+"')");
		   try {
		   if(ps.executeUpdate()>0) {
			   return true;
		   }
		   else {
			   return false;
		   }
		   }
		   finally {
				c.close();
				ps.close();
		   }
	}
	
	// Функция добавления в базу данных имени фотографии для товара с конкретным id
	public static boolean insertPhoto(int IdGood, String Name) throws ClassNotFoundException, SQLException {
		   int IdPhoto = findMaxId("IdPhoto", "photo")+1;
		   Connection c = getConnection();
		   PreparedStatement ps1 = c.prepareStatement("select IdPhoto from photo where IdGood='"+IdGood+"'");
		   ResultSet rs1 = ps1.executeQuery();
		   try {
			if (rs1.next()) {
				PreparedStatement ps2 = c.prepareStatement("UPDATE photo set Name = '"+Name+"' WHERE IdGood = '"+IdGood+"'");
				try {
				if(ps2.executeUpdate()>0) {
					return true;
				}
				else {
					
					return false;
				}
				}
				finally {
					ps2.close();
				}
			}
				else {
					   PreparedStatement ps = c.prepareStatement("INSERT INTO `photo`(`IdPhoto`, `IdGood`,`Name`) VALUES ('"+IdPhoto+"','"+IdGood+"', '"+Name+"')");
					   try {
					   if(ps.executeUpdate()>0) {
						   return true;
					   }
					   else {
							
						   return false;
					   }
					   }
					   finally {
						   ps.close();
					   }
				}
		   }
		   finally {
			   c.close();
			   ps1.close();
		   }
	}
	
	// Поиск id юзера по телефону
	public static int findIdUser(String phone) throws ClassNotFoundException, SQLException {
	Connection c = getConnection();
	PreparedStatement ps = c.prepareStatement("SELECT IdUser FROM USER WHERE phone = '"+phone+"'");
	try {
	ResultSet rs = ps.executeQuery();
	if (rs.next()) {
		return rs.getInt(1);
		}
		else {
			return -1;
		}
	}
	finally{
		c.close();
		ps.close();
	}
	}
	   
	
	// Поиск юзера по логину
	public static int findbyUsername(String Username) throws ClassNotFoundException, SQLException {
	Connection c = getConnection();
	PreparedStatement ps = c.prepareStatement("SELECT IdUser FROM USER WHERE Username = '"+Username+"'");
	try {
	ResultSet rs = ps.executeQuery();
	if (rs.next()) {
		return rs.getInt(1);
		}
		else {
			return -1;
		}
	}
	finally {
		ps.close();
		c.close();
	}
	}
		   
	// Поиск юзера по логину и паролю 
	public static int FindUser(String Username, String Password) throws SQLException, ClassNotFoundException {
		int userIsFind = 0;
		try {
				Connection c = getConnection();
			   PreparedStatement ps = c.prepareStatement("SELECT IdUser FROM user WHERE Username = '"+ Username+"' and Password = '"+Password+"'");
			   try {
			   ResultSet rs = ps.executeQuery();
			   if (rs.next()) {

				   return rs.getInt(1);
			   }
			   else {
				   
				   return -1;
			   }
			}
			   finally {
				   c.close();
				   ps.close();
			   }
		}catch (SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
				return 0;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userIsFind;
	}
		
	
	// Добавление нового юзера
	public static boolean addUser(String Username, String Password, String fio, String phone) throws ClassNotFoundException, SQLException {
		   int id = findMaxId("IdUser", "user")+1;
		   Connection c = getConnection();
		   PreparedStatement ps = c.prepareStatement("INSERT INTO `user`(`IdUser`,`Username`, `fio`, `phone`, `Password`) VALUES ('"+id+"','"+Username+"', '"+fio+"', '"+phone+"', '"+Password+"')");
		   try {
		   if(ps.executeUpdate()>0) {
			   return true;
		   }
		   else {

			   return false;
		   }
		   }
		   finally {
				c.close();
				ps.close(); 
		   }
	}
	
	// Создаем список товаров	
	public static ArrayList<Goods> getGoods() throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("select g.IdGood, g.IdType, g.IdMark, g.IdModel, g.Price, g.Count, t.Name as t_name, mr.Name as mark_name, md.Name as model_name, p.Name as photoName from good g left join type t on g.IdType=t.IdType left join mark mr on g.IdMark=mr.IdMark left join model md on g.IdModel=md.IdModel left join photo p on g.IdGood=p.IdGood");
		try {
		ResultSet rs = ps.executeQuery();
		
		goods.clear();
		while(rs.next()) {
			int id = rs.getInt("IdGood");
			String photo = rs.getString("photoName");
			int price = rs.getInt("Price");
			String type = rs.getString("t_name");
			String mark = rs.getString("mark_name");
			String model = rs.getString("model_name");
			int count = rs.getInt("Count");
			goods.add(new Goods(id, photo, type, mark, model, price, count));
		}
		return goods;
		}
		finally {
			c.close();
			ps.close();
		}
	}
		
	// Создаем список типов
	public static ArrayList<Types> getTypes() throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("select IdType, Name FROM type");
		try {
		ResultSet rs = ps.executeQuery();
		types.clear();
		while(rs.next()) {
			int idType = rs.getInt("IdType");
			String Name = rs.getString("Name");;
			types.add(new Types(idType, Name));
		}

		return types;
		}
		finally {
			c.close();
			ps.close();
		}
	}
	
	// Создаем список марок
	public static ArrayList<Marks> getMarks() throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("select IdMark, Name FROM mark");
		try {
		ResultSet rs = ps.executeQuery();
		marks.clear();
		while(rs.next()) {
			int idMark = rs.getInt("IdMark");
			String Name = rs.getString("Name");;
			marks.add(new Marks(idMark, Name));
		}

		return marks;
		}
		finally {
			c.close();
			ps.close();
		}
	}
		
	//Создам список моделей
	public static String getModel(int idMark) throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("select IdModel, Name FROM model where IdMark = '"+idMark+"'");
		try {
		ResultSet rs = ps.executeQuery();
		optionStr="";
		while(rs.next()) {
			int IdModel = rs.getInt("IdModel");
			String Name = rs.getString("Name");
			optionStr += "<option value="+"'"+IdModel+"'>"+Name+"</option>"; // динамически добавляем значения выпадающего списка моделей в зависимости от того, какая марка выбрана
		}
		
		return optionStr;
		}
		finally {
			c.close();
			ps.close();
		}
	}
		
	// Создаем список юзеров	
	public static ArrayList<User> getUsers() throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("select * from user");
		try {
		ResultSet rs = ps.executeQuery();
		users.clear();
		while(rs.next()) {
			int id = rs.getInt("IdUser");
			String username = rs.getString("Username");
			String fio = rs.getString("fio");
			String phone = rs.getString("phone");
			users.add(new User(id, username, fio, phone));
		}
		return users;
		}
		finally {
			c.close();
			ps.close();	
		}
	}
  
	// Получаем полное название товара "Тип+Марка+Модель"
   public static String getTitle(int IdGood) throws SQLException, ClassNotFoundException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("select g.IdGood, g.IdType, g.IdMark, g.IdModel, t.Name as t_name, mr.Name as mark_name, md.Name as model_name from good g left join type t on g.IdType=t.IdType left join mark mr on g.IdMark=mr.IdMark left join model md on g.IdModel=md.IdModel where IdGood = "+IdGood+"");
	   try {
	   ResultSet rs = ps.executeQuery();
	   String title = "";
	   if (rs.next()) {
		   title = rs.getString("t_name")+" "+rs.getString("mark_name")+" "+rs.getString("model_name");
	   }
		
	return title;
	   }
	   finally {
		   c.close();
			ps.close();
	   }
	   
   }
   
   // универсальная функция для нахождения характеристик устройства.
   public static String getGoodChar(int IdGood, String col, String table) throws SQLException, ClassNotFoundException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("select g."+col+", t.Name as t_name from good g left join "+table+" t on g."+col+"=t."+col+" where IdGood = "+IdGood+"");
	   try {
	   ResultSet rs = ps.executeQuery();
	   String cr = "";
	   if (rs.next()) {
		   cr = rs.getString("t_name");
	   }
	return cr;
	   }
	   finally {
		   c.close();
			ps.close();
	   }
	   
   }
   
   // получаем цену товара
   public static String getGoodPrice(int IdGood) throws SQLException, ClassNotFoundException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("select Price from good where IdGood = "+IdGood+"");
	   try {
	   ResultSet rs = ps.executeQuery();
	   String cr = "";
	   if (rs.next()) {
		   cr = rs.getString("Price");
	   }
		
	return cr;
	   }
	   finally {
		   c.close();
			ps.close();
	   }
	   
   }
   // получаем сумму заказа
   public static String getSum(String idOrder) throws SQLException, ClassNotFoundException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("select sumcount from baskets where IdOrder = "+idOrder+"");
	   try {
	   ResultSet rs = ps.executeQuery();
	   String cr = "";
	   if (rs.next()) {
		   cr = rs.getString("sumcount");
	   }
		
	return cr;
	   }
	   finally {
		   c.close();
			ps.close();
	   }
	   
   }
   
   // Получаем фото товара
   public static String getPhoto(int IdGood) throws SQLException, ClassNotFoundException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("select p.Name, g.IdGood as IdGood from photo p inner join good g on p.IdGood=g.IdGood where p.IdGood='"+IdGood+"'");
	   try {
	   ResultSet rs = ps.executeQuery();
	   String cr = "";
	   if (rs.next()) {
		   cr = rs.getString("p.Name");
	   }
		
	return cr;
	   }
	   finally {
		   c.close();
			ps.close();
	   }
	   
   }
	
   // Добавляем товар во временный список до оформления заказа
   public static boolean addGoodBasket(int IdGood, int price, int IdBasket) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		int MaxId = findMaxId("GoodCount", "basketitems_tmp")+1;
		int IdStatus = 0;
		PreparedStatement ps = c.prepareStatement("INSERT INTO `basketitems_tmp`(`GoodCount`, `IdGood`,`Price`, `Count`, `IdBasket`, `IdStatus`) VALUES ('"+MaxId+"', '"+IdGood+"', '"+price+"', '"+1+"', '"+IdBasket+"', '"+IdStatus+"')");
		try {
		ChangeCountGood((int)-1, IdGood);
		if(ps.executeUpdate()>0) {
			return true;
		}
		
		return false;
		}
		finally {
			c.close();
			ps.close();
		}
		
   }
		
   // Добавляем товары из временного списка в конечный список, где хранятся данные обо всех купленных товарах.
   public static boolean addToOrders(int IdBasket) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("INSERT INTO `basketitem`(`IdBasket`,`IdGood`, `Count`, `IdStatus`, `Price`)  SELECT IdBasket, IdGood, Count, IdStatus, Price FROM `basketitems_tmp` WHERE IdBasket = '"+IdBasket+"'");
		try {
		if(ps.executeUpdate()>0) {
			return true;
		}
		return false;
		}
		finally {
			c.close();
			ps.close();
		}
		
   }
   
   // получам список товаров к корзине
   public static ArrayList<BasketInit> getDataFromBasket(int SessionIdBasket) throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("SELECT b.IdGood, b.IdBasket, b.Price, b.GoodCount, b.IdStatus, b.Count, s.StatusName FROM basketitems_tmp b left join itemstatus s on b.IdStatus=s.IdStatus WHERE b.IdBasket='"+SessionIdBasket+"'");
	   try {
	   ResultSet rs = ps.executeQuery();
	   basket.clear();
	   while(rs.next()) {
		   int IdGood = rs.getInt("IdGood");
		   String title = getTitle(IdGood);
		   int b_IdBasket = rs.getInt("IdBasket");
		   int GoodCount = rs.getInt("GoodCount");
		   int price = rs.getInt("Price");
		   int count = rs.getInt("Count");
		   String Status = rs.getString("s.StatusName");
		   basket.add(new BasketInit(GoodCount, b_IdBasket, title, IdGood, price, count, Status));
	   }
		
	return basket;  
	   }
	   finally {
		   c.close();
			ps.close();
	   }
   }
   
   // Получам товары в заказе по id заказа
   public static ArrayList<OrderItems> getOrderItems(String idOrder) throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   int Status=1;
	   PreparedStatement ps = c.prepareStatement("select bi.IdGood, bi.IdStatus, bi.price, bi.count from basketitem bi left join baskets b on bi.IdBasket=b.idBasket where IdOrder = '"+idOrder+"' and bi.IdStatus='"+Status+"'");
	   try {
	   ResultSet rs = ps.executeQuery();
	   orderitems.clear();
	   while(rs.next()) {
		   int IdGood = rs.getInt("bi.IdGood");
		   String title = getTitle(IdGood);
		   int price = rs.getInt("bi.price");
		   int count = rs.getInt("bi.count");
		   orderitems.add(new OrderItems(title, count, price));
	   }
		
	return orderitems;  
	   }
	   finally {
		   c.close();
			ps.close();
	   }
   }

   // Меняем количество товара при добавлнии или удалении.
   public static boolean ChangeCountGood(int newCount, int IdGood) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps1 = c.prepareStatement("select Count from good WHERE IdGood = '"+IdGood+"'");
		try {
		ResultSet rs = ps1.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt("Count");
			if(count>0) {
				PreparedStatement ps = c.prepareStatement("UPDATE good set Count = '"+(count+newCount)+"' WHERE IdGood = '"+IdGood+"'");
				try {
				if(ps.executeUpdate()>0) {
					return true;
				}
				else {
					
					return false;
				}
				}
				finally {
					ps.close();
				}
			}
			else {
				PreparedStatement ps = c.prepareStatement("UPDATE good set Count = 0 WHERE IdGood = '"+IdGood+"'");
				try {
				if(ps.executeUpdate()>0) {
					return true;
				}
				else {

					return false;
					}
				}
				finally {
					ps.close();
				}
			}
		}
		
		return true;
		}
		finally {
			c.close();
			ps1.close();
		}
  
   }
   
   // Создаем новый заказ со статусом "в работе"
   public static boolean createOrder(int id_user, int id_basket, String StartDate, String FinishDate, int sum) throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   int IdOrder = findMaxId("IdOrder", "baskets")+1;
	   int IdStatus = 0;
	   PreparedStatement ps = c.prepareStatement("INSERT INTO `baskets`(`IdOrder`,`IdUser`, `IdBasket`,`StartDate`, `FinishDate`, `IdStatus`, `sumcount`) VALUES ('"+IdOrder+"', '"+id_user+"', '"+id_basket+"', '"+StartDate+"', '"+FinishDate+"', '"+IdStatus+"', '"+sum+"')");
	   try {
	   if(ps.executeUpdate()>0) {
		   return true;
	   }
		
	   return false;
	   }
	   finally {
		   c.close();
			ps.close();
	   }
	   
   }
   
   // Создаем список всех заказов
   public static ArrayList<OrderList> getNewOrders() throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("select b.IdOrder, b.StartDate, b.FinishDate, u.fio as fio, u.phone as phone from baskets b inner join user u on b.IdUser = u.IdUser");
	   try {
	   ResultSet rs = ps.executeQuery();
	   int IdOrder = 1;
	   String StartDate = "";
	   String FinishDate = "";
	   String status = "";
	   String fio = "";
	   String phone = "";
	   orders.clear();
	   while(rs.next()) {
		   StartDate = rs.getString("b.StartDate");
		   FinishDate = rs.getString("b.FinishDate");
		   fio = rs.getString("fio");
		   phone = rs.getString("phone");
		   IdOrder = rs.getInt("b.IdOrder");
		   status = getOrderStatus(IdOrder);
		   orders.add(new OrderList(StartDate, FinishDate, IdOrder, fio, phone, status));
	   }
	  
	   return orders;  
	   }
	   finally {
		   c.close();
		   ps.close();
	   }
   }
   
   // Получаем статус заказа словами, а не id
   public static String getOrderStatus(int IdOrder) throws ClassNotFoundException, SQLException{
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("SELECT s.StatusName FROM statuses s inner join baskets b on s.IdStatus=b.IdStatus WHERE IdOrder = '"+IdOrder+"'");
	   try {
	   ResultSet rs = ps.executeQuery();
	   if (rs.next()) {
		   String status = rs.getString("s.StatusName");
		   return status;
	   }
	   else {
		   return null;
	   }
	   }
	   finally {
		   c.close();
		   ps.close();
	   }
   }
   
   // Получаем список заказов конкретного юзера
   public static ArrayList<OrderList> getInfoOrders(int IdUser) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("select b.IdOrder, b.StartDate, b.FinishDate, u.fio as fio, u.phone as phone from baskets b inner join user u on b.IdUser = u.IdUser  WHERE b.IdUser ="+IdUser+"");
		try {
		ResultSet rs = ps.executeQuery();
		int IdOrder = 1;
		String StartDate = "";
		String FinishDate = "";
		String status = "";
		String fio = "";
		String phone = "";
		orders.clear();
		while(rs.next()) {
			StartDate = rs.getString("b.StartDate");
			FinishDate = rs.getString("b.FinishDate");
			fio = rs.getString("fio");
			phone = rs.getString("phone");
			IdOrder = rs.getInt("b.IdOrder");
			status = getOrderStatus(IdOrder);
			orders.add(new OrderList(StartDate, FinishDate, IdOrder, fio, phone, status));
		}

		return orders;  
		}
		finally{
			c.close();
			ps.close();
		}
	}
	  
	// Очищаем временный список товаров, добавленных в корзину после оформления заказа
	public static int clearBasket(int id_basket) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("DELETE from basketitems_tmp WHERE IdBasket = '"+id_basket+"'");
		try {
		int rs = ps.executeUpdate();
		
		return rs;
		}
		finally {
			c.close();
			ps.close();
		}
	}
	   
	// Обновляем статус добавленного в корзину товара после оформления заказа. 
	public static boolean changeItemStatus(int newStatus, int id_basket) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("UPDATE basketitems_tmp set IdStatus = '"+newStatus+"' WHERE IdBasket = '"+id_basket+"'");
		try {
		if(ps.executeUpdate()>0) {
			return true;
		}
		else {
			
			return false;
		}  
		}
		finally {
			c.close();
			ps.close();
		}
	}
	
	// Администратор при нажатии на кнопку "готово" изменяет статус заказа с "в работе" на "отправлен"
	public static boolean changeOrderStatus(int idorder, String finDate) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("UPDATE baskets set IdStatus = '"+1+"', FinishDate='"+finDate+"' WHERE IdOrder = '"+idorder+"'");
		try {
		if(ps.executeUpdate()>0) {
			return true;
		}
		else {
			
			return false;
		}  
		}
		finally {
			c.close();
			ps.close();	
		}
	}
	
	// Проверяем перед оформлением заказа, не пуста ли корзина
	public static boolean checkBasketEmpty(int id_Basket) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT IdGood FROM basketitems_tmp WHERE IdBasket = '"+id_Basket+"'");
		try {
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return true;
		}
		return false;
		}
		finally {
			c.close();
			ps.close();
		}
	}
	
	// Если пользователь удаляет товар из корзины, то этот товар со статусом "0" попадает из временной корзины в постоянную. Таким образом, администратор, при желании, может видеть, что покупатели добавляют в корзину, но не берут.
	// Но т.к. пользователь оформляет только товары со статусом "1", он этот товар он уже не видит в корзине и в оформленные товары этот товар не попадает. Нужен чисто для статистики.
	public static boolean InsertDeletedFromBasket(int GoodCount, int id_Basket) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		int GoodCounty=findMaxId("GoodCount", "basketitem")+1;
		PreparedStatement ps = c.prepareStatement("SELECT IdBasket, IdGood, Count, IdStatus, Price, GoodCount FROM basketitems_tmp WHERE GoodCount = '"+GoodCount+"' AND IdBasket = '"+id_Basket+"'");
		try {
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		   int IdGood = rs.getInt("IdGood");
		   int IdBasket = rs.getInt("IdBasket");
		   int Price = rs.getInt("Price");
		   int Count = rs.getInt("Count");
		   int IdStatus = rs.getInt("IdStatus");
		   PreparedStatement ps1 = c.prepareStatement("INSERT INTO basketitem(GoodCount, IdBasket, IdGood, Count, IdStatus, Price) VALUES('"+GoodCounty+"', '"+IdBasket+"', '"+IdGood+"', '"+Count+"', '"+IdStatus+"', '"+Price+"')");
		   if(ps1.executeUpdate()>0) {
			   	ChangeCountGood(1, IdGood);
			   return true;
		   }
		}
			
			return false;
		}
		finally {
			c.close();
			ps.close();
		}
	}
	
	// После оформления заказа временная корзина очищается. 
	public static boolean deleteFromBasket(int GoodCount, int id_Basket) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("DELETE from basketitems_tmp WHERE GoodCount = '"+GoodCount+"' AND IdBasket = '"+id_Basket+"'");
		try {
		if(ps.executeUpdate()>0) {
			return true;
		}
		else {

			return false;
		}
		}
		finally {
			c.close();
			ps.close();
		}
	}
	
	// проверка на авторизицию
	public static  boolean UserExist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("Username")!=null && session.getAttribute("Password")!=null) return true;
		return false;
	}
	
	// Открытие новой корзины для юзера
	public static boolean CreateBaskets(int id_user, int id_basket) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		int status = 0;
		PreparedStatement ps = c.prepareStatement("INSERT INTO `informuserbasket`(`IdUser`,`IdBasket`, `IdStatus`) VALUES ('"+id_user+"', '"+id_basket+"', '"+status+"')");
		try {
		if(ps.executeUpdate()>0) {
			return true;
		}
		
		return false;
		}
		finally {
			c.close();
			ps.close();
		}
		   
	}
	   
	// Проверяем, есть ли открытая корзина на юзера. если есть, возвращаем ее id и используем его для дальнейшей работы. 
	// Необходимо для того, чтобы не открывать для юзера больше одной корзины.
	public static int getOpenBasketId(int id_user) throws ClassNotFoundException, SQLException{
		Connection c = getConnection();
		int status = 0;
		PreparedStatement ps = c.prepareStatement("SELECT IdBasket FROM informuserbasket WHERE IdUser = '"+id_user+"' AND IdStatus = '"+status+"'");
		try {
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("IdBasket");
			return id;
		}
		else {
			
			return 0;
		}
		}
		finally {
			c.close();
			ps.close();
		}
	}
	   
	// После оформления заказа меняем статус корзины юзера, "закрывая" её.
	public static boolean changeBasketStatus(int newStatus, int id_basket) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("UPDATE informuserBasket set IdStatus = '"+newStatus+"' WHERE IdBasket = '"+id_basket+"'");
		try {
		if(ps.executeUpdate()>0) {
			return true;
		}
		else {
			
			return false;
		}  
		}
		finally {
			c.close();
			ps.close();
		}
	}

	
}


