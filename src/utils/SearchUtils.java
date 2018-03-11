package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.App;
import model.Exam;
import model.Professor;
import model.Student;
import model.Subject;

public class SearchUtils {

	public static Subject findSubjectByName(String name) {
		if(name == null)
			return null;
		for (Subject s : App.getSubjects()) {
			if (s.getName().toUpperCase().equals(name.toUpperCase()))
				return s;
		}
		return null;
	}

	public static Professor findProfessorById(int id) {
		for (Professor prof : App.getProfessors()) {
			if (prof.getId() == id)
				return prof;
		}
		return null;
	}
	
	public static Student findStudentByIndex(int index){
		for (Student student : App.getStudents()){
			if (student.getIndex() == index)
				return student;
		}
		return null;
	}
	
	public static String findStudentByLastName(String lastName){
		for (Student student : App.getStudents()){
			if (student.getLastName().toUpperCase().contains((lastName.toUpperCase())))
				return student.getFirstName() + " " + student.getLastName() + " " + student.getIndex()
				+ " " + student.getDepartment();
		}
		return null;
	}
	
	public static void sortStudentsByLastName(){
		 ArrayList<Student> students = App.getStudents();
		 int ind = 0;
		 Collections.sort(students, new Comparator<Student>() {
		        public int compare(Student student2, Student student1)
		        {
		            return  student2.getLastName().toUpperCase().compareTo(student1.getLastName().toUpperCase());
		        }
		    });
		 for (Student s : students){
			 if (s == null)
				 continue;
			 System.out.println(s);
			 ind++;
		 }
		 if (ind == 0) {
			System.out.println("Nema studenata.");
		}
	}
	
	public static Exam findExamBySubject(String s){
		for (Exam e : App.getExams()) {
			if (e.getSubject().getName().equals(s))
				return e;
		}
		return null;
	}
}
