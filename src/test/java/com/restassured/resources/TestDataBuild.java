package com.restassured.resources;

import com.assignment.restassured.pojo.BookingDates;
import com.assignment.restassured.pojo.BookingPayload;

public class TestDataBuild {
	
	public BookingPayload bookingPayload(String firstname, String lastname, String additionalneeds, String checkin, String checkout) {
		
		BookingDates bookingDates = new BookingDates();
		BookingPayload bookingPayload = new BookingPayload();
		bookingPayload.setAdditionalneeds(additionalneeds);
		bookingPayload.setDepositpaid(false);
		bookingPayload.setFirstname(firstname);
		bookingPayload.setLastname(lastname);
		bookingDates.setCheckin(checkin);
		bookingDates.setCheckout(checkout);
		bookingPayload.setBookingdates(bookingDates);
		
		return bookingPayload;
		
	}

}
