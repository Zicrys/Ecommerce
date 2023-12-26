package models;

public class OrderItems {
	int Price;
	int count;
	String title;

	public OrderItems(String title, int count, int Price) {;

		this.Price = Price;
		this.count = count;
		this.title = title;


	}
	



	public int getPrice() {
		return Price;
	}


	public int getcount() {
		return count;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getTitle() {
		return title;
	}

	//�������
	public void setPrice(int Price) {
		this.Price = Price;
	}
	
	
	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		return "Basket{"+ "title='" + title + "'" +",price=" + Price + ",count=" + count +"}";

	}

}
