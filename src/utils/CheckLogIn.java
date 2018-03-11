package utils;

import application.App;
import model.Admin;
import model.Professor;
import model.Student;

public class CheckLogIn {
	
	private static Admin adminLogedIn;
	private static Student studentLogedIn;
	private static Professor professorLogedIn;
	
	public static boolean checkLogIn(String korisnickoIme, String lozinka){
		for (Admin admin : App.getAdmins()){
			if (admin.getUsername().equals(korisnickoIme) && admin.getPassword().equals(lozinka)){
				adminLogedIn = admin;
				return false;
			}
		}
		
		for (Student student : App.getStudents()){
			if (student.getUsername().equals(korisnickoIme) && student.getPassword().equals(lozinka)){
				studentLogedIn = student;
				return false;
			}
		}
		
		for (Professor professor : App.getProfessors()){
			if (professor.getUsername().equals(korisnickoIme) && professor.getPassword().equals(lozinka)){
				professorLogedIn = professor;
				return false;
			}
		}
		
		return true;
	}

	public static Admin getAdminLogedIn() {
		return adminLogedIn;
	}

	public static void setAdminLogedIn(Admin adminLogedIn) {
		CheckLogIn.adminLogedIn = adminLogedIn;
	}

	public static Student getStudentLogedIn() {
		return studentLogedIn;
	}

	public static void setStudentLogedIn(Student studentLogedIn) {
		CheckLogIn.studentLogedIn = studentLogedIn;
	}

	public static Professor getProfessorLogedIn() {
		return professorLogedIn;
	}

	public static void setProfessorLogedIn(Professor professorLogedIn) {
		CheckLogIn.professorLogedIn = professorLogedIn;
	}
}
