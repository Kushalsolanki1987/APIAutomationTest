package com.restassured.resources;

public enum APIresources {

	CreateBookingAPI("/booking/"), GetBookingAPI("/booking/"),
	DeleteBookingAPI("/booking/"), PartialUpdateBookingAPI("/booking/");

	private String resource;

	APIresources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}
}
