package application;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import dataUtil.FactoryUtils;
import dataUtil.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Admin;
import model.Exam;
import model.Payment;
import model.Professor;
import model.Student;
import model.Subject;
import utils.SearchUtils;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class App extends Application {
	private static ArrayList<Admin> admins = new ArrayList<Admin>();
	private static ArrayList<Professor> professors = new ArrayList<Professor>();
	private static ArrayList<Student> students = new ArrayList<Student>();
	private static ArrayList<Exam> exams = new ArrayList<Exam>();
	private static ArrayList<Subject> subjects = new ArrayList<Subject>();
	private static ArrayList<Payment> payments = new ArrayList<Payment>();
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static ArrayList<Admin> getAdmins() {
		return admins;
	}

	public static void setAdmins(ArrayList<Admin> admins) {
		App.admins = admins;
	}

	public static ArrayList<Professor> getProfessors() {
		return professors;
	}

	public static void setProfessors(ArrayList<Professor> professors) {
		App.professors = professors;
	}

	public static ArrayList<Student> getStudents() {
		return students;
	}

	public static void setStudents(ArrayList<Student> students) {
		App.students = students;
	}

	public static ArrayList<Exam> getExams() {
		return exams;
	}

	public static void setExams(ArrayList<Exam> exams) {
		App.exams = exams;
	}

	public static ArrayList<Subject> getSubjects() {
		return subjects;
	}

	public static void setSubjects(ArrayList<Subject> subjects) {
		App.subjects = subjects;
	}

	public static ArrayList<Payment> getPayments() {
		return payments;
	}

	public static void setPayments(ArrayList<Payment> payments) {
		App.payments = payments;
	}

	public void professorMenu(){
		System.out.println("Izaberite jednu od sledeæih opcija:");
		System.out.println(" 1 - Pregled prijavljenih ispita");
		System.out.println(" 2 - Unos rezultata ispita");
		System.out.println(" 3 - Upis ispita");
		System.out.println(" 4 - Izlazak iz aplikacije");
		Scanner optionScan = new Scanner(System.in);
		int option = FactoryUtils.checkInputInt(optionScan);
		if (option == 1) {
			FactoryUtils.examsEntryForProfessor();
			professorMenu();
		}
		else if (option == 2) {
			FactoryUtils.marks();
			professorMenu();
		}
		else if (option == 3) {
			FactoryUtils.unesiIspit();
			professorMenu();
		}
		else if (option == 4) {
			System.exit(0);
		}
		else {
			System.out.println("Pogrešan unos!");
			professorMenu();
		}
	}
	
	public void studentMenu(){
		System.out.println("Izaberite jednu od sledeæih opcija:");
		System.out.println(" 1 - Prijava ispita");
		System.out.println(" 2 - Odjava ispita");
		System.out.println(" 3 - Prikaz položenih predmeta s prosjeènom ocjenom");
		System.out.println(" 4 - Prikaz prijavljenih ispita i rezultata ispita");
		System.out.println(" 5 - Izlazak iz aplikacije");
		Scanner optionScan = new Scanner(System.in);
		int option = FactoryUtils.checkInputInt(optionScan);
		if (option == 1) {
			FactoryUtils.prijaviIspit();
			studentMenu();
		}
		else if (option == 2) {
			FactoryUtils.odjavaIspita();
			studentMenu();
		}
		else if (option == 3) {
			FactoryUtils.passedSubjects();
			studentMenu();
		}
		else if (option == 4) {
			FactoryUtils.examsEntry();
			studentMenu();
		}
		else if (option == 5) {
			System.exit(0);
		}
		else {
			System.out.println("Pogrešan unos!");
			studentMenu();
		}
	}
	
	private Scanner lastNameScan;
	
	public void adminMenu(){
		System.out.println("Izaberite jednu od sledeæih opcija:");
		System.out.println(" 1 - Upis studenata na prvu godinu studija");
		System.out.println(" 2 - Upis nastavnika");
		System.out.println(" 3 - Upis predmeta");
		System.out.println(" 4 - Evidencija finansijskih uplata studenata");
		System.out.println(" 5 - Pronalaženje studenta po broju indeksa");
		System.out.println(" 6 - Pretraživanje studenata po prezimenu");
		System.out.println(" 7 - Pregledanje svih studenata sortiranih po prezimenu");
		System.out.println(" 8 - Izlazak iz aplikacije");
		Scanner optionScan = new Scanner(System.in);
		int option = FactoryUtils.checkInputInt(optionScan);
		if (option == 1) {
			FactoryUtils.unesiStudenta();
			adminMenu();
		}
		else if (option == 2) {
			FactoryUtils.unesiProfesora();
			adminMenu();
		}
		else if (option == 3) {
			FactoryUtils.unesiPredmet();
			adminMenu();
		}
		
		else if (option == 4) {
			FactoryUtils.payments();
			adminMenu();
		}
		else if (option == 5) {
			System.out.println("Unesite broj indeksa:");
			Scanner indexScan = new Scanner(System.in);
			int index = FactoryUtils.checkInputInt(indexScan);
			Student s = SearchUtils.findStudentByIndex(index);
			if (s == null) {
				System.out.println("Ne postoji student s navedenim brojem indeksa.");
			}
			else {
				System.out.println(SearchUtils.findStudentByIndex(index));
			}
			adminMenu();
		}
		else if (option == 6) {
			System.out.println("Unesite prezime:");
			lastNameScan = new Scanner(System.in);
			String lastName = FactoryUtils.checkInput(lastNameScan.nextLine());
			String s = SearchUtils.findStudentByLastName(lastName);
			if (s == null) {
				System.out.println("Ne postoji student s navedenim prezimenom.");
			}
			else {
				System.out.println(SearchUtils.findStudentByLastName(lastName));
			}
			adminMenu();
		}
		else if (option == 7) {
			SearchUtils.sortStudentsByLastName();
			adminMenu();
		}
		else if (option == 8) {
			System.exit(0);
		}
		else {
			System.out.println("Pogrešan unos!");
			adminMenu();
		}
	}
	
	private void readAllData() {
		professors = FileUtils.readProfessors();
		subjects = FileUtils.readSubjects();
		exams = FileUtils.readExams();
		students = FileUtils.readStudents();
		admins = FileUtils.readAdmins();
		payments = FileUtils.readPayments();
	}
	
	private Stage primaryStage;
	private AnchorPane mainLayout;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		readAllData();
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Studentska služba");
		showMainView();
	}
	
	private void showMainView() throws IOException{
		mainLayout = FXMLLoader.load(getClass().getResource("MainView.fxml"));
		Scene scene = new Scene(mainLayout);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
