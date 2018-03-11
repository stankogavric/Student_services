package model;

public class PassedSubject implements Writable{

	private int grade;
	private Subject subject;

	public PassedSubject(int grade, Subject subject) {
		super();
		this.grade = grade;
		this.subject = subject;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Subject getSubject() {
		return subject;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return subject.getName() + " (Ocjena: " + grade + ")";
	}
	
	@Override
	public String toFile() {
		return getGrade() + "!" + subject.getName();
	}

}
