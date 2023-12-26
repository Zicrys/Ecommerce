package models;

public class Model {
	int idModel;
	String Name;

	public Model(int idModel, String Name) {;

		this.idModel = idModel;
		this.Name = Name;

	}
	public int getidModel() {
		return idModel;
	}

	public String getName() {
		return Name;
	}

	public void setIdModel(int idModel) {
		this.idModel = idModel;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	@Override
	public String toString() {
		return "Model{"+ "idModel='" + idModel + "'" +",name=" + Name +"}";

	}


}