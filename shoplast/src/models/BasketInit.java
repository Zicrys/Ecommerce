package models;

public class BasketInit {
	int GoodCount;
	int IdBasket;
	int IdGood;
	int Price;
	int count;
	String status;
	String title;

	public BasketInit(int GoodCount, int IdBasket, String title, int IdGood, int Price, int count, 	String status) {
		this.GoodCount = GoodCount;
		this.IdBasket = IdBasket;
		this.IdGood = IdGood;
		this.Price = Price;
		this.count = count;
		this.title = title;
		this.status = status;
	}
	

	public int getGoodCount() {
		return GoodCount;
	}

	public int getPrice() {
		return Price;
	}

	public int getIdBasket() {
		return IdBasket;
	}
	public int getIdGood() {
		return IdGood;
	}

	public int getcount() {
		return count;
	}
	public String getStatus() {
		return status;
	}

	//�������
	public void setTitle(String title) {
		this.title = title;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	
	public String getTitle() {
		return title;
	}

	//�������
	public void setPrice(int Price) {
		this.Price = Price;
	}

	public void setGoodCount(int GoodCount) {
		this.GoodCount = GoodCount;
	}
	public void setIdBasket(int IdBasket) {
		this.IdBasket = IdBasket;
	}
	public void setIdGood(int IdGood) {
		this.IdGood = IdGood;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		return "Basket{"+ "goodCount='" + GoodCount + "'" + ",title='" + title + "'" + ",idBasket='" + IdBasket + "'"  + ",idGood='" + IdGood + "'" +",Price=" + Price + ",count=" + count +",status=" + status +"}";}

}
