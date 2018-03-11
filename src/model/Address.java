package model;

public class Address {

	private String place;
	private String street;
	private int streetNo;

	public Address(String place, String street, int streetNo) {
		super();
		this.place = place;
		this.street = street;
		this.streetNo = streetNo;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(int streetNo) {
		this.streetNo = streetNo;
	}

	@Override
	public String toString() {
		return "Address [place=" + place + ", street=" + street + ", streetNo=" + streetNo + "]";
	}
	
	

}
