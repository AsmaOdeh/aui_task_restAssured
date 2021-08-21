import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class UsersRestAssuredClassTemp extends UsersActions {


    private static int successStatusCode = 200;
    private static String extractedValidID = "7";
    private static String extractedInvalidID = "20";


    @Test(description = "Post Test", priority = 1)
    public static void postTest (){

        //Response body for all
        RestAssured.baseURI = "https://reqres.in/api/users";
        RequestSpecification usersRequest = RestAssured.given();

        // JSONObject is a class that represents a Simple JSON.
        // We can add Key - Value pairs using the put method
        JSONObject requestParams = new JSONObject();

        requestParams.put("id", "7");
        requestParams.put("email", "asma@gmail.com");
        requestParams.put("first_name", "Asma");
        requestParams.put("last_name", "Odeh");
        requestParams.put("avatar",  "https://reqres.in/img/faces/1-image.jpg");

        // Add a header stating the Request body is a JSON
        usersRequest.header("Content-Type", "application/json");

        // Add the Json to the body of the request
        usersRequest.body(requestParams.toString());

        // Post the request and check the response
        Response response = usersRequest.post("");

        System.out.println("Response body: " + response.body().asString());

        JsonPath jsonPathEvaluator = response.jsonPath();
        String firstName = jsonPathEvaluator.getString("last_name");
        System.out.println("First Name received from Response " + firstName);
//-----------------------------
        /* String id = "7";
        Response idResponse = usersRequest.request(Method.GET, "/"+id);

        int statusCode = statusCode (idResponse);
        Assert.assertEquals(statusCode , 200 , "Verify if the status code is correct");*/

//--------------------------------------------------------------------------------------
         //Another way for payload
        // Creating a File instance
        File jsonDataInFile = new File("src/main/resources/payloads/payload.json");

        //GIVEN
        Response response1 = RestAssured
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

        System.out.println("Response body: " + response1.body().asString());

        //Extract the id from the returned response and print it out
        JsonPath jsonPathEvaluator1 = response1.jsonPath();
        String idFromPostResponse = jsonPathEvaluator1.getString("id");
        System.out.println("The returned id is <" + idFromPostResponse+">");

        // Status Code
        int statusCode = statusCode (response1);
        System.out.println("Status code is "+statusCode);
        Assert.assertEquals(statusCode , 201 , "Verify if the status code is correct");

    }

    @Test(description = "positive get Test", priority = 2)
    public static void positiveGetTest (){

        //String extractedID = "7";

        //Returning the request against the base URL
        RestAssured.baseURI = "https://reqres.in/api/users";

        //Response body for id=7
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
        // Then simply query the JsonPath object to get a String value of the node
        // specified by JsonPath: City (Note: You should not put $. in the Java code)
        String firstName = jsonPathEvaluator.getString("data.first_name");
        // Let us print the city variable to see what we got
        System.out.println("First Name received from Response " + firstName);


        String lastName = jsonPathEvaluator.getString("data.last_name");
        // Let us print the city variable to see what we got
        System.out.println("Last Name received from Response " + lastName);

        System.out.println("The user with ID #"+extractedValidID+" is <"+firstName+"> <"+lastName+">");


        //-------------------------------------------------------

       /* RestAssured.given().get("https://reqres.in/api/users?page=2").then().statusCode(200).body("data.id[0]", equalTo(7));


        RestAssured.given().get("https://reqres.in/api/users?page=2").then().
                statusCode(200).
                body("data.id[1]", equalTo(8)).
                body("data.first_name", hasItems("Michael","Lindsay")).
                log().all();*/

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
        // Then simply query the JsonPath object to get a String value of the node
        // specified by JsonPath: City (Note: You should not put $. in the Java code)
        String firstName = jsonPathEvaluator.getString("data.first_name");
        // Let us print the city variable to see what we got
        System.out.println("First Name received from Response " + firstName);


        String lastName = jsonPathEvaluator.getString("data.last_name");
        // Let us print the city variable to see what we got
        System.out.println("Last Name received from Response " + lastName);

        System.out.println("The user with ID #"+extractedInvalidID+" is <"+firstName+"> <"+lastName+">");



    }


}
