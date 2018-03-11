package dataUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import application.App;
import model.Address;
import model.Admin;
import model.Exam;
import model.PassedSubject;
import model.Payment;
import model.Exam.ExamType;
import model.Exam.Status;
import model.Professor;
import model.Professor.ProfessorStatus;
import model.Student;
import model.Subject;
import utils.CheckLogIn;
import utils.SearchUtils;

public class FactoryUtils {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	@SuppressWarnings("deprecation")
	public static Date pocetakIspitnogRoka = new Date(117, 05, 01); //117 je 2017. godina, a mjesec je ustvari +1
	@SuppressWarnings("deprecation")
	public static Date krajIspitnogRoka = new Date(117, 05, 30); //117 je 2017. godina, a mjesec je ustvari +1

	public static Professor makeProfessor(String line) {
		if (line.equals("") || line.equals("null")){
			return null;
		}
		String[] data = line.split("\\|");
		int id = Integer.parseInt(data[0]);
		String jmbg = data[1];
		String firstName = data[2];
		String lastName = data[3];
		String username = data[4];
		String password = data[5];
		String department = data[6];
		ProfessorStatus status = ProfessorStatus.valueOf(data[7]);
		return new Professor(id, jmbg, firstName, lastName, username, password, department, status);
	}
	
	public static Admin makeAdmin(String line) {
		if (line.equals("") || line.equals("null")){
			return null;
		}
		String[] data = line.split("\\|");
		int id = Integer.parseInt(data[0]);
		String jmbg = data[1];
		String firstName = data[2];
		String lastName = data[3];
		String username = data[4];
		String password = data[5];
		return new Admin(id, jmbg, firstName, lastName, username, password);
	}
	
	public static Student makeStudent(String line) {
		if (line.equals("") || line.equals("null")){
			return null;
		}
		String[] data = line.split("\\|");
		int id = Integer.parseInt(data[0]);
		String jmbg = data[1];
		String firstName = data[2];
		String lastName = data[3];
		String username = data[4];
		String password = data[5];
		int index = Integer.parseInt(data[6]);
		double gpa = Double.parseDouble(data[7]);
		int schoolYear = Integer.parseInt(data[8]);
		String parent = data[9];
		String email = data[10];
		String place = data[11];
		String street = data[12];
		int streetNo = Integer.parseInt(data[13]);
		Address address = new Address (place, street, streetNo);
		double account = Double.parseDouble(data[14]);
		ArrayList<Exam> examEntry = new ArrayList<Exam>();
		
		String examEntryString = data[15].replace("[","").replace("]","").replace(" ","");
		String[] examEntryTemp = examEntryString.split(",");
		if(examEntryTemp.length>0){
			for (String s : examEntryTemp){
				if(s == null)
					continue;
				examEntry.add(SearchUtils.findExamBySubject(s));
			}
		}
		ArrayList<PassedSubject> passedSubjects = new ArrayList<PassedSubject>();
		String passedSubjectString = data[16].replace("[","").replace("]","").replace(" ","");
		String[] passedSubjectTemp = passedSubjectString.split(",");
		if (passedSubjectTemp.length>0){
			for (String s : passedSubjectTemp){
				 passedSubjects.add(makePassedSubject(s));
			}
		}
		String department = data[17];
		Date date = null;
		try {
			date = sdf.parse(data[18]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String telephone = data[19];
		return new Student(id, jmbg, firstName, lastName, username, password, index, gpa, 
				schoolYear, parent, email, address, account, examEntry, passedSubjects, 
				department, date, telephone);
	}

	public static Exam makeExam(String line) {
		if (line.equals("") || line.equals("null")){
			return null;
		}
		String[] data = line.split("\\!");
		int id = Integer.parseInt(data[0]);
		ExamType type = ExamType.valueOf(data[1]);
		Status status = Exam.Status.valueOf(data[2]);
		Date date = null;
		try {
			date = sdf.parse(data[3]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String classroom = data[4];
		Subject subject = SearchUtils.findSubjectByName(data[5]);
		return new Exam(id, type, status, date, classroom, subject);
	}
	
	public static Payment makePayment(String line) {
		if (line.equals("") || line.equals("null")){
			return null;
		}
		String[] data = line.split("\\|");
		double amount = Double.parseDouble(data[0]);
		int index = Integer.parseInt(data[1]);
		Date date = null;
		try {
			date = sdf.parse(data[2]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Subject subject = SearchUtils.findSubjectByName(data[3]);
		return new Payment(amount, index, date, subject);
	}

	public static Subject makeSubject(String line) {
		if (line.equals("") || line.equals("null")){
			return null;
		}
		String[] data = line.split("\\|");
		String name = data[0].trim();
		String department = data[1].trim();
		int weekly = Integer.parseInt(data[2].trim()); 
		Professor professor = SearchUtils.findProfessorById(Integer.parseInt(data[3].trim()));
		int schoolYear = Integer.parseInt(data[4].trim());
		return new Subject(name, department, weekly, professor, schoolYear);
	}
	
	public static PassedSubject makePassedSubject(String line) {
		if (line.equals("") || line.equals("null")){
			return null;
		}
		String[] data = line.split("\\!");
		int grade = Integer.parseInt(data[0].trim());
		Subject subject = SearchUtils.findSubjectByName(data[1].trim());
		return new PassedSubject(grade, subject);
	}
	
	private static Scanner firstNameScan;
	private static Scanner lastNameScan;
	private static Scanner usernameScan;
	private static Scanner passwordScan;
	private static Scanner departmentScan;
	private static Scanner jmbgScan;
	
	public static void unesiProfesora(){
		String jmbg = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		String password = null;
		String department = null;
		ProfessorStatus status = null;
		
		System.out.println("Podaci o profesoru:");
		
		int id = App.getProfessors().size() + 1;
		
		boolean y = true;
		jmbgScan = new Scanner(System.in);
		while (y){
			System.out.println("JMBG: ");
			jmbg = jmbgScan.nextLine().trim();
			if (!jmbg.matches("[0-9]{13}"))
				System.out.println("Neispravan JMBG.");
			else {
				y = false;
			}
		}

		firstNameScan = new Scanner(System.in);
		System.out.println("Ime: ");
		firstName = checkInput(firstNameScan.nextLine().trim());
		
		lastNameScan = new Scanner(System.in);
		System.out.println("Prezime: ");
		lastName = checkInput(lastNameScan.nextLine().trim());
		
		usernameScan = new Scanner(System.in);
		System.out.println("Korisnièko ime: ");
		username = checkInputString(usernameScan.nextLine().trim());
		
		passwordScan = new Scanner(System.in);
		System.out.println("Lozinka: ");
		password = checkInputString(passwordScan.nextLine().trim());
		
		departmentScan = new Scanner(System.in);
		System.out.println("Smjer: ");
		department = checkInput(departmentScan.nextLine().trim());
		
		boolean x = true;
		Scanner typeOptionScan = new Scanner(System.in);
		while (x){
			System.out.println("-Zvanje-");
			System.out.println("Izaberite jednu od sledeæih opcija:");
			System.out.println(" 1 - Docent");
			System.out.println(" 2 - Vanredni profesor");
			System.out.println(" 3 - Redovni profesor");
			int typeOption = FactoryUtils.checkInputInt(typeOptionScan);
			if (typeOption == 1) {
				status = ProfessorStatus.DOCENT;
				x = false;
			}
			else if (typeOption == 2) {
				status = ProfessorStatus.ASSOCIATE_PROFESSOR;
				x = false;
			}
			else if (typeOption == 3) {
				status = ProfessorStatus.FULL_PROFESSOR;
				x = false;
			}
			else {
				System.out.println("Pogrešan unos!");
			}
		}
		
		ArrayList<Professor> professors = App.getProfessors();
		professors.add(new Professor(id, jmbg, firstName, lastName, username, password, department, status));
		FileUtils.writeToFile(professors, "professors");
		System.out.println("Uspiješno ste unijeli profesora.");
	}
	
	private static Scanner nameScan;
	private static Scanner departmentScan2;
	
	public static void unesiPredmet(){
		String name = null;
		int weekly;
		Professor professor = null;
		String department = null;
		int schoolYear;
		
		if(App.getProfessors().isEmpty()){
			System.out.println("Ne možete unijeti predmet jer nema nijednog profesora koji "
					+ "bi mogao predavati.");
			return;
		}
		
		System.out.println("Podaci o predmetu:");
		
		nameScan = new Scanner(System.in);
		System.out.println("Naziv: ");
		name = checkInputString(nameScan.nextLine().trim());
		
		departmentScan2 = new Scanner(System.in);
		System.out.println("Smjer: ");
		department = checkInput(departmentScan2.nextLine().trim());
		
		Scanner weeklyScan = new Scanner(System.in);
		System.out.println("Fond èasova: ");
		weekly = FactoryUtils.checkInputInt(weeklyScan);
		
		while(professor == null){
			Scanner professorScan = new Scanner(System.in);
			System.out.println("ID profesora: ");
			professor = SearchUtils.findProfessorById(FactoryUtils.checkInputInt(professorScan));
			if(professor == null)
				System.out.println("Ne postoji profesor s navedenim ID-om.");
		}
		
		Scanner schoolYearScan = new Scanner(System.in);
		System.out.println("Školska godina: ");
		schoolYear = FactoryUtils.checkInputInt(schoolYearScan);
		
		ArrayList<Subject> subjects = App.getSubjects();
		subjects.add(new Subject(name, department, weekly, professor, schoolYear));
		FileUtils.writeToFile(subjects, "subjects");
		System.out.println("Uspiješno ste unijeli predmet.");
	}
	
	private static Scanner jmbgScan2;
	private static Scanner firstNameScan2;
	private static Scanner lastNameScan2;
	private static Scanner usernameScan2;
	private static Scanner passwordScan2;
	private static Scanner parentScan;
	private static Scanner emailScan;
	private static Scanner placeScan;
	private static Scanner streetScan;
	private static Scanner namePassedSubjectScan;
	private static Scanner departmentScan3;
	private static Scanner dateScan;
	private static Scanner telephoneScan;
	
	public static void unesiStudenta(){
		String jmbg = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		String password = null;
		int index;
		double gpa = 0;
		int schoolYear;
		String parent;
		String email;
		String place;
		String street;
		int streetNo;
		Address address;
		double account = 0;
		ArrayList<Exam> examEntry = new ArrayList<Exam>();
		int option;
		int option2;
		Subject subject = null;
		int grade;
		PassedSubject passedSubject;
		ArrayList<PassedSubject> passedSubjects = new ArrayList<PassedSubject>();
		String department;
		Date date = null;
		String telephone;
		
		System.out.println("Podaci o studentu:");
		
		int id = App.getStudents().size() + 1;
		
		boolean z = true;
		while (z){
			jmbgScan2 = new Scanner(System.in);
			System.out.println("JMBG: ");
			jmbg = jmbgScan2.nextLine().trim();
			if (!jmbg.matches("[0-9]{13}"))
				System.out.println("Neispravan JMBG.");
			else {
				z = false;
			}
		}
		
		firstNameScan2 = new Scanner(System.in);
		System.out.println("Ime: ");
		firstName = checkInput(firstNameScan2.nextLine().trim());
		
		lastNameScan2 = new Scanner(System.in);
		System.out.println("Prezime: ");
		lastName = checkInput(lastNameScan2.nextLine().trim());
		
		usernameScan2 = new Scanner(System.in);
		System.out.println("Korisnièko ime: ");
		username = checkInputString(usernameScan2.nextLine().trim());
		
		passwordScan2 = new Scanner(System.in);
		System.out.println("Lozinka: ");
		password = checkInputString(passwordScan2.nextLine().trim());
		
		Scanner indexScan = new Scanner(System.in);
		System.out.println("Broj indeksa: ");
		index = FactoryUtils.checkInputInt(indexScan);
		
		Scanner gpaScan = new Scanner(System.in);
		System.out.println("Prosjeèna ocjena: ");
		gpa = FactoryUtils.checkInputDouble(gpaScan);
		
		Scanner schoolYearScan = new Scanner(System.in);
		System.out.println("Školska godina: ");
		schoolYear = FactoryUtils.checkInputInt(schoolYearScan);
		
		parentScan = new Scanner(System.in);
		System.out.println("Ime jednog roditelja: ");
		parent = checkInput(parentScan.nextLine().trim());
		
		emailScan = new Scanner(System.in);
		System.out.println("E-mail: ");
		email = checkInputString(emailScan.nextLine().trim());
		
		placeScan = new Scanner(System.in);
		System.out.println("Mjesto stanovanja: ");
		place = checkInput(placeScan.nextLine().trim());
		
		streetScan = new Scanner(System.in);
		System.out.println("Ulica: ");
		street = checkInput(streetScan.nextLine().trim());
		
		Scanner streetNoScan = new Scanner(System.in);
		System.out.println("Broj ulice: ");
		streetNo = FactoryUtils.checkInputInt(streetNoScan);
		
		address = new Address(place, street, streetNo);
		
		System.out.println("-Položeni predmeti-");
		boolean x = true;
		while (x){
			System.out.println("Izaberite jednu od sledeæih opcija:");
			System.out.println(" 1 - Unos položenih predmeta");
			System.out.println(" 2 - Student nema položenih predmeta");
			Scanner optionScan = new Scanner(System.in);
			option = FactoryUtils.checkInputInt(optionScan);
			if (option == 1){
				boolean y = true;
				while (y){
					while(subject == null){
						namePassedSubjectScan = new Scanner(System.in);
						System.out.println("Naziv položenog predmeta: ");
						subject = SearchUtils.findSubjectByName(checkInputString(namePassedSubjectScan.nextLine().trim()));
						if(subject == null)
							System.out.println("Ne postoji predmet s navedenim nazivom");
					}
					Scanner gradeScan = new Scanner(System.in);
					System.out.println("Ocjena: ");
					grade = FactoryUtils.checkInputInt(gradeScan);
					passedSubject = new PassedSubject(grade, subject);
					passedSubjects.add(passedSubject);
					System.out.println("Predmet je uspiješno unesen");
					System.out.println("Izaberite jednu od sledeæih opcija:");
					System.out.println(" 1 - Unos položenih predmeta");
					System.out.println(" 2 - Student nema više položenih predmeta");
					Scanner option2Scan = new Scanner(System.in);
					option2 = FactoryUtils.checkInputInt(option2Scan);
					if (option2 == 2){
						y = false;
						x = false;
					}
					else if (option2 == 1){
						
					}
					else {
						System.out.println("Pogrešan unos!");
					}
				}
			}
			else if (option == 2){
				x = false;
			}
			else {
				System.out.println("Pogrešan unos!");
			}
		}
		
		departmentScan3 = new Scanner(System.in);
		System.out.println("Smjer: ");
		department = checkInput(departmentScan3.nextLine().trim());
		
		dateScan = new Scanner(System.in);
		System.out.println("Datum roðenja: ");
		boolean pom = true;
		while(pom){
			try {
				date = sdf.parse(checkInputString(dateScan.nextLine().trim()));
				pom = false;
			} catch (ParseException e) {
				System.out.println("Greška! Unesite datum roðenja u formatu DD.MM.GGGG.");
			}
		}
		
		telephoneScan = new Scanner(System.in);
		System.out.println("Broj telefona: ");
		telephone = checkInputString(telephoneScan.nextLine().trim());
		
		ArrayList<Student> students = App.getStudents();
		students.add(new Student(id, jmbg, firstName, lastName, username, password, index, gpa,
				schoolYear, parent, email, address, account, examEntry, passedSubjects, 
				department, date, telephone));
		FileUtils.writeToFile(students, "students");
		System.out.println("Uspiješno ste unijeli studenta.");
	}
	
	private static Scanner classroomScan;
	private static Scanner subjectScan;
	private static Scanner dateScan2;
	
	public static void unesiIspit(){
		ExamType type = null;
		Status status = null;
		String classroom;
		Subject subject = null;
		Date date = null;
		
		Date dateNow = new Date();
		if(dateNow.before(pocetakIspitnogRoka) || dateNow.after(krajIspitnogRoka)){
			System.out.println("Ne postoji ispitni rok.");
			return;
		}
		
		System.out.println("Podaci o ispitu:");
		
		int id = App.getExams().size() + 1;
		
		boolean x = true;
		while (x){
			System.out.println("-Tip ispita-");
			System.out.println("Izaberite jednu od sledeæih opcija:");
			System.out.println(" 1 - Usmeno");
			System.out.println(" 2 - Pismeno");
			System.out.println(" 3 - Praktièno");
			Scanner typeOptionScan = new Scanner(System.in);
			int typeOption = FactoryUtils.checkInputInt(typeOptionScan);
			if (typeOption == 1) {
				type = ExamType.ORAL_EXAM;
				x = false;
			}
			else if (typeOption == 2) {
				type = ExamType.TEST;
				x = false;
			}
			else if (typeOption == 3) {
				type = ExamType.PRACTISE;
				x = false;
			}
			else {
				System.out.println("Pogrešan unos!");
			}
		}
		
		boolean y = true;
		while (y){
			System.out.println("-Status ispita-");
			System.out.println("Izaberite jednu od sledeæih opcija:");
			System.out.println(" 1 - Redovan");
			System.out.println(" 2 - Vanredni");
			Scanner statusOptionScan = new Scanner(System.in);
			int statusOption = FactoryUtils.checkInputInt(statusOptionScan);
			if (statusOption == 1) {
				status = Status.REGULAR;
				y = false;
			}
			else if (statusOption == 2) {
				status = Status.NOT_REGULAR;
				y = false;
			}
			else {
				System.out.println("Pogrešan unos!");
			}
		}
		
		classroomScan = new Scanner(System.in);
		System.out.println("Uèionica: ");
		classroom = checkInputString(classroomScan.nextLine().trim());
		
		while(subject == null){
			subjectScan = new Scanner(System.in);
			System.out.println("Predmet: ");
			subject = SearchUtils.findSubjectByName(checkInputString(subjectScan.nextLine().trim()));
			if(subject == null)
				System.out.println("Ne postoji predmet s navedenim nazivom");
		}
		dateScan2 = new Scanner(System.in);
		System.out.println("Datum polaganja ispita: ");
		boolean pom = true;
		while(pom){
			try {
				date = sdf.parse(checkInputString(dateScan2.nextLine().trim()));
				pom = false;
			} catch (ParseException e) {
				System.out.println("Greška! Unesite datum polaganja ispita u formatu DD.MM.GGGG.");
			}
		}
		
		ArrayList<Exam> exams = App.getExams();
		exams.add(new Exam(id, type, status, date, classroom, subject));
		FileUtils.writeToFile(exams, "exams");
		System.out.println("Uspiješno ste unijeli ispit.");
	}
	
	public static void prijaviIspit(){
		ArrayList<Exam> exams = App.getExams();
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		Date dateTemp = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		@SuppressWarnings("deprecation")
		Date datePayment = new Date(date.getYear(), date.getMonth(), date.getDate());
		int i = 0;
		for (Exam e : exams){ 
			boolean ind = false;
			if (e.getSubject().getSchoolYear() == logedInStudent.getSchoolYear() &&
					e.getSubject().getDepartment().equals(logedInStudent.getDepartment()) &&
					e.getDate().after(dateTemp)){
				for (PassedSubject ps : passedSubjects) {
					if (ps == null)
						continue;
					if(ps.getSubject().equals(e.getSubject()))
						ind = true;
				}
				for (Exam e2 : logedInStudent.getExamEntry()) {
					if (e2 == null)
						continue;
					if(e2.getSubject().equals(e.getSubject()))
						ind = true;
				}
				if(ind)
					continue;
				i++;
				System.out.println(i + ". " + e.getSubject().getName());
			}
		}
		if (i == 0){
			System.out.println("Nema ispita za prijavljivanje.");
			return;
		}
		else {
			System.out.println("Izaberite predmet koji želite polagati");
		}
		Scanner examOptionScan = new Scanner(System.in);
		int examOption = FactoryUtils.checkInputInt(examOptionScan);
		if(examOption <1 || examOption >i){
			System.out.println("Pogrešan unos.");
			prijaviIspit();
		}
		i = 0;
		for (Exam e1 : exams){
			boolean ind = false;
			if (e1.getSubject().getSchoolYear() == logedInStudent.getSchoolYear() &&
					e1.getSubject().getDepartment().equals(logedInStudent.getDepartment()) &&
					e1.getDate().after(dateTemp)){
				for (PassedSubject ps : passedSubjects) {
					if (ps == null)
						continue;
					if(ps.getSubject().equals(e1.getSubject()))
						ind = true;
				}
				for (Exam e2 : logedInStudent.getExamEntry()) {
					if (e2 == null)
						continue;
					if(e2.getSubject().equals(e1.getSubject()))
						ind = true;
				}
				if(ind)
					continue;
				i++;
				if(i == examOption){
					Exam selectedExam = e1;
					if (selectedExam.getStatus().equals(Status.REGULAR)){
						if(logedInStudent.getAccount() == 200 || logedInStudent.getAccount() > 200){
							logedInStudent.setAccount(logedInStudent.getAccount() - 200);
							logedInStudent.getExamEntry().add(selectedExam);
							ArrayList<Student> students = App.getStudents();
							FileUtils.writeToFile(students, "students");
							ArrayList<Payment> payments = App.getPayments();
							payments.add(new Payment(200, logedInStudent.getIndex(), datePayment, selectedExam.getSubject()));
							FileUtils.writeToFile(payments, "payments");
							System.out.println("Uspiješno ste prijavili ispit.");
						}
						else {
							System.out.println("Ne možete prijaviti ispit, nedovoljan iznos na raèunu.");
						}
					}
					else {
						if(logedInStudent.getAccount() == 400 || logedInStudent.getAccount() > 400){
							logedInStudent.setAccount(logedInStudent.getAccount() - 400);
							logedInStudent.getExamEntry().add(selectedExam);
							ArrayList<Student> students = App.getStudents();
							FileUtils.writeToFile(students, "students");
							ArrayList<Payment> payments = App.getPayments();
							payments.add(new Payment(400, logedInStudent.getIndex(), datePayment, selectedExam.getSubject()));
							FileUtils.writeToFile(payments, "payments");
							System.out.println("Uspiješno ste prijavili ispit.");
						}
						else {
							System.out.println("Ne možete prijaviti ispit, nedovoljan iznos na raèunu");
						}
					}
				}
			}
		}
	}
	
	public static void passedSubjects (){
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		
		int i = 0;
		double gpa = 0;
		double sum = 0;
		for (PassedSubject ps : passedSubjects){
			if(ps == null)
				continue;
			i++;
			System.out.println(i + ". " + ps);
			sum += ps.getGrade();
		}
		if (i == 0){
			System.out.println("Nema položenih ispita.");
		}
		else {
			gpa = sum / passedSubjects.size();
			System.out.println("Prosjeèna ocjena: " + gpa);
		}
	}
	
	public static void examsEntry (){
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<Exam> examEntry = logedInStudent.getExamEntry();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		int i = 0;
		Date dateNow = new Date();
		for (Exam e : examEntry){
			boolean ind = true;
			boolean x = false;
			if(e == null)
				continue;
			if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
					(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())))
				x = true;
			if(x)
				continue;
			i++;
			for (PassedSubject ps : passedSubjects){
				if (ps == null)
					continue;
				if(ps.getSubject().equals(e.getSubject())){
					System.out.println(i + ". " + e.getSubject().getName() + " (Ocjena: " + ps.getGrade() + ")");
					ind = false;
					break;
				}
			}
			if(ind)
				System.out.println(i + ". " + e.getSubject().getName());
		}
		if (i == 0){
			System.out.println("Nema prijavljenih ispita.");
		}
	}
	
	public static void payments(){
		int i = 0;
		for(Payment p : App.getPayments()){
			if (p == null)
				continue;
			i++;
			System.out.println(i + ". " + p);
		}
		if(i == 0)
			System.out.println("Nema uplata.");
	}
	
	public static void examsEntryForProfessor(){
		Professor logedInProfessor = CheckLogIn.getProfessorLogedIn();
		ArrayList<Payment> payments = App.getPayments();
		int i = 0;
		Date dateNow = new Date();
		for (Payment p : payments){
			boolean x = false;
			if (p == null)
				continue;
			for (Exam e : SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry()){
				if(e == null)
					continue;
				if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
						(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())))
					x = true;
			}
			if(x)
				continue;
			if (p.getSubject().getProfessor().getJmbg().equals(logedInProfessor.getJmbg())  &&
					SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry().contains(SearchUtils.findExamBySubject(p.getSubject().getName()))){
				i++;
				System.out.println(i + ". " + p.getSubject().getName() + " (" + 
				(SearchUtils.findStudentByIndex(p.getIndex())).getFirstName() + " " + 
						(SearchUtils.findStudentByIndex(p.getIndex())).getLastName() + " " + p.getIndex() + ")");
			}
		}
		if(i == 0)
			System.out.println("Nema prijavljenih ispita.");
	}
	
	public static void marks(){
		Professor logedInProfessor = CheckLogIn.getProfessorLogedIn();
		ArrayList<Payment> payments = App.getPayments();
		Date dateNow = new Date();
		int i = 0;
		for (Payment p : payments){
			if (p == null)
				continue;
			if (p.getSubject().getProfessor().getJmbg().equals(logedInProfessor.getJmbg()) &&
					SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry().contains(SearchUtils.findExamBySubject(p.getSubject().getName()))){
				boolean x = false;
				for (PassedSubject ps : SearchUtils.findStudentByIndex(p.getIndex()).getPassedSubjects()){
					if(ps == null)
						continue;
					if(ps.getSubject().equals(p.getSubject()))
						x = true;
				}
				for (Exam e : SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry()){
					if(e == null)
						continue;
					if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
							(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())))
						x = true;
				}
				if(x)
					continue;
				i++;
				System.out.println(i + ". " + p.getSubject().getName() + " (" + 
				(SearchUtils.findStudentByIndex(p.getIndex())).getFirstName() + " " + 
						(SearchUtils.findStudentByIndex(p.getIndex())).getLastName() + " " + p.getIndex() + ")");
			}
		}
		if(i == 0){
			System.out.println("Nema prijavljenih ispita.");
			return;
		}
		
		System.out.println("Izaberite studenta");
		Scanner examOptionScan = new Scanner(System.in);
		int examOption = FactoryUtils.checkInputInt(examOptionScan);
		if(examOption <1 || examOption >i){
			System.out.println("Pogrešan unos.");
			marks();
		}
		i = 0;
		for (Payment p : payments){
			if (p == null)
				continue;
			if (p.getSubject().getProfessor().getJmbg().equals(logedInProfessor.getJmbg())){
				i++;
				if(i == examOption){
					Scanner gradeScan = new Scanner(System.in);
					System.out.println("Ocjena: ");
					int grade = FactoryUtils.checkInputInt(gradeScan);
					Student s = SearchUtils.findStudentByIndex(p.getIndex());
					PassedSubject ps = new PassedSubject(grade, p.getSubject());
					s.getPassedSubjects().add(ps);
					double gpa = 0;
					double sum = 0;
					for (PassedSubject ps1 : s.getPassedSubjects()){
						if(ps1 == null)
							continue;
						sum += ps1.getGrade();
					}
					gpa = sum / (s.getPassedSubjects().size());
					s.setGpa(gpa);
					ArrayList<Student> students = App.getStudents();
					FileUtils.writeToFile(students, "students");
					System.out.println("Uspiješno ste unijeli ocjenu.");
				}
			}
		}
	}
	
	public static void odjavaIspita(){
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<Exam> exams = logedInStudent.getExamEntry();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		ArrayList<Payment> payments = App.getPayments();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		Date dateTemp = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		int i = 0;
		for (Exam e : exams){ 
			boolean x = false;
			if(e == null)
				continue;
			for (PassedSubject ps : passedSubjects) {
				if (ps == null)
					continue;
				if(ps.getSubject().equals(e.getSubject()))
					x = true;
			}
			if(x)
				continue;
			if(e.getDate().after(dateTemp)){
				i++;
				System.out.println(i + ". " + e.getSubject().getName());
			}
		}
		if (i == 0){
			System.out.println("Nema ispita za odjavljivanje.");
			return;
		}
		else {
			System.out.println("Izaberite ispit koji želite odjaviti");
		}
		Scanner examOptionScan = new Scanner(System.in);
		int examOption = FactoryUtils.checkInputInt(examOptionScan);
		if(examOption <1 || examOption >i){
			System.out.println("Pogrešan unos.");
			odjavaIspita();
		}
		i = 0;
		for (Exam e1 : exams){
			if(e1 == null)
				continue;
			i++;
			if(i == examOption){
				Exam selectedExam = e1;
				if(selectedExam.getStatus().equals(Status.REGULAR))
					logedInStudent.setAccount(logedInStudent.getAccount() + 200);
				if(selectedExam.getStatus().equals(Status.NOT_REGULAR))
					logedInStudent.setAccount(logedInStudent.getAccount() + 400);
				logedInStudent.getExamEntry().remove(selectedExam);
				for(Payment p : payments){
					if(p == null)
						continue;
					if(p.getSubject().getName().equals(selectedExam.getSubject().getName()) && p.getIndex() == logedInStudent.getIndex()){
						payments.remove(p);
						FileUtils.writeToFile(payments, "payments");
						break;
					}
				}
				
				ArrayList<Student> students = App.getStudents();
				FileUtils.writeToFile(students, "students");
				System.out.println("Uspiješno ste odjavili ispit.");
				break;
			}
		}
	}
	
	private static Scanner sc;
	
	public static String checkInput(String s){
		while (!s.matches("[a-zA-Z]+") || s.equals("")){
			sc = new Scanner(System.in);
			System.out.println("Greška! Unesite ponovo: ");
			s = sc.nextLine().trim();
		}
		return s;
	}
	
	private static Scanner sc2;
	
	public static String checkInputString(String s){
		while (s.equals("")){
			sc2 = new Scanner(System.in);
			System.out.println("Greška! Unesite ponovo: ");
			s = sc2.nextLine().trim();
		}
		return s;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static int checkInputInt(Scanner sc){
		String s = sc.nextLine();
		while(!isInteger(s)){
			System.out.println("Greška! Unesi ponovo: ");
			s = sc.nextLine();
		}
		int i = Integer.parseInt(s);
		return i;
	}
	
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static double checkInputDouble(Scanner sc){
		String s = sc.nextLine();
		while(!isDouble(s)){
			System.out.println("Greška! Unesi ponovo: ");
			s = sc.nextLine();
		}
		double i = Double.parseDouble(s);
		return i;
	}
}
