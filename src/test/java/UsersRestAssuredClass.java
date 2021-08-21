import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class UsersRestAssuredClass extends UsersActions {


    private static int successStatusCode = 200;
    private static String extractedValidID = "7";
    private static String extractedInvalidID = "20";



    @Test(description = "Post Test", priority = 1)
    public static void postTest (){

        //This is a post request and I created the payload as JSON and attached it in the resources

        // Creating payload as a file >> Creating a File instance
        File jsonDataInFile = new File("src/main/resources/payloads/payload.json");

        //GIVEN
        Response response = RestAssured
                .given()
                .baseUri("https://reqres.in/api/users")
                .contentType(ContentType.JSON)
                .body(jsonDataInFile)
                // WHEN
                .when()
                .post()
                // THEN
                .then()
                .extract().response();

        System.out.println("Response body: " + response.body().asString());

        // Status Code
        int statusCode = statusCode (response);
        System.out.println("Status code is "+statusCode);
        Assert.assertEquals(statusCode , 201 , "Verify if the status code is correct");

        //Additional step to verify that the response is correct and contains the data from the payload
        //Assert.assertEquals("NAME", response.jsonPath().getString("name"),"VERIFY");

        //Extract the id from the returned response and print it out
        JsonPath jsonPathEvaluator = response.jsonPath();
        String idFromPostResponse = jsonPathEvaluator.getString("id");
        System.out.println("The returned id is <" + idFromPostResponse+">");

    }

    @Test(description = "positive get Test", priority = 2)
    public static void positiveGetTest (){

        //Returning the request against the base URL
        RestAssured.baseURI = "https://reqres.in/api/users";

        //Response body for id = 7
        RequestSpecification idRequest = RestAssured.given();

        //Make a GET request call directly by using RequestSpecification.get() method to return the URL for a specific id
        Response idResponse = idRequest.request(Method.GET, "/"+extractedValidID+"?page=2");

        //Return the content of the response body for a specific id
        String idResponseBody = idResponse.getBody().asString();
        System.out.println("Response Body for " +extractedValidID+ " is =>  " + idResponseBody);

        // Status Code
        int statusCode = statusCode (idResponse);
        System.out.println("Status code is "+statusCode);
        Assert.assertEquals(statusCode , successStatusCode , "Verify if the status code is correct");

        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = idResponse.jsonPath();
        // Query the JsonPath object to get a String value of the specified by JsonPath: first name
        String firstName = jsonPathEvaluator.getString("data.first_name");


        // Query the JsonPath object to get a String value of the specified by JsonPath: last name
        String lastName = jsonPathEvaluator.getString("data.last_name");
        System.out.println("The user with ID #"+extractedValidID+" is <"+firstName+"> <"+lastName+">");
    }

    @Test(description = "Negative get test", priority = 3)
    public static void negativeGetTest(){

        //Returning the request against the base URL
        RestAssured.baseURI = "https://reqres.in/api/users";

        //Response body for id=7
        RequestSpecification idRequest = RestAssured.given();

        //Make a GET request call directly by using RequestSpecification.get() method to return the URL for a specific id
        Response idResponse = idRequest.request(Method.GET, "/"+extractedInvalidID+"?page=2");


        //Return the content of the response body for a specific id
        String idResponseBody = idResponse.getBody().asString();
        System.out.println("Response Body for " +extractedInvalidID+ " is =>  " + idResponseBody);

        // Status Code
        int statusCode = statusCode (idResponse);
        System.out.println("Status code is "+statusCode);
        Assert.assertEquals(statusCode , successStatusCode , "Verify if the status code is correct");

        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = idResponse.jsonPath();
        // Query the JsonPath object to get a String value of the specified by JsonPath: first name
        String firstName = jsonPathEvaluator.getString("data.first_name");

        // Query the JsonPath object to get a String value of the specified by JsonPath: last name
        String lastName = jsonPathEvaluator.getString("data.last_name");
        System.out.println("The user with ID #"+extractedValidID+" is <"+firstName+"> <"+lastName+">");

    }


}
