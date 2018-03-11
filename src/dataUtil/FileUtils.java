package dataUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Admin;
import model.Exam;
import model.PassedSubject;
import model.Payment;
import model.Professor;
import model.Student;
import model.Subject;
import model.Writable;

public class FileUtils {

	private static ArrayList<String> readLines(String filename) {

		try {
			String path = getRelativePath(filename);
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("File '" + filename + "' does not exist!");
				return null;
			}

			BufferedReader in = new BufferedReader(new FileReader(path));
			String line;
			ArrayList<String> ret = new ArrayList<>();
			while ((line = in.readLine()) != null) { // common pattern; Python's 'readlines' function
				ret.add(line.trim());
			}
			in.close();
			return ret;

		} catch (Exception e) {
			System.out.println("Something went wrong with file " + filename + "!");
			e.printStackTrace();
			return null;
		}
	}

	public static String getRelativePath(String fileName) {
		return "src" + File.separator + "data" + File.separator + fileName + ".txt";
	}

//	Writing to file is generic, so just this one method is used for all entities. 
	public static void writeToFile(ArrayList<? extends Writable> writable, String filename) {
		String path = getRelativePath(filename);
		File f = new File(path);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(path));
			for (Writable w : writable)
				out.println(w.toFile());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Exam> readExams() {
		ArrayList<Exam> ret = new ArrayList<>();
		ArrayList<String> lines = readLines("exams");
		for (String line : lines) {
			ret.add(FactoryUtils.makeExam(line));
		}
		return ret;
	}

	public static ArrayList<Professor> readProfessors() {
		ArrayList<Professor> ret = new ArrayList<>();
		ArrayList<String> lines = readLines("professors");
		for (String line : lines) {
			ret.add(FactoryUtils.makeProfessor(line));
		}
		return ret;
	}

	public static ArrayList<Subject> readSubjects() {
		ArrayList<Subject> ret = new ArrayList<>();
		ArrayList<String> lines = readLines("subjects");
		for (String line : lines) {
			ret.add(FactoryUtils.makeSubject(line));
		}
		return ret;
	}
	
	public static ArrayList<Student> readStudents() {
		ArrayList<Student> ret = new ArrayList<>();
		ArrayList<String> lines = readLines("students");
		for (String line : lines) {
			ret.add(FactoryUtils.makeStudent(line));
		}
		return ret;
	}
	
	public static ArrayList<Admin> readAdmins() {
		ArrayList<Admin> ret = new ArrayList<>();
		ArrayList<String> lines = readLines("admins");
		for (String line : lines) {
			ret.add(FactoryUtils.makeAdmin(line));
		}
		return ret;
	}
	
	public static ArrayList<PassedSubject> readPassedSubjects() {
		ArrayList<PassedSubject> ret = new ArrayList<>();
		ArrayList<String> lines = readLines("passedExams");
		for (String line : lines) {
			ret.add(FactoryUtils.makePassedSubject(line));
		}
		return ret;
	}

	public static ArrayList<Payment> readPayments() {
		ArrayList<Payment> ret = new ArrayList<>();
		ArrayList<String> lines = readLines("payments");
		for (String line : lines) {
			ret.add(FactoryUtils.makePayment(line));
		}
		return ret;
	}
}
