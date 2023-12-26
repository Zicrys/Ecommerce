package models;

public class Goods {
	private int id;
	private String photo;
	private int price;
	private String Type;
	private String Mark;
	private String Model;
	private int Count;
	
	public Goods(int id, String photo, String Type, String Mark, String Model, int price, int Count) {
		this.id = id;
		this.photo = photo;
		this.price = price;
		this.Type = Type;
		this.Mark = Mark;
		this.Model = Model;
		this.Count = Count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
	
	public String getMark() {
		return Mark;
	}

	public void setMark(String Mark) {
		this.Mark = Mark;
	}
	public String getModel() {
		return Model;
	}
	
	public void setModel(String Model) {
		this.Model = Model;
	}


	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public int getCount() {
		return Count;
	}

	public void setCount(int Count) {
		this.Count = Count;
	}
	
	@Override
	public String toString() {
		
		return "Goods{"+
				"id="+id+
				",photo='"+photo+
				",type='"+Type+
				",mark='"+Mark+
				",model='"+Model+
				"',price="+price+"}";
	}
}