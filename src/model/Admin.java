package model;

public class Admin extends Person {

	public Admin(int id, String jmbg, String firstName, String lastName, String username, String password) {
		super(id, jmbg, firstName, lastName, username, password);
	}
	
	@Override
	public String toFile() {
		return getId() + "|" + getJmbg() + "|" + getFirstName() + "|" + getLastName() + "|" + 
				getUsername() + "|" + getPassword();
	}
}
