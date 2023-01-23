Feature: Validating Booking APIs 

@Regression
Scenario Outline:
Verify if the Booking is being successfully created using CreateBookingAPI 
Given Create Booking Payload with "<firstname>" "<lastname>" "<additionalneeds>" "<checkin>" "<checkout>"
When user calls "CreateBookingAPI" with "POST" http request 
Then The API call gets status code 200 
And "booking.firstname" in response body is "<firstname>" 
And "booking.lastname" in response body is "<lastname>" 
And verify BookingID maps to "<firstname>" using "GetBookingAPI" 	
Examples: 
|firstname|     |lastname|   |additionalneeds| |checkin|       |checkout|
|charles|       |austin|     |breakfast|	   |2001-01-20|    |2001-01-20| 
|Steve|         |Martin|     |Parking|		   |11-11-1900|	   |2022-01-18|	
|GREG|			|CHAPPEL|    |PATIO|		   |2011-01-22|	   |2009-12-20|	
|*&**@&|		|()(#)|		 |*(*)@()|		   |2022-01-18|	   |2003-12-20|
|1212|			|8392938|	 |9000912|		   |2009-12-20|	   |2009-12-20|
|firstname_testmaximumcharacterlength||lastname_testmaximumcharacterlength||additionalneeds_testmaximumcharacterlength| |2009-12-20| |2009-12-00|
	
@Regression	
Scenario Outline:
Verify if the Specific booking Ids are fetched using parameters
Given Booking API Payload using "<firstname>" "<lastname>"
When user calls "GetBookingAPI" with "GET-ALL" http request
Then The API call gets status code 200 
And verify the Booking Id using "<firstname>"
Examples: 
|firstname|     |lastname| 
|charles|       |austin|     
		
@Regression		
Scenario Outline:
Verify if the user is able to partially update booking using Partial Update API 
Given Partial Update Booking Payload with "<firstname>" "<lastname>" "<additionalneeds>" "<checkin>" "<checkout>"
When user calls "PartialUpdateBookingAPI" with "PATCH" http request 
Then The API call gets status code 200 
And verify BookingID maps to "<firstname>" using "GetBookingAPI"	
Examples: 
|firstname|            |lastname|          |additionalneeds|  |checkin|		|checkout|
|Updatedcharles|       |Updatedaustin|     |Updatedbreakfast| |01-01-1900|	|2009-12-20|
|updatedSteve|         |updatedMartin|     |updatedParking|   |2011-11-22|	|2003-12-20|
				
@Regression	
Scenario Outline:
Verify if Partial Update Booking API throws an error when accessed without private token. 
Given Partial Update Booking Payload with "<firstname>" "<lastname>" "<additionalneeds>" "<checkin>" "<checkout>" without private token 
When user calls "PartialUpdateBookingAPI" with "PATCH" http request 
Then The API call gets status code 403 	
Examples: 
|firstname|            |lastname|          |additionalneeds|  |checkin|    |checkout|
|charlesnotoken|       |austinnotoken|     |breakfastnotoken| |01-01-2020| |2009-12-20|

@Regression	@DeleteBooking		
Scenario: Verify if the Delete Booking API is working. 
Given Delete Booking Payload 
When user calls "DeleteBookingAPI" with "DELETE-ALL" http request 
Then The API call gets status code 201 
And  user calls "GetBookingAPI" with "GET" http request 
And The API call gets status code 404 

@Regression	
Scenario:
Verify if the Delete Booking API throws an error when accessed without private token. 
Given Delete Booking Payload without Private Token 
When  user calls "DeleteBookingAPI" with "DELETE" http request 
Then The API call gets status code 403 
	
@Regression	
Scenario:
Verify if All the booking Ids are fethched using Booking API
Given Booking API Payload
When user calls "GetBookingAPI" with "GET-ALL" http request
Then The API call gets status code 200 


	
	
	
	
	
