package model;

import java.util.Date;

import dataUtil.FactoryUtils;

public class Exam implements Writable {

	public enum ExamType {
		ORAL_EXAM, TEST, PRACTISE
	}

	public enum Status {
		REGULAR, NOT_REGULAR
	}

	private int id;
	private ExamType type;
	private Status status;
	private Date date;
	private String classroom;
	private Subject subject;

	public Exam(int id, ExamType type, Status status, Date date, String classroom, Subject subject) {
		this.id = id;
		this.type = type;
		this.status = status;
		this.date = date;
		this.classroom = classroom;
		this.subject = subject;
	}

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", type=" + String.valueOf(getType()) + ", status=" + String.valueOf(getStatus())
				+ ", date=" + FactoryUtils.sdf.format(getDate()) + ", classroom="
				+ classroom + ", subject=" + getSubject().getName() + "]";
	}
	
	@Override
	public String toFile() {
		return getId() + "!" + String.valueOf(getType()) + "!" + String.valueOf(getStatus()) + "!" +
				FactoryUtils.sdf.format(getDate()) + "!" + getClassroom() + "!" + getSubject().getName();
	}

}
