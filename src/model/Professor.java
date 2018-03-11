package model;

public class Professor extends Person {

	private String department;
	private ProfessorStatus status;

	public enum ProfessorStatus {
		FULL_PROFESSOR, ASSOCIATE_PROFESSOR, DOCENT
	}

	public Professor(int id, String jmbg, String firstName, String lastName, String username, String password,
			String department, ProfessorStatus status) {
		super(id, jmbg, firstName, lastName, username, password);
		this.department = department;
		this.status = status;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public ProfessorStatus getStatus() {
		return status;
	}

	public void setStatus(ProfessorStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Professor [department=" + department + ", status=" + status + "]";
	}

	@Override
	public String toFile() {
		return getId() + "|" + getJmbg() + "|" + getFirstName() + "|" + getLastName() + "|" + 
				getUsername() + "|" + getPassword() + "|" + getDepartment() + "|" + getStatus();
	}

}
