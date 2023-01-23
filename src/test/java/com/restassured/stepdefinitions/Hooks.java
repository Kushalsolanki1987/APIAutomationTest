package com.restassured.stepdefinitions;
import java.io.FileNotFoundException;
import io.cucumber.java.Before;

public class Hooks {
	// Below methods is created to run the Delete Issue scenario independently
	
	@Before("@DeleteBooking")
	public void beforeScenario() throws FileNotFoundException { 
		StepDefinitions m = new StepDefinitions();		
		if(StepDefinitions.bookingid==null) {
		m.create_booking_payload_with("Alex","Scott","Farm","01-01-2020", "2022-01-01");
		m.user_calls_with_http_request("CreateBookingAPI", "POST");		
		}			
	}	
}
