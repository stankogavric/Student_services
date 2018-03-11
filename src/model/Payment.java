package model;

import java.util.Date;

import dataUtil.FactoryUtils;

public class Payment implements Writable{
	private double amount;
	private int index;
	private Date date;
	private Subject subject;
	
	public Payment(double amount, int index, Date date, Subject subject) {
		super();
		this.amount = amount;
		this.index = index;
		this.date = date;
		this.subject = subject;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	@Override
	public String toString() {
		return "Payment [amount=" + amount + ", index=" + index + ", date=" + FactoryUtils.sdf.format(date) + ", subject="
				+ subject.getName() + "]";
	}

	@Override
	public String toFile() {
		return getAmount() + "|" + getIndex() + "|" + FactoryUtils.sdf.format(getDate()) + "|" + 
				getSubject().getName();
	}
	
}
