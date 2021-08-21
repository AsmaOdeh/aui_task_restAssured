import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class CapitalActions {

    public static int statusCode (Response capitalResponse){
        int statusCode = capitalResponse.getStatusCode();
        return statusCode;
    }

    //Response body schema validation
    public static void schemaValidation (Response capitalResponse){
        capitalResponse.then().assertThat().body(matchesJsonSchemaInClasspath("album_schema.json"));
    }
}
