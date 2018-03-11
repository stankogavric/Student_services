package model;

import java.util.ArrayList;
import java.util.Date;

import dataUtil.FactoryUtils;

public class Student extends Person {

	private int index;
	private double gpa;
	private int schoolYear;
	private String parent;
	private String email;
	private Address address;
	private double account;
	private ArrayList<Exam> examEntry = new ArrayList<Exam>();
	private ArrayList<PassedSubject> passedSubjects = new ArrayList<PassedSubject>();
	private String department;
	private Date date;
	private String telephone;
	
	

	public Student(int id, String jmbg, String firstName, String lastName, String username, String password,
			int index, double gpa, int schoolYear, String parent, String email, Address address, double account,
			ArrayList<Exam> examEntry, ArrayList<PassedSubject> passedSubjects, String department,
			Date date, String telephone) {
		super(id, jmbg, firstName, lastName, username, password);
		this.index = index;
		this.gpa = gpa;
		this.schoolYear = schoolYear;
		this.parent = parent;
		this.email = email;
		this.address = address;
		this.account = account;
		this.examEntry = examEntry;
		this.passedSubjects = passedSubjects;
		this.department = department;
		this.date = date;
		this.telephone = telephone;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public int getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	};

	public ArrayList<PassedSubject> getPassedSubjects() {
		return passedSubjects;
	}

	public void setPassedSubjects(ArrayList<PassedSubject> passedSubjects) {
		this.passedSubjects = passedSubjects;
	}

	public ArrayList<Exam> getExamEntry() {
		return examEntry;
	}

	public void setExamEntry(ArrayList<Exam> examEntry) {
		this.examEntry = examEntry;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public static ArrayList<String> passedSubjectsToFile(ArrayList<PassedSubject> passedSubjects){
		ArrayList<String> data = new ArrayList<String>();
		if (passedSubjects.isEmpty())
			return data;
		for (PassedSubject ps : passedSubjects) {
			if (ps == null)
				continue;
			data.add(ps.toFile());
		}
		return data;
	}
	
	public static ArrayList<String> examEntryToFile(ArrayList<Exam> examEntry){
		ArrayList<String> data = new ArrayList<String>();
		if (examEntry.isEmpty())
			return data;
		for (Exam e : examEntry) {
			if (e == null)
				continue;
			data.add(e.getSubject().getName());
		}
		return data;
	}

	@Override
	public String toString() {
		return "Ime: " + getFirstName() + "\nPrezime: " + getLastName() + "\nID: "+ getId() + "\nJMBG: " + getJmbg() + 
				"\nBroj indeksa: " + getIndex() + "\nProsjeèna ocjena: " + getGpa() + "\nŠkolska godina: " + getSchoolYear() + 
				"\nIme jednog roditelja: " + getParent() + "\nE-mail: " + getEmail() + "\nMjesto stanovanja: " +
				address.getPlace() + "\nUlica: " + address.getStreet() + "\nBroj ulice: " + address.getStreetNo() +
				"\nStanje na raèunu: " + getAccount() + "\nPrijavljeni ispiti: " + examEntryToFile(getExamEntry()) +
				"\nPoloženi predmeti: " + passedSubjectsToFile(getPassedSubjects()) + "\nSmjer: " + getDepartment() +
				"\nDatum roðenja: " + FactoryUtils.sdf.format(date) + "\nBroj telefona: " + getTelephone() + "\n";
	}

	@Override
	public String toFile() {
		return getId() + "|" + getJmbg() + "|" + getFirstName() + "|" + getLastName() + "|" + 
				getUsername() + "|" + getPassword() + "|" + getIndex() + "|" + getGpa() + "|" + 
				getSchoolYear() + "|" + getParent() + "|" + getEmail() + "|" + address.getPlace()
				+ "|" + address.getStreet() + "|" + address.getStreetNo() + "|" + getAccount() +
				"|" + examEntryToFile(getExamEntry()) + "|" + passedSubjectsToFile(getPassedSubjects()) + "|" + getDepartment() + "|" + 
				FactoryUtils.sdf.format(getDate()) + "|" + getTelephone();
	}
	

}
