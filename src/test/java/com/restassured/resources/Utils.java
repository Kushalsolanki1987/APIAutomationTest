package com.restassured.resources;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	protected static List<String> bookingidsValue;
	static RequestSpecification requestSpec;
	protected static RequestSpecification requestBody;
	protected static Response responseBody;
	public static String bookingid;
	protected static HashMap<String, String> bookingids = new HashMap<String, String>();
	static PrintStream log;
	static {
		try {
			log = new PrintStream(new FileOutputStream("logging.txt"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static RequestSpecification requestSpecWithoutToken;
	private static final String PRIVATE_TOKEN_HEADER_NAME = "Authorization";
	public RequestSpecification requestSpecifications() throws FileNotFoundException {

		if (requestSpec == null) {
			requestSpec = new RequestSpecBuilder().setBaseUri(getProperties("baseUrl")).setContentType(ContentType.JSON)
					.addHeader(PRIVATE_TOKEN_HEADER_NAME, getProperties("privateToken"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return requestSpec;
		}
		return requestSpec;
	}

	public String getProperties(String value) {
		try (InputStream input = new FileInputStream("src/test/java/com/restassured/resources/config.properties")) {
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			return prop.getProperty(value);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String getJsonPath(Response responseBody, String key) {
		String resp = responseBody.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}

	public RequestSpecification requestSpecificationsWithoutToken() throws FileNotFoundException {
		if (requestSpecWithoutToken == null) {
			requestSpecWithoutToken = new RequestSpecBuilder().setBaseUri(getProperties("baseUrl"))
					.setContentType(ContentType.JSON).addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return requestSpecWithoutToken;
		}
		return requestSpecWithoutToken;
	}

	public void deleteAllBookingIds() throws FileNotFoundException {
		for (int i = 0; i < bookingidsValue.size(); i++) {
			System.out.println(bookingidsValue.get(i));
			requestBody = given().spec(requestSpecifications()).pathParam("bookingid", bookingidsValue.get(i));
			responseBody = requestBody.when().delete("/booking/" + "{bookingid}");
		}
	}

	public String getBookingId() {
		bookingid = getJsonPath(responseBody, "bookingid");
		return bookingid;
	}

	public HashMap<String, String> getBookingIdList() {
		String firstname = getJsonPath(responseBody, "booking.firstname");
		bookingids.put(firstname, bookingid);
		return bookingids;
	}
}
