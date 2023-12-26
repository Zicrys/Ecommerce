package models;

import java.util.ArrayList;

public class Order {
	int id;
	ArrayList<String> goods;
	String fio;
	String phone;
	String status;
	
	public Order(int id, ArrayList<String> goods, String fio, String phone, String status) {
		this.id = id;
		this.goods = goods;
		this.fio = fio;
		this.phone = phone;
		this.status = status;
		
	}
	public String getFio() {
		return fio;
	}
	
	public int getId() {
		return id;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getStatus() {
		return status;
	}
	
	public ArrayList<String> getGoods() {
		return goods;
	}
}