package models;

public class OrderList {
	int id;
	String fio;
	String phone;
	String status;
	String startDate;
	String finishDate;
	
	public OrderList(String startDate, String finishDate, int id, String fio, String phone, String status) {
		this.id = id;
		this.fio = fio;
		this.phone = phone;
		this.status = status;
		this.startDate = startDate;
		this.finishDate = finishDate;
		
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
	public String getStartDate() {
		return startDate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public String toString() {
		return "OrderList{"+"startDate='" + startDate + "'" + ",finishDate='" + finishDate + "'" + ",id='" + id + "'" + ",fio='" + fio + "'" + ",phone='" + phone + "'"  + ",status='" + status + "'}";
	}

}