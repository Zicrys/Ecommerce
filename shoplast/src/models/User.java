package models;

public class User {
	private int id;
	private String fio;
	private String username;
	private String phone;
	
	public User(int id, String username, String fio, String phone) {
		this.id = id;
		this.username = username;
		this.fio = fio;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFio() {
		return fio;
	}

	public void setFoi(String fio) {
		this.fio = fio;
	}
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		
		return "User{"+
				"id="+id+
				",fio='"+fio+
				",username='"+username+
				",phone='"+phone+"}";
	}
}