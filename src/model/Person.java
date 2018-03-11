package model;

public abstract class Person implements Writable {

	private int id;
	private String jmbg;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	
	public int getId() {
		return id;
	}

	public Person(int id, String jmbg, String firstName, String lastName, String username, String password) {
		super();
		this.id = id;
		this.jmbg = jmbg;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public void setId(int uid) {
		this.id = uid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getJmbg() {
		return jmbg;
	}
	
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", jmbg=" + jmbg + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", username=" + username + ", password=" + password + "]";
	}
	
	@Override
	public String toFile() {
		return null;
	}

}
