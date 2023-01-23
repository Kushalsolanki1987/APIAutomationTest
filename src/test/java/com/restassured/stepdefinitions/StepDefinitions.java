package com.restassured.stepdefinitions;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import com.restassured.resources.APIresources;
import com.restassured.resources.TestDataBuild;
import com.restassured.resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions extends Utils {	
	APIresources resourceAPI;
	TestDataBuild data = new TestDataBuild();	

	@Given("Create Booking Payload with {string} {string} {string} {string} {string}")
	public void create_booking_payload_with(String firstname, String lastname, String additionalneeds, String checkin, String checkout)
			throws FileNotFoundException {
		requestBody = given().spec(requestSpecifications())
		.body(data.bookingPayload(firstname, lastname, additionalneeds, checkin, checkout));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) throws FileNotFoundException {
		// Constructor will be called with the value of resource which you pass		
		resourceAPI = APIresources.valueOf(resource);
		if (method.equalsIgnoreCase("POST"))
			responseBody = requestBody.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			responseBody = requestBody.when().get(resourceAPI.getResource() + "{bookingid}");
		else if (method.equalsIgnoreCase("DELETE"))
			responseBody = requestBody.when().delete(resourceAPI.getResource() + "{bookingid}");
		else if (method.equalsIgnoreCase("DELETE-ALL"))
			deleteAllBookingIds();
		else if (method.equalsIgnoreCase("PATCH"))
			responseBody = requestBody.when().patch(resourceAPI.getResource() + "{bookingid}");
		else if (method.equalsIgnoreCase("GET-ALL"))
			responseBody = requestBody.when().get(resourceAPI.getResource());
	}

	@Then("The API call gets status code {int}")
	public void the_api_call_gets_status_code(int expectedStatuscode) {
		assertEquals(responseBody.getStatusCode(), expectedStatuscode);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {		
		getBookingId(); 
		getBookingIdList();
		assertEquals(getJsonPath(responseBody, keyValue), expectedValue);
	}

	@Then("verify BookingID maps to {string} using {string}")
	public void verify_booking_id_maps_to_using(String expectedFirstName, String resource)
			throws FileNotFoundException {
		requestBody = given().spec(requestSpecifications()).pathParam("bookingid", bookingid);
		user_calls_with_http_request(resource, "GET");
		String actualFirstName = getJsonPath(responseBody, "firstname");
		assertEquals(actualFirstName, expectedFirstName);
	}

	@Given("Partial Update Booking Payload with {string} {string} {string} {string} {string}")
	public void partial_update_booking_payload_with(String firstname, String lastname, String additionalneeds, String checkin, String checkout)
			throws FileNotFoundException {
		requestBody = given().spec(requestSpecifications())
		.body(data.bookingPayload(firstname, lastname, additionalneeds, checkin, checkout)).pathParam("bookingid", bookingid);
	}

	@Given("Delete Booking Payload")
	public void delete_booking_payload() throws FileNotFoundException {
		bookingidsValue = new ArrayList<String>(bookingids.values());
	}

	@Given("Partial Update Booking Payload with {string} {string} {string} {string} {string} without private token")
	public void partial_update_booking_payload_with_without_private_token(String firstname, String lastname,
			String additionalneeds, String checkin, String checkout) throws FileNotFoundException {
		requestBody = given().spec(requestSpecificationsWithoutToken())
				.body(data.bookingPayload(firstname, lastname, additionalneeds, checkin, checkout)).pathParam("bookingid", bookingid);
	}

	@Given("Delete Booking Payload without Private Token")
	public void delete_booking_payload_without_private_token() throws FileNotFoundException {
		requestBody = given().spec(requestSpecificationsWithoutToken()).pathParam("bookingid", bookingid);
	}

	@Given("Booking API Payload")
	public void booking_api_payload() throws FileNotFoundException {
		requestBody = given().spec(requestSpecifications());
	}

	@Given("Booking API Payload using {string} {string}")
	public void booking_api_payload_using(String firstname, String lastname) throws FileNotFoundException {
		requestBody = given().spec(requestSpecifications()).queryParam("firstname", firstname).queryParam("lastname",
				lastname);
	}

	@Then("verify the Booking Id using {string}")
	public void verify_the_booking_id_using(String firstname) {
		String expectedBookingId = getJsonPath(responseBody, "[0].bookingid");
		String actualBookingID = bookingids.get(firstname);
		assertEquals(actualBookingID, expectedBookingId);
	}
}
