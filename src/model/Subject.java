package model;

public class Subject implements Writable {

	private String name;
	private String department; // naziv studijakog smjera
	private int weekly; // fond casova
	private Professor professor;
	private int schoolYear;

	public Subject(String name, String department, int weekly, Professor professor, int schoolYear) {
		super();
		this.name = name;
		this.department = department;
		this.weekly = weekly;
		this.professor = professor;
		this.schoolYear = schoolYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getWeekly() {
		return weekly;
	}

	public void setWeekly(int weekly) {
		this.weekly = weekly;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}

	@Override
	public String toString() {
		return "Subject [name=" + name + ", department=" + department + ", weekly=" + weekly + ", professor="
				+ professor.getId() + ", schoolYear=" + schoolYear + "]";
	}

	@Override
	public String toFile() {
		return name + "|" + department + "|" + weekly + "|" + professor.getId() + "|" + schoolYear;				
	}

}
