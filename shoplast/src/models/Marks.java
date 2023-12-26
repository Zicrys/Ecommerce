package models;

public class Marks {
	int idMark;
	String Name;

	public Marks(int idMark, String Name) {;

		this.idMark = idMark;
		this.Name = Name;

	}
	public int getidMark() {
		return idMark;
	}

	public String getName() {
		return Name;
	}

	public void setIdMark(int idMark) {
		this.idMark = idMark;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	@Override
	public String toString() {
		return "Mark{"+ "idMark='" + idMark + "'" +",name=" + Name +"}";

	}


}
