package models;

public class Types {
	int IdType;
	String Name;

	public Types(int IdType, String Name) {;

		this.IdType = IdType;
		this.Name = Name;

	}
	public int getIdType() {
		return IdType;
	}

	public String getName() {
		return Name;
	}

	public void setIdType(int IdType) {
		this.IdType = IdType;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	@Override
	public String toString() {
		return "Type{"+ "idType='" + IdType + "'" +",name=" + Name +"}";

	}


}
